package ru.nsu.shmakov.lexemes;

/**
 * Created by Иван on 14.03.2015.
 */
public class TerminalConsts {
    public static Lexeme addition           = new Lexeme("+", LexemeType.OPERATOR);
    public static Lexeme subtraction        = new Lexeme("-", LexemeType.OPERATOR);
    public static Lexeme multiplication     = new Lexeme("*", LexemeType.OPERATOR);
    public static Lexeme division           = new Lexeme("/", LexemeType.OPERATOR);
    public static Lexeme power              = new Lexeme("^", LexemeType.OPERATOR);
    public static Lexeme openingBracket     = new Lexeme("(", LexemeType.OPERATOR);
    public static Lexeme closingBracket     = new Lexeme(")", LexemeType.OPERATOR);
    public static Lexeme openingBody        = new Lexeme("{", LexemeType.OPERATOR);
    public static Lexeme closingBody        = new Lexeme("}", LexemeType.OPERATOR);
    public static Lexeme intType            = new Lexeme("INT", LexemeType.OPERATOR);
    public static Lexeme arrayType          = new Lexeme("ARRAY OF", LexemeType.OPERATOR);
    public static Lexeme assignment         = new Lexeme(":=", LexemeType.OPERATOR);
    public static Lexeme less               = new Lexeme("<", LexemeType.OPERATOR);
    public static Lexeme more               = new Lexeme(">", LexemeType.OPERATOR);
    public static Lexeme equally            = new Lexeme("=", LexemeType.OPERATOR);
    public static Lexeme lessOrEqually      = new Lexeme("<=", LexemeType.OPERATOR);
    public static Lexeme moreOrEqually      = new Lexeme(">=", LexemeType.OPERATOR);

    public static Lexeme getAddition() {
        return addition;
    }

    public static Lexeme getSubtraction() {
        return subtraction;
    }

    public static Lexeme getMultiplication() {
        return multiplication;
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

    public static Lexeme getOpeningBody() {
        return openingBody;
    }

    public static Lexeme getClosingBody() {
        return closingBody;
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

    public static Lexeme getNotEqually() {
        return notEqually;
    }

    public static Lexeme getNot() {
        return not;
    }

    public static Lexeme getApp() {
        return app;
    }

    public static Lexeme getUpd() {
        return upd;
    }

    public static Lexeme getSuccessively() {
        return successively;
    }

    public static Lexeme getIteration() {
        return iteration;
    }

    public static Lexeme getSelection() {
        return selection;
    }

    public static Lexeme notEqually         = new Lexeme("!=", LexemeType.OPERATOR);
    public static Lexeme not                = new Lexeme("!", LexemeType.OPERATOR);

    public static Lexeme app                = new Lexeme("APP", LexemeType.OPERATOR);
    public static Lexeme upd                = new Lexeme("UPD", LexemeType.OPERATOR);

    public static Lexeme successively       = new Lexeme(";", LexemeType.OPERATOR);
    public static Lexeme iteration          = new Lexeme("@", LexemeType.OPERATOR);
    public static Lexeme selection          = new Lexeme("#", LexemeType.OPERATOR);
}
