package ru.nsu.shmakov.Lexer;

import ru.nsu.shmakov.Buffer.Buffer;
import ru.nsu.shmakov.lexemes.Lexeme;
import ru.nsu.shmakov.lexemes.LexemeType;
import ru.nsu.shmakov.lexemes.TerminalConsts;

import java.util.ArrayList;

/**
 * Created by Иван on 14.03.2015.
 */
public class Lexer {
    public ArrayList<Lexeme> getLexemes() {
        return lexemes;
    }

    public ArrayList<Lexeme> readLexemesList(){
        Lexeme lex = getNext();
        lexemes.add(lex);
        while (!lex.equals(TerminalConsts.eof)) {
            lex = getNext();
            lexemes.add(lex);
        }
        return lexemes;

    }

    private Lexeme getNext() {
        Lexeme result = null;
        Character c = buffer.pop();
        if (c == null) {
            result = TerminalConsts.eof;
            return result;
        }
        while(c == ' ' || c == '\n' || c == '\r' || c == '\t'){
            c = buffer.pop();
        }
        switch (c) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '^':
            case '(':
            case ')':
            case '{':
            case '}':
            case '=':
            case '#':
            case '@':
            case ';':
            case '!':
                result = TerminalConsts.getLexemeByName(c.toString());
                return result;
            case '<':
            case '>':
                if(buffer.pop() == '=') {
                    buffer.goBack();
                    result = TerminalConsts.getLexemeByName(c.toString() + "=");
                    return result;
                }else{
                    result = TerminalConsts.getLexemeByName(c.toString());
                    return result;
                }
            default:
                break;
        }
        StringBuilder sb = new StringBuilder();
        while ( '0' <= c && c <= '9' ||
                'a' <= c && c <= 'z' ||
                'A' <= c && c <= 'Z' ) {
            sb.append(c);
            c = buffer.pop();
        }

        String tmp = sb.toString();

        if(!tmp.isEmpty()){
            try{
                Integer.parseInt(tmp);
                return new Lexeme(tmp, LexemeType.CONSTANT);
            } catch (Exception e){
                if(     tmp.equals("INT")      || tmp.equals("VAR") ||
                        tmp.equals("ARRAYOF") || tmp.equals("INT") ||
                        tmp.equals("APP")      || tmp.equals("UPD") ||
                        tmp.equals("INPUT")    || tmp.equals("PRINT")){
                    return TerminalConsts.getLexemeByName(tmp);
                }else if(tmp.charAt(0)> 9 || tmp.charAt(0) < 0){
                    return new Lexeme(tmp, LexemeType.VARIABLE);
                }
            }
        }
        if(result == null)
            throw new RuntimeException("Incorrect lexeme.");
        return result;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public Lexer(Buffer buffer) {

        this.buffer = buffer;
    }

    private Buffer buffer;
    private ArrayList<Lexeme>  lexemes = new ArrayList<Lexeme>();
}
