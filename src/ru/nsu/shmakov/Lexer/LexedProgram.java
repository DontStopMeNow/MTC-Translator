package ru.nsu.shmakov.lexer;

import ru.nsu.shmakov.lexemes.Lexeme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kyb1k on 11.04.2015.
 */
public class LexedProgram {
    private Map<String, Lexeme> variablesTable = new HashMap<String, Lexeme>();
    private Map<String, Lexeme> constantsTable = new HashMap<String, Lexeme>();
    private ArrayList<Lexeme> program = new ArrayList<Lexeme>();

    public LexedProgram(Map<String, Lexeme> variablesTable, Map<String, Lexeme> constantsTable, ArrayList<Lexeme> program) {
        this.variablesTable = variablesTable;
        this.constantsTable = constantsTable;
        this.program = program;
    }

    public Map<String, Lexeme> getVariablesTable() {
        return variablesTable;
    }

    public void setVariablesTable(Map<String, Lexeme> variablesTable) {
        this.variablesTable = variablesTable;
    }

    public Map<String, Lexeme> getConstantsTable() {
        return constantsTable;
    }

    public void setConstantsTable(Map<String, Lexeme> constantsTable) {
        this.constantsTable = constantsTable;
    }

    public ArrayList<Lexeme> getProgram() {
        return program;
    }

    public void setProgram(ArrayList<Lexeme> program) {
        this.program = program;
    }
}
