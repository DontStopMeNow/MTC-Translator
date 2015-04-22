package ru.nsu.shmakov.translator;

import java.util.ArrayList;

/**
 * Created by kyb1k on 23.04.2015.
 */
public class Command {
    private Integer label;
    private String name;
    private String parameter;
    private ArrayList<Integer> nextCommands;

    public Command(Integer label, String name) {
        this.label = label;
        this.name = name;
        this.parameter = "";
        this.nextCommands = new ArrayList<Integer>();
    }

    public Command(Integer label, String name, String parameter) {
        this.label = label;
        this.name = name;
        this.parameter = parameter;
        this.nextCommands = new ArrayList<Integer>();
    }

    public boolean isHaveChildren() {
        return nextCommands.isEmpty();
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void setNextCommands(ArrayList<Integer> nextCommands) {
        this.nextCommands = nextCommands;
    }

    public Command(Integer label, String parameter, BodyType type) {

    }

    public void addNextCommand(Integer next) {
        nextCommands.add(next);
    }

    public Integer getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getParameter() {
        return parameter;
    }

    public ArrayList<Integer> getNextCommands() {
        return nextCommands;
    }

    public void addToLabel(int a) {
        label += a;
        for (int i = 0; i < nextCommands.size(); ++i) {
            nextCommands.set(i, nextCommands.get(i) + a);
        }
    }

    public String toString() {
        String s1 = String.format("%d: %s -> ", label, name);
        StringBuilder sb = new StringBuilder();
        for(Integer command: nextCommands) {
            sb.append(command).append(" ");
        }
        return s1.concat(sb.toString());
    }
}
