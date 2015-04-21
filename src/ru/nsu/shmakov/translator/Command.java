package ru.nsu.shmakov.translator;

import java.util.ArrayList;

/**
 * Created by kyb1k on 23.04.2015.
 */
public class Command {
    private Integer label;
    private String name;
    private String parameter;
    private ArrayList<Command> nextCommands;

    public Command(Integer label, String name) {
        this.label = label;
        this.name = name;
    }

    public Command(Integer label, String name, String parameter) {
        this.label = label;
        this.name = name;
        this.parameter = parameter;
    }



    public Command(Integer label, String parameter, BodyType type) {

    }

    public void addNextCommand(Command next) {
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

    public ArrayList<Command> getNextCommands() {
        return nextCommands;
    }
}
