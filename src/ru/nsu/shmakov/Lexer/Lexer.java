package ru.nsu.shmakov.Lexer;


import ru.nsu.shmakov.Buffer.Buffer;
import ru.nsu.shmakov.lexemes.Lexeme;
import ru.nsu.shmakov.lexemes.LexemeType;
import ru.nsu.shmakov.lexemes.TerminalConsts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Иван on 14.03.2015.
 */
public class Lexer {
    private Buffer buffer;
    private ArrayList<Lexeme>  lexemes;
    private Map<String, Lexeme> variablesTable;
    private Map<String, Lexeme> constantsTable;
    private Integer level;

    public ArrayList<Lexeme> getLexemes() {
        return lexemes;
    }

    public LexedProgram readProgram(){
        lexemes = new ArrayList<Lexeme>();
        variablesTable = new HashMap<String, Lexeme>();
        constantsTable = new HashMap<String, Lexeme>();

        Lexeme lex = getNext();
        lexemes.add(lex);
        level = 0;

        while (lex != null && !lex.equals(TerminalConsts.eof)) {
            lex = getNext();
            lexemes.add(lex);
        }

        if(level != 0)
            throw new RuntimeException("Invalid brackets");

        return new LexedProgram(variablesTable, constantsTable, lexemes);
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
            case '/':
            case '^':
            case ',':
            case '=':
            case '*':
            case '(':
            case '?':
            case ')':
            case '@':
            case '$':
            case '#':
            case ';':
                if(c == '(')
                    ++level;
                if(c == ')')
                    --level;
                if(level < 0)
                    throw new RuntimeException("Invalid brackets");
                result = TerminalConsts.getLexemeByName(c.toString());
                return result;
            case '<':
            case '>':
            case '!':
            case ':':
                if(buffer.pop() == '=') {
                    result = TerminalConsts.getLexemeByName(c.toString() + "=");
                    return result;
                } else {
                    buffer.goBack();
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
            if(c == null) {
                break;
            }
        }
        if (c != null && !( '0' <= c && c <= '9' ||
                'a' <= c && c <= 'z' ||
                'A' <= c && c <= 'Z') )
            buffer.goBack();


        String tmp = sb.toString();

        if(!tmp.isEmpty()){
            try{
                Integer.parseInt(tmp);
                result = new Lexeme(tmp, LexemeType.CONSTANT);
                if (!constantsTable.containsKey(tmp))
                    constantsTable.put(tmp, result);
                return result;

            } catch (Exception e){
                if(     tmp.equals("INT")      || tmp.equals("VAR") ||
                        tmp.equals("ARRAYOF")  || tmp.equals("END") ||
                        tmp.equals("APP")      || tmp.equals("UPD") ||
                        tmp.equals("INPUT")    || tmp.equals("PRINT") ||
                        tmp.equals("BEGIN")){
                    return TerminalConsts.getLexemeByName(tmp);
                }else if(tmp.charAt(0)> 9 || tmp.charAt(0) < 0){
                    result = new Lexeme(tmp, LexemeType.VARIABLE);
                    if (!variablesTable.containsKey(tmp))
                        variablesTable.put(tmp, result);
                    return result;
                }
            }
        }
        if(result == null)
            throw new RuntimeException("Incorrect lexeme." + c.charValue());
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
}
