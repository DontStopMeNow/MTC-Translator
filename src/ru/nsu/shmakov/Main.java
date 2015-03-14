package ru.nsu.shmakov;

import ru.nsu.shmakov.Buffer.Buffer;
import ru.nsu.shmakov.Lexer.Lexer;
import ru.nsu.shmakov.lexemes.Lexeme;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Buffer buffer = new Buffer("./resources/1.myl");
        Lexer lexer = new Lexer(buffer);
        ArrayList<Lexeme> lexemes = null;
        try {
            lexemes = lexer.readLexemesList();
        }
        catch (RuntimeException e) {
            System.out.println("Invalid lexeme.");
            return;
        }
        
        FileOutputStream fos = new FileOutputStream("./resources/1.lex");
        PrintStream ps = new PrintStream(fos);
        for (Lexeme lex : lexemes) {
            StringBuilder sb = new StringBuilder();
            sb.append(lex.getType());
            sb.append(" : ");
            sb.append(lex.getValue());

            System.out.println(sb.toString());
            ps.println(sb.toString());
        }


    }
}
