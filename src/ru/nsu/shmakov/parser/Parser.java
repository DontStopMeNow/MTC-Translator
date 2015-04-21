package ru.nsu.shmakov.parser;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import ru.nsu.shmakov.lexemes.Lexeme;
import ru.nsu.shmakov.lexemes.LexemeType;
import ru.nsu.shmakov.lexemes.TerminalConsts;
import ru.nsu.shmakov.lexer.LexedProgram;
import ru.nsu.shmakov.tree.TreeNode;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kyb1k on 11.04.2015.
 */
public class Parser {
    private LexedProgram lexedProgram;
    private TreeNode root;

    private ArrayList<Lexeme> program;
    private Map<String, Lexeme> variablesTable;
    private Map<String, Lexeme> constantsTable;

    private Integer position;

    public Parser(LexedProgram lexedProgram) {
        this.lexedProgram = lexedProgram;
        program = lexedProgram.getProgram();
        variablesTable = new HashMap<String, Lexeme>();
        constantsTable = new HashMap<String, Lexeme>();
        position = 0;
    }

    private Lexeme getLexeme() {
        return program.get(position++);
    }

    private Lexeme lookLexeme() {
        return program.get(position);
    }

    private void goBack() {
        position--;
    }

    private void goFront() {
        position++;
    }

    private Lexeme getValidLexeme(Lexeme lexeme) {
        Lexeme operator = getLexeme();
        if (!operator.equals(lexeme)) {
            System.out.println(String.format("Expected lexeme: '%s'; Current lexeme: '%s'", lexeme.toString(), operator.toString()));
            throw new RuntimeException(String.format("Expected lexeme: '%s'; Current lexeme: '%s'", lexeme.toString(), operator.toString()));
        }
        return operator;
    }

    private Lexeme getValidLexemeByType(LexemeType lexemeType) {
        Lexeme lex = getLexeme();
        if (!lexemeType.equals(lex.getType())) {
            throw new RuntimeException("Parse exception!");
        }
        return lex;
    }

    public void parseProgram() {
        position = 0;
        program = lexedProgram.getProgram();
        variablesTable = new HashMap<String, Lexeme>();
        constantsTable = new HashMap<String, Lexeme>();

        parseDeclaration();
        Lexeme tmp = getValidLexeme(TerminalConsts.begin);
        root = parseBody();
        tmp = getValidLexeme(TerminalConsts.end);
    }

    private void parseDeclaration() {
        Lexeme lex = getValidLexeme(TerminalConsts.var);

        while (!lex.equals(TerminalConsts.begin)) {
            Lexeme variable = getLexeme();

            if (variable.equals(TerminalConsts.begin)) {
                goBack();
                break;
            } else {
                String varType = parseVarType();
                if (variablesTable.containsKey(variable.getValue())) {
                    System.out.println(String.format("Double declaratoin of: '%s'", variable.getValue()));
                    throw new RuntimeException(String.format("Double declaratoin of: '%s'", variable.getValue()));
                } else {
                    variable.setVarType(varType);
                    variablesTable.put(variable.getValue(), variable);
                    System.out.println(variable.toString());
                }
            }
        }

    }

    private String parseVarType() {
        Lexeme lexeme = getValidLexemeByType(LexemeType.OPERATOR);

        if (lexeme.equals(TerminalConsts.intType)) {
            lexeme = getValidLexeme(TerminalConsts.separator);
            return TerminalConsts.intType.getValue();
        } else if (lexeme.equals(TerminalConsts.arrayType)) {
            StringBuilder sb = new StringBuilder();
            sb.append(TerminalConsts.arrayType.getValue());
            sb.append(" ");
            sb.append(parseVarType());
            return sb.toString();
        } else {
            System.out.println("Invalid vartype");
            throw new RuntimeException("Invalid vartype");
        }
    }

