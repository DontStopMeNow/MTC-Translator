package ru.nsu.shmakov.translator;

import ru.nsu.shmakov.lexemes.Lexeme;
import ru.nsu.shmakov.tree.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kyb1k on 23.04.2015.
 */
public class Translator {
    TreeNode r;
    private Map<String, Lexeme> variablesTable;
    private Map<String, Lexeme> constantsTable;

    public Translator(TreeNode r, Map<String, Lexeme> variablesTable, Map<String, Lexeme> constantsTable) {
        this.r = r;
        this.variablesTable = variablesTable;
        this.constantsTable = constantsTable;
    }

    public void translate() {

    }

    private void translateAssignment() {

    }
}
