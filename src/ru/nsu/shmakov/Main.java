package ru.nsu.shmakov;

import ru.nsu.shmakov.Buffer.Buffer;
import ru.nsu.shmakov.Lexer.LexedProgram;
import ru.nsu.shmakov.Lexer.Lexer;
import ru.nsu.shmakov.lexemes.Lexeme;
import ru.nsu.shmakov.parser.Parser;
import ru.nsu.shmakov.translator.Translator;
import ru.nsu.shmakov.tree.TreeNode;

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
        t.translate();
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