    private TreeNode parseBody() {
        TreeNode bodyNode;
        Lexeme lexeme = lookLexeme();
        if (lexeme.equals(TerminalConsts.test)) {
            bodyNode = new TreeNode(lexeme);
            goFront();
            lexeme = getValidLexeme(TerminalConsts.openingBracket);
            TreeNode logicalExpression = parseLogicalExpression();
            lexeme = getValidLexeme(TerminalConsts.closingBracket);
            logicalExpression.setParent(bodyNode);
            bodyNode.addChild(logicalExpression);
            return bodyNode;
        }
        else if(lexeme.equals(TerminalConsts.print)) {
            goFront();
            bodyNode = new TreeNode(lexeme);
            lexeme = getValidLexeme(TerminalConsts.openingBracket);
            TreeNode expr = parseExpression();
            lexeme = getValidLexeme(TerminalConsts.closingBracket);
            if (!expr.getType().equals(TerminalConsts.intType.getValue())) {
                System.out.println("Printing expression is not int");
                throw new RuntimeException("Printing expression is not int");
            }
            expr.setParent(bodyNode);
            bodyNode.addChild(expr);
            return bodyNode;
        }
        else if (!lexeme.equals(TerminalConsts.openingBracket)) {
            TreeNode assignmentNode = parseAssignment();
            bodyNode = new TreeNode(TerminalConsts.separator);
            assignmentNode.setParent(bodyNode);
            bodyNode.addChild(assignmentNode);
            return bodyNode;
        } else {
            goFront();
            TreeNode body1 = parseBody();
            lexeme = getLexeme();
            if (lexeme.equals(TerminalConsts.closingBracket)) {
                lexeme = getLexeme();
                if (lexeme.equals(TerminalConsts.iteration)) {
                    bodyNode = new TreeNode(lexeme);
                    body1.setParent(bodyNode);
                    bodyNode.addChild(body1);
                    return bodyNode;
                } else {
                    System.out.println("Invalid operator");
                    throw new RuntimeException("Invalid operator");
                }
            } else if (lexeme.equals(TerminalConsts.successively) || lexeme.equals(TerminalConsts.selection)) {
                bodyNode = new TreeNode(lexeme);
                TreeNode body2 = parseBody();
                lexeme = getValidLexeme(TerminalConsts.closingBracket);
                body1.setParent(bodyNode);
                body2.setParent(bodyNode);
                bodyNode.addChild(body1);
                bodyNode.addChild(body2);
                return bodyNode;
            } else {
                System.out.println("Invalid operator");
                throw new RuntimeException("Invalid operator");
            }
        }
    }

    private TreeNode parseAssignment() {
        Integer oldPosition = position;
        Lexeme variable;
        try {
            variable = getValidLexemeByType(LexemeType.VARIABLE);
        } catch (Exception e) {
            goBack();
            return null;
        }
        if (!variablesTable.containsKey(variable.getValue())) {
            System.out.println(String.format("Variable not declarated: '%s'", variable.getValue()));
            throw new RuntimeException(String.format("Variable not declarated: '%s'", variable.getValue()));
        }
        variable = variablesTable.get(variable.getValue());
        Lexeme operator = getValidLexeme(TerminalConsts.assignment);
        TreeNode operatorNode = new TreeNode(operator);
        TreeNode expressionNode = parseExpression();
        TreeNode variableNode = new TreeNode(variable, variable.getVarType());

        if (!expressionNode.equalType(variableNode)) {
            System.out.println("Invalid vartype of variable and assignment expression");
            throw new RuntimeException("Invalid vartype of variable and assignment expression");
        }

        expressionNode.setParent(operatorNode);
        variableNode.setParent(operatorNode);
        operatorNode.addChild(variableNode);
        operatorNode.addChild(expressionNode);

        return operatorNode;
    }

    private TreeNode parseExpression() {
        TreeNode term1 = parseTerm();
        Lexeme operator = getLexeme();
        if (operator.equals(TerminalConsts.addition) ||
                operator.equals(TerminalConsts.subtraction)) {
            TreeNode term2 = parseExpression();
            if (!term1.equalType(term2)) {
                System.out.println("Invalid types in expression.");
                throw new RuntimeException("Invalid types in expression.");
            }

            TreeNode operatorNode = new TreeNode(operator, term1.getType());
            term1.setParent(operatorNode);
            term2.setParent(operatorNode);
            operatorNode.addChild(term1);
            operatorNode.addChild(term2);

            return operatorNode;
        } else {
            goBack();
            return term1;
        }
    }

    private TreeNode parseTerm() {
        TreeNode factor1 = parseFactor();
        Lexeme operator = getLexeme();
        if (operator.equals(TerminalConsts.multiplicationIter) ||
                operator.equals(TerminalConsts.division)) {
            TreeNode factor2 = parseTerm();
            if (!factor1.equalType(factor2)) {
                System.out.println("Invalid types in expression.");
                throw new RuntimeException("Invalid types in expression.");
            }
            TreeNode operatorNode = new TreeNode(operator, factor1.getType());
            factor1.setParent(operatorNode);
            factor2.setParent(operatorNode);
            operatorNode.addChild(factor1);
            operatorNode.addChild(factor2);
            return operatorNode;
        } else {
            goBack();
            return factor1;
        }
    }

    private TreeNode parseFactor() {
        TreeNode power1 = parsePower();
        Lexeme operator = getLexeme();
        if (operator.equals(TerminalConsts.power)) {
            TreeNode power2 = parseFactor();
            if (!power1.equalType(power2)) {
                System.out.println("Invalid types in expression.");
                throw new RuntimeException("Invalid types in expression.");
            }
            TreeNode operatorNode = new TreeNode(operator, power1.getType());
            power1.setParent(operatorNode);
            power2.setParent(operatorNode);
            operatorNode.addChild(power1);
            operatorNode.addChild(power2);
            return operatorNode;
        } else {
            goBack();
            return power1;
        }
    }

    private TreeNode parsePower() {
        Lexeme operator = getLexeme();
        TreeNode operand;
        if (operator.equals(TerminalConsts.subtraction)) {
            operand = parseOperand();
            TreeNode operatorNode = new TreeNode(operator, operand.getType());
            operand.setParent(operatorNode);
            operatorNode.addChild(operand);
            return operatorNode;
        } else {
            goBack();
            return operand = parseOperand();
        }
    }

