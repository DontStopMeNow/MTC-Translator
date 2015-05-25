package ru.nsu.shmakov.virtualmachine;

import ru.nsu.shmakov.translator.CommandSet;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Иван on 25.05.2015.
 */
public class Condition {
    private int codePosition;
    private CommandSet program;
    private HashMap<String, MyVariable> variables;
    private Stack<MyVariable> mathStack = new Stack<MyVariable>();

    public Stack<MyVariable> getMathStack() {
        return mathStack;
    }

    public void setMathStack(Stack<MyVariable> mathStack) {
        this.mathStack = mathStack;
    }

    public Condition(int codePosition, CommandSet program, HashMap<String, MyVariable> variables) {
        this.codePosition = codePosition;
        this.program = program;
        this.variables = variables;
    }

    public Condition(int codePosition, CommandSet program, HashMap<String, MyVariable> variables, Stack<MyVariable> mathStack) {
        this.codePosition = codePosition;
        this.program = program;
        this.variables = variables;
        this.mathStack = mathStack;
    }

    public Condition clone() {
        Set<String> keys = variables.keySet();
        HashMap<String, MyVariable> resVar = (HashMap<String, MyVariable>) variables.clone();

        for (String key : keys) {
            MyVariable old = variables.get(key);
            MyVariable tmp = variables.get(key).clone();

            //System.out.println(String.format("%d %d", old.getLevel(), tmp.getLevel()));
            resVar.put(key, tmp);
            while (mathStack.contains(old)) {
                int pos = mathStack.indexOf(old);
                mathStack.set(pos, tmp);
            }

        }
        return new Condition(codePosition, program, resVar, (Stack<MyVariable>) mathStack.clone());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Position: ").append(codePosition).append("]").append(variables.toString());
        return sb.toString();
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
