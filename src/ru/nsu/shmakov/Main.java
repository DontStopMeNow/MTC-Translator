package ru.nsu.shmakov;

import ru.nsu.shmakov.Buffer.Buffer;
import ru.nsu.shmakov.Lexer.LexedProgram;
import ru.nsu.shmakov.Lexer.Lexer;
import ru.nsu.shmakov.lexemes.Lexeme;
import ru.nsu.shmakov.parser.Parser;
import ru.nsu.shmakov.translator.Translator;
import ru.nsu.shmakov.tree.TreeNode;
import ru.nsu.shmakov.virtualmachine.MyArray;
import ru.nsu.shmakov.virtualmachine.MyInt;
import ru.nsu.shmakov.virtualmachine.VirtualMachine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Buffer buffer = new Buffer("./resources/1.myl");
        Lexer lexer = new Lexer(buffer);
        LexedProgram program = lexer.readProgram();
        ArrayList<Lexeme> lexemes = program.getProgram();
        Parser parser = new Parser(program);
        parser.parseProgram();
        TreeNode root = parser.getRoot();

        FileOutputStream fos = new FileOutputStream("./resources/1.lex");
        PrintStream ps = new PrintStream(fos);
        ps.println(root.serrTree());

        Translator t = new Translator(root, parser.getVariablesTable(), parser.getConstantsTable());
        fos = new FileOutputStream("./resources/1.byte");
        ps = new PrintStream(fos);

        ps.println(t.translateVar().concat(t.translate().toString()));

        /*MyArray<MyArray<MyInt> > arr = new MyArray<MyArray<MyInt>>();
        MyArray<MyInt> arr2 = new MyArray<MyInt>();
        MyInt i1 = new MyInt(2);
        MyInt i2 = new MyInt(3);
        MyInt i3 = new MyInt(4);

        arr2.put(2, i1);
        arr2.put(3, i2);

        arr.put(0, arr2);

        MyArray<MyArray<MyInt> > arr3 = arr.clone();
        arr3.get(0).put(3, i3);


        System.out.println(arr.toString());
        System.out.println(arr3.toString());
        */

        VirtualMachine vm = new VirtualMachine(t.translate(), t.getVariablesTable());
        vm.start();
        /*for (Lexeme lex : lexemes) {
            StringBuilder sb = new StringBuilder();
            sb.append(lex.getType());
            sb.append(" : ");
            sb.append(lex.getValue());

            System.out.println(sb.toString());
            ps.println(sb.toString());
        }*/

    }
}