    public Map<String, Lexeme> getVariablesTable() {
        return variablesTable;
    }

    public Map<String, Lexeme> getConstantsTable() {
        return constantsTable;
    }

    private TreeNode parseOperand() {
        Lexeme lexeme = getLexeme();
        if (lexeme.getType().equals(LexemeType.CONSTANT)) {
            TreeNode node = new TreeNode(lexeme, TerminalConsts.intType.getValue());
            return node;
        } else if (lexeme.getType().equals(LexemeType.VARIABLE)) {
            if (!variablesTable.containsKey(lexeme.getValue())) {
                System.out.println(String.format("Variable not declarated: '%s'", lexeme.getValue()));
                throw new RuntimeException(String.format("Variable not declarated: '%s'", lexeme.getValue()));
            }
            TreeNode node = new TreeNode(variablesTable.get(lexeme.getValue()), variablesTable.get(lexeme.getValue()).getVarType());
            return node;
        } else if (lexeme.equals(TerminalConsts.openingBracket)) {
            TreeNode node = parseExpression();
            lexeme = getValidLexeme(TerminalConsts.closingBracket);
            return node;
        } else if (lexeme.equals(TerminalConsts.app)) {
            lexeme = getValidLexeme(TerminalConsts.openingBracket);
            TreeNode array = parseOperand();
            if (!array.getType().contains(TerminalConsts.arrayType.getValue())) {
                System.out.println("Array expected.");
                throw new RuntimeException("Array expected.");
            }
            lexeme = getValidLexeme(TerminalConsts.comma);
            TreeNode operand = parseOperand();
            lexeme = getValidLexeme(TerminalConsts.closingBracket);
            if (!operand.getType().equals(TerminalConsts.intType.getValue())) {
                System.out.println("Index not int.");
                throw new RuntimeException("Index not int.");
            }
            String newVarType = new String(array.getType());
            newVarType = newVarType.replaceFirst("ARRAYOF ", "");
            TreeNode node = new TreeNode(TerminalConsts.app, newVarType);
            array.setParent(node);
            operand.setParent(node);

            node.addChild(array);
            node.addChild(operand);

            return node;
        } else if (lexeme.equals(TerminalConsts.upd)) {
            lexeme = getValidLexeme(TerminalConsts.openingBracket);
            TreeNode array = parseOperand();
            if (!array.getType().contains(TerminalConsts.arrayType.getValue())) {
                System.out.println("Array expected.");
                throw new RuntimeException("Array expected.");
            }
            lexeme = getValidLexeme(TerminalConsts.comma);
            TreeNode operand = parseOperand();
            lexeme = getValidLexeme(TerminalConsts.comma);
            if (!operand.getType().equals(TerminalConsts.intType.getValue())) {
                System.out.println("Index not int.");
                throw new RuntimeException("Index not int.");
            }

            String newVarType = new String(array.getType());
            newVarType = newVarType.replaceFirst("ARRAYOF ", "");
            TreeNode operand2 = parseOperand();
            if (!operand2.getType().equals(newVarType)) {
                System.out.println("Invalid type of new value in array.");
                throw new RuntimeException("Invalid type of new value in array.");
            }
            lexeme = getValidLexeme(TerminalConsts.closingBracket);

            TreeNode node = new TreeNode(TerminalConsts.app, array.getType());
            array.setParent(node);
            operand.setParent(node);
            operand2.setParent(node);

            node.addChild(array);
            node.addChild(operand);
            node.addChild(operand2);

            return node;
        }
        else if (lexeme.equals(TerminalConsts.input)) {
            lexeme = getValidLexeme(TerminalConsts.openingBracket);
            lexeme = getValidLexeme(TerminalConsts.closingBracket);
            return new TreeNode(TerminalConsts.input, TerminalConsts.intType.getValue());
        }


        return null;
    }

    private TreeNode parseLogicalExpression() {
        TreeNode operand1 = parseExpression();
        Lexeme operator = getLexeme();
        if (!operator.equals(TerminalConsts.less) && !operator.equals(TerminalConsts.lessOrEqually) &&
                !operator.equals(TerminalConsts.more) && !operator.equals(TerminalConsts.moreOrEqually) &&
                !operator.equals(TerminalConsts.equally) && !operator.equals(TerminalConsts.notEqually)) {
            System.out.println("Logical operator expected.");
            throw new RuntimeException("Logical operator expected.");
        }
        TreeNode operand2 = parseExpression();
        if (!operand1.equalType(operand2)) {
            System.out.println("Not equales types in logical expression.");
            throw new RuntimeException("Not equales types in logical expression.");
        }
        TreeNode operatorNode = new TreeNode(operator, "BOOLEAN");
        operand1.setParent(operatorNode);
        operand2.setParent(operatorNode);

        operatorNode.addChild(operand1);
        operatorNode.addChild(operand2);

        return operatorNode;
    }

    public TreeNode getRoot() {
        return root;
    }
}