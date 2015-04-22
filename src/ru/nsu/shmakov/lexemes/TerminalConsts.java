package ru.nsu.shmakov.lexemes;

/**
 * Created by Иван on 14.03.2015.
 */
public class TerminalConsts {
    public static Lexeme addition           = new Lexeme("+", LexemeType.OPERATOR);
    public static Lexeme subtraction        = new Lexeme("-", LexemeType.OPERATOR);
    public static Lexeme multiplicationIter = new Lexeme("*", LexemeType.OPERATOR);
    public static Lexeme division           = new Lexeme("/", LexemeType.OPERATOR);
    public static Lexeme power              = new Lexeme("^", LexemeType.OPERATOR);
    public static Lexeme openingBracket     = new Lexeme("(", LexemeType.OPERATOR);
    public static Lexeme closingBracket     = new Lexeme(")", LexemeType.OPERATOR);
    public static Lexeme intType            = new Lexeme("INT", LexemeType.OPERATOR);
    public static Lexeme arrayType          = new Lexeme("ARRAYOF", LexemeType.OPERATOR);
    public static Lexeme assignment         = new Lexeme(":=", LexemeType.OPERATOR);
    public static Lexeme less               = new Lexeme("<", LexemeType.OPERATOR);
    public static Lexeme more               = new Lexeme(">", LexemeType.OPERATOR);
    public static Lexeme equally            = new Lexeme("=", LexemeType.OPERATOR);
    public static Lexeme lessOrEqually      = new Lexeme("<=", LexemeType.OPERATOR);
    public static Lexeme moreOrEqually      = new Lexeme(">=", LexemeType.OPERATOR);
    public static Lexeme notEqually         = new Lexeme("!=", LexemeType.OPERATOR);
    public static Lexeme app                = new Lexeme("APP", LexemeType.OPERATOR);
    public static Lexeme upd                = new Lexeme("UPD", LexemeType.OPERATOR);
    public static Lexeme separator          = new Lexeme(";", LexemeType.OPERATOR);


    public static Lexeme successively       = new Lexeme("$", LexemeType.OPERATOR);
    public static Lexeme selection          = new Lexeme("#", LexemeType.OPERATOR);
    public static Lexeme iteration          = new Lexeme("@", LexemeType.OPERATOR);

    public static Lexeme eof                = new Lexeme("EOF", LexemeType.OPERATOR);
    public static Lexeme print              = new Lexeme("PRINT", LexemeType.OPERATOR);
    public static Lexeme input              = new Lexeme("INPUT", LexemeType.OPERATOR);
    public static Lexeme var                = new Lexeme("VAR", LexemeType.OPERATOR);
    public static Lexeme begin              = new Lexeme("BEGIN", LexemeType.OPERATOR);
    public static Lexeme end                = new Lexeme("END", LexemeType.OPERATOR);
    public static Lexeme comma              = new Lexeme(",", LexemeType.OPERATOR);
    public static Lexeme test               = new Lexeme("?", LexemeType.OPERATOR);


    public static Lexeme getLexemeByName(String name) {
        if (name.toUpperCase().equals("+")) return addition;
        if (name.toUpperCase().equals(";")) return separator;
        if (name.toUpperCase().equals("-")) return subtraction;
        if (name.toUpperCase().equals("*")) return multiplicationIter;
        if (name.toUpperCase().equals("/")) return division;
        if (name.toUpperCase().equals("^")) return power;
        if (name.toUpperCase().equals("(")) return openingBracket;
        if (name.toUpperCase().equals(")")) return closingBracket;
        if (name.toUpperCase().equals("INT")) return intType;
        if (name.toUpperCase().equals("ARRAYOF")) return arrayType;
        if (name.toUpperCase().equals(":=")) return assignment;
        if (name.toUpperCase().equals("<")) return less;
        if (name.toUpperCase().equals(">")) return more;
        if (name.toUpperCase().equals("=")) return equally;
        if (name.toUpperCase().equals("<=")) return lessOrEqually;
        if (name.toUpperCase().equals(">=")) return moreOrEqually;
        if (name.toUpperCase().equals("@")) return iteration;
        if (name.toUpperCase().equals("APP")) return app;
        if (name.toUpperCase().equals("UPD")) return upd;
        if (name.toUpperCase().equals("$")) return successively;
        if (name.toUpperCase().equals("#")) return selection;
        if (name.toUpperCase().equals("EOF")) return eof;
        if (name.toUpperCase().equals("PRINT")) return print;
        if (name.toUpperCase().equals("INPUT")) return input;
        if (name.toUpperCase().equals("VAR")) return var;
        if (name.toUpperCase().equals("BEGIN")) return begin;
        if (name.toUpperCase().equals("END")) return end;
        if (name.toUpperCase().equals(",")) return comma;
        if (name.toUpperCase().equals("?")) return test;
        if (name.toUpperCase().equals("!=")) return notEqually;
        return null;
    }

    public static Lexeme getEof() {
        return eof;
    }

    public static Lexeme getAddition() {
        return addition;
    }

    public static Lexeme getSubtraction() {
        return subtraction;
    }

    public static Lexeme getMultiplicationIter() {
        return multiplicationIter;
    }

    public static Lexeme getSeparator() {
        return separator;
    }

    public static Lexeme getSuccessively() {
        return successively;
    }

    public static Lexeme getIteration() {
        return iteration;
    }

    public static Lexeme getComma() {
        return comma;
    }

    public static Lexeme getDivision() {
        return division;
    }

    public static Lexeme getPower() {
        return power;
    }

    public static Lexeme getOpeningBracket() {
        return openingBracket;
    }

    public static Lexeme getClosingBracket() {
        return closingBracket;
    }

    public static Lexeme getNotEqually() {
        return notEqually;
    }

    public static Lexeme getIntType() {
        return intType;
    }

    public static Lexeme getArrayType() {
        return arrayType;
    }

    public static Lexeme getAssignment() {
        return assignment;
    }

    public static Lexeme getLess() {
        return less;
    }

    public static Lexeme getMore() {
        return more;
    }

    public static Lexeme getEqually() {
        return equally;
    }

    public static Lexeme getLessOrEqually() {
        return lessOrEqually;
    }

    public static Lexeme getMoreOrEqually() {
        return moreOrEqually;
    }

    public static Lexeme getApp() {
        return app;
    }

    public static Lexeme getUpd() {
        return upd;
    }


    public static Lexeme getSelection() {
        return selection;
    }

    public static Lexeme getPrint() {
        return print;
    }

    public static Lexeme getInput() {
        return input;
    }

    public static Lexeme getVar() {
        return var;
    }

    public static Lexeme getBegin() {
        return begin;
    }

    public static Lexeme getEnd() {
        return end;
    }


    public static Lexeme getTest() {
        return test;
    }
}
