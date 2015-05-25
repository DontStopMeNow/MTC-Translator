package ru.nsu.shmakov.virtualmachine;

import com.sun.org.apache.xpath.internal.operations.Variable;
import ru.nsu.shmakov.lexemes.Lexeme;
import ru.nsu.shmakov.translator.Command;
import ru.nsu.shmakov.translator.CommandSet;

import java.io.IOException;
import java.util.*;

/**
 * Created by Иван on 25.05.2015.
 */
public class VirtualMachine {
    private CommandSet program;
    private HashMap<String, MyVariable> variables = new HashMap<String, MyVariable>();
    private Queue<Condition> conditions = new ArrayDeque<Condition>();
    private Stack<MyVariable> MathStack = new Stack<MyVariable>();
    private ArrayList<Condition> finalCond = new ArrayList<Condition>();

    public VirtualMachine(CommandSet program, Map<String, Lexeme> variablesTable) {
        this.program = program;
        Set<String> keys = variablesTable.keySet();
        int varLevel = 0;
        for (String key : keys) {
            Lexeme tmp = variablesTable.get(key);
            String varType = tmp.getVarType();

            if (varType.equals("INT")) {
                varLevel = 0;
                MyInt tmpInt = new MyInt(0);
                variables.put(key, tmpInt);
            }
            else {
                String tmpVarType = new String(varType);
                while (tmpVarType.contains("ARRAYOF ")) {
                    varLevel++;
                    tmpVarType = tmpVarType.replaceFirst("ARRAYOF ", "");

                }
                MyArray<MyVariable> arr = new MyArray<MyVariable>();
                arr.setLevel(varLevel);
                variables.put(key, arr);
            }
        }
        Condition startCondition = new Condition(0, program, (HashMap<String, MyVariable>)variables.clone());
        conditions.add(startCondition);
        //System.out.println(variables.toString());
    }

    public void start() {
        while (!conditions.isEmpty()) {
            Condition currentCondition = conditions.poll();
            Command currentCommand = program.getCommand(currentCondition.getCodePosition());
            try {
                applyCommand(currentCommand, currentCondition);
            }
            catch (TestException e) {
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(currentCommand.getNextCommands().size() == 0) {
                finalCond.add(currentCondition);
                continue;
            }
            for (int i = 0; i < currentCommand.getNextCommands().size(); i++) {
                Condition newCondition = currentCondition.clone();
                newCondition.setCodePosition(currentCommand.getNextCommands().get(i));
                conditions.add(newCondition);
            }
            /*
            if(currentCommand.getNextCommands().size() != 0) {
                if(currentCommand.getNextCommands().size() == 2)
                    currentCondition.setCodePosition(currentCommand.getNextCommands().get(1));
                else
                    currentCondition.setCodePosition(currentCommand.getNextCommands().get(0));
                conditions.add(currentCondition);
            }
            else {
                finalCond.add(currentCondition);
            }*/
        }
    }

    public void applyCommand(Command command, Condition condition) throws IOException {
        String name = command.getName();
        try {
            int tmp = Integer.parseInt(name);
            MathStack.add(new MyInt(tmp));
        }
        catch (Exception e) {
            if(variables.containsKey(name))
                MathStack.add(variables.get(name));
            else {

                if (name.equals(":=")) {
                    MyVariable operand1 = MathStack.pop();
                    MyVariable operand2 = MathStack.pop();
                    if (operand1.getLevel() == operand2.getLevel() && operand1.getLevel() == 0) {
                        MyInt var  = (MyInt) operand2;
                        MyInt expr = (MyInt) operand1;
                        var.setValue(expr.value);
                    }
                    else if (operand1.getLevel() == operand2.getLevel()) {
                        MyArray var  = (MyArray) operand2;
                        MyArray expr = (MyArray) operand1;
                        var.setValues(expr.getValues());
                    }
                    else
                        throw new RuntimeException("Invalid arr types");
                }
                else if(name.equals("+")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    MathStack.add(new MyInt(operand1.value + operand2.value));
                }
                else if(name.equals("-")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    MathStack.add(new MyInt(operand2.value - operand1.value));
                }
                else if(name.equals("*")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    MathStack.add(new MyInt(operand2.value * operand1.value));
                }
                else if(name.equals("/")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    MathStack.add(new MyInt(operand2.value / operand1.value));
                }
                else if(name.equals("^")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    MathStack.add(new MyInt((int) Math.pow(operand2.value , operand1.value)));
                }
                else if(name.equals("<")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    if (!(operand2.value < operand1.value))
                        throw new TestException("< test failed");
                }
                else if(name.equals(">")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    if (!(operand2.value > operand1.value))
                        throw new TestException("> test failed");
                }
                else if(name.equals("=")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    if (!(operand2.value == operand1.value))
                        throw new TestException("= test failed");
                }
                else if(name.equals("<=")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    if (!(operand2.value <= operand1.value))
                        throw new TestException("<= test failed");
                }
                else if(name.equals(">=")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    if (!(operand2.value >= operand1.value))
                        throw new TestException(">= test failed");
                }
                else if(name.equals("!=")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    MyInt operand2 = (MyInt)MathStack.pop();
                    if (!(operand2.value < operand1.value))
                        throw new TestException("!= test failed");
                }
                else if(name.equals("PRINT")) {
                    MyInt operand1 = (MyInt)MathStack.pop();
                    System.out.println(operand1.value);
                }
                else if(name.equals("INPUT")) {
                    int tmp = new Scanner(System.in).nextInt();
                    MathStack.add(new MyInt(tmp));
                }
                else if(name.equals("APP")) {
                    int tmp = new Scanner(System.in).nextInt();
                    MathStack.add(new MyInt(tmp));
                }
                else if(name.equals("UPD")) {
                    int tmp = new Scanner(System.in).nextInt();
                    MathStack.add(new MyInt(tmp));
                }

            }
        }
        //System.out.println(variables);
    }

    class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}
