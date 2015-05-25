package ru.nsu.shmakov.translator;

import java.util.ArrayList;

/**
 * Created by Иван on 22.04.2015.
 */
public class CommandSet {
    private ArrayList<Command> commands;

    public CommandSet(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void addBefore (Command command) {
        commands.add(0, command);
    }



    public void addToLabels(int a) {
        for (Command command: commands) {
            command.addToLabel(a);
        }
    }

    public int getMax() {
        int max = 0;
        if (!commands.isEmpty()) {
            max = commands.get(0).getLabel();

            for (Command command : commands) {
                if (command.getLabel() > max) {
                    max = command.getLabel();
                }
            }
            return max;
        }
        throw new RuntimeException("Empty body");
    }

    public void addNextLabelToLast(int a) {
        commands.get(commands.size() - 1).addNextCommand(a);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Command command: commands) {
            sb.append(command.toString()).append('\n');

        }
        return sb.toString();
    }

    public void addAll(CommandSet another) {
        ArrayList<Command> anotherCommands = another.getCommands();
        this.commands.addAll(anotherCommands);
    }

    public Command getCommand(int i) {
        return commands.get(i);
    }

}
