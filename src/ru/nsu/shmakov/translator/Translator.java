package ru.nsu.shmakov.translator;

import ru.nsu.shmakov.lexemes.Lexeme;
import ru.nsu.shmakov.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by kyb1k on 23.04.2015.
 */
public class Translator {
    TreeNode r;
    private Map<String, Lexeme> variablesTable;
    private Map<String, Lexeme> constantsTable;

    private int expressionCounter = 0;
    private CommandSet expressionSet = null;


    public Translator(TreeNode r, Map<String, Lexeme> variablesTable, Map<String, Lexeme> constantsTable) {
        this.r = r;
        this.variablesTable = variablesTable;
        this.constantsTable = constantsTable;
    }

    public CommandSet translate() {
        return translateBody(r);
    }

    public String translateVar() {
        StringBuilder sb = new StringBuilder();
        sb.append(variablesTable.size()).append('\n');
        Set<String> keys = variablesTable.keySet();
        for (String key: keys) {
            sb.append(key).append(" ").append(variablesTable.get(key).getVarType()).append('\n');
        }
        return sb.toString();
    }

    private CommandSet translateBody(TreeNode root) {
        if(root.getLexeme().getValue().equals(":=")) {
            return translateAssignment(root);
        }
        else if(root.getLexeme().getValue().equals("$")) {
            return translateSuccessively(root);
        }
        else if(root.getLexeme().getValue().equals("#")) {
            return translateSelection(root);
        }
        else if(root.getLexeme().getValue().equals("@")) {
            return translateIteration(root);
        }
        else if(root.getLexeme().getValue().equals("?")) {
            return translateTest(root);
        }
        else if(root.getLexeme().getValue().equals("PRINT")) {
            return translatePrint(root);
        }
        throw new RuntimeException("invalid operator");
    }
    private CommandSet translatePrint(TreeNode root) {
        expressionCounter = 0;
        expressionSet = new CommandSet(new ArrayList<Command>());

        translateExpression(root);
        CommandSet set = expressionSet;

        ArrayList<Command> commands = set.getCommands();
        commands.get(commands.size() - 1).setNextCommands(new ArrayList<Integer>());

        //System.out.println(set.toString());
        return set;
    }

    private CommandSet translateTest(TreeNode root) {
        expressionCounter = 0;
        expressionSet = new CommandSet(new ArrayList<Command>());

        translateExpression(root.getChildren().get(0));
        CommandSet set = expressionSet;

        ArrayList<Command> commands = set.getCommands();
        commands.get(commands.size() - 1).setNextCommands(new ArrayList<Integer>());

        //System.out.println(set.toString());
        return set;
    }

    private CommandSet translateIteration(TreeNode root) {
        Command jumpStart = new Command(0, "\\jmp");

        CommandSet childSet = translateBody(root.getChildren().get(0));
        childSet.addToLabels(1);
        int childMax = childSet.getMax();
        Command jumpEnd = new Command(childMax + 1, "\\jmp");

        jumpStart.addNextCommand(1);
        jumpStart.addNextCommand(childMax + 1);
        childSet.addNextLabelToLast(0);


        childSet.addBefore(jumpStart);
        childSet.addCommand(jumpEnd);

        //System.out.println(childSet.toString());

        return childSet;
    }

    private CommandSet translateSelection(TreeNode root) {
        TreeNode left  = root.getChildren().get(0);
        TreeNode right = root.getChildren().get(1);
        CommandSet leftSet  = translateBody(left);
        CommandSet rightSet = translateBody(right);

        int maxLeft  = leftSet.getMax();
        rightSet.addToLabels(maxLeft + 1);

        int maxRight = rightSet.getMax();
        rightSet.addNextLabelToLast(maxRight + 1);
        leftSet .addNextLabelToLast(maxRight + 1);

        Command jumpEnd = new Command(maxRight + 1, "\\jmp");
        Command jumpStart = new Command(-1, "\\jmp");

        jumpStart.addNextCommand(0);
        jumpStart.addNextCommand(maxLeft + 1);

        leftSet.addAll(rightSet);
        leftSet.addCommand(jumpEnd);
        leftSet.addBefore(jumpStart);
        leftSet.addToLabels(1);

        //System.out.println(leftSet.toString());
        return leftSet;
    }

    private CommandSet translateSuccessively(TreeNode root) {
        TreeNode left  = root.getChildren().get(0);
        TreeNode right = root.getChildren().get(1);
        CommandSet leftSet  = translateBody(left);
        CommandSet rightSet = translateBody(right);

        int maxLeft = leftSet.getMax();
        rightSet.addToLabels(maxLeft + 1);
        leftSet.addNextLabelToLast(maxLeft + 1);
        leftSet.addAll(rightSet);

        //System.out.println(leftSet.toString());
        return leftSet;
    }

    private CommandSet translateAssignment(TreeNode root) {
        expressionCounter = 0;
        expressionSet = new CommandSet(new ArrayList<Command>());
        translateExpression(root);
        ArrayList<Command> commands = expressionSet.getCommands();
        commands.get(commands.size() - 1).setNextCommands(new ArrayList<Integer>());
        return expressionSet;
    }


    private void translateExpression(TreeNode root) {
        ArrayList<TreeNode> children = root.getChildren();
        ArrayList<Integer>  nextCommands = new ArrayList<Integer>();
        if (!children.isEmpty()) {
            for (int i = 0; i < children.size(); ++i) {
                translateExpression(children.get(i));
            }
        }
        Command result = new Command(expressionCounter, root.getLexeme().getValue());
        nextCommands.add(expressionCounter + 1);
        result.setNextCommands(nextCommands);
        expressionSet.addCommand(result);
        expressionCounter++;
    }

    public Map<String, Lexeme> getVariablesTable() {
        return variablesTable;
    }


}
