package ru.nsu.shmakov.translator;

import ru.nsu.shmakov.lexemes.Lexeme;
import ru.nsu.shmakov.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        translateSuccessively(r);
        return null;
    }

    /*public CommandSet translateSelection(TreeNode root) {
        TreeNode left  = root.getChildren().get(0);
        TreeNode right = root.getChildren().get(1);
        CommandSet leftSet  = translateAssignment(left ); //ToDo: замениь на translateBody
        CommandSet rightSet = translateAssignment(right);

        int maxLeft = leftSet.getMax();
        rightSet.addToLabels(maxLeft + 1);
        leftSet.addNextLabelToLast(maxLeft + 1);
        leftSet.addAll(rightSet);

        System.out.println(leftSet.toString());
        return null;
    }*/

    public CommandSet translateSuccessively(TreeNode root) {
        TreeNode left  = root.getChildren().get(0);
        TreeNode right = root.getChildren().get(1);
        CommandSet leftSet  = translateAssignment(left ); //ToDo: замениь на translateBody
        CommandSet rightSet = translateAssignment(right);

        int maxLeft = leftSet.getMax();
        rightSet.addToLabels(maxLeft + 1);
        leftSet.addNextLabelToLast(maxLeft + 1);
        leftSet.addAll(rightSet);

        System.out.println(leftSet.toString());
        return null;
    }

    private CommandSet translateAssignment(TreeNode root) {
        expressionCounter = 0;
        expressionSet = new CommandSet(new ArrayList<Command>());
        translateExpression(root);
        ArrayList<Command> commands = expressionSet.getCommands();
        commands.get(commands.size() - 1).setNextCommands(new ArrayList<Integer>());
        System.out.println(expressionSet.toString());
        return expressionSet;
    }


    private void translateExpression(TreeNode root) {
        ArrayList<TreeNode> children = root.getChildren();
        ArrayList<Integer>  nextCommands = new ArrayList<Integer>();
        if (!children.isEmpty()) {
            //System.out.println("!");
            for (int i = 0; i < children.size(); ++i) {
                translateExpression(children.get(i));
            }
        }
        Command result = new Command(expressionCounter, root.getLexeme().getValue());
        nextCommands.add(expressionCounter + 1);
        result.setNextCommands(nextCommands);
        expressionSet.addCommand(result);
        //System.out.println(result.toString());
        //System.out.println(childrenCommands.toString());
        expressionCounter++;
        //return result;
    }
}
