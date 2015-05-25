package ru.nsu.shmakov.virtualmachine;

import ru.nsu.shmakov.translator.CommandSet;

import java.util.HashMap;

/**
 * Created by Иван on 25.05.2015.
 */
public class Condition {
    private int codePosition;
    private CommandSet program;
    private HashMap<String, MyVariable> variables;


    public Condition(int codePosition, CommandSet program, HashMap<String, MyVariable> variables) {
        this.codePosition = codePosition;
        this.program = program;
        this.variables = variables;
    }

    public Condition clone() {
        return new Condition(codePosition, program, (HashMap<String, MyVariable>) variables.clone());
    }

    public int getCodePosition() {
        return codePosition;
    }

    public void setCodePosition(int codePosition) {
        this.codePosition = codePosition;
    }

    public CommandSet getProgram() {
        return program;
    }

    public void setProgram(CommandSet program) {
        this.program = program;
    }

    public HashMap<String, MyVariable> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, MyVariable> variables) {
        this.variables = variables;
    }


}
