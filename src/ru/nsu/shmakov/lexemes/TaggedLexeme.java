package ru.nsu.shmakov.lexemes;

/**
 * Created by Иван on 28.03.2015.
 */
public class TaggedLexeme {
    private Lexeme lexeme;
    private String tag = "";

    public TaggedLexeme(Lexeme lexeme, String tag) {
        this.lexeme = lexeme;
        this.tag = tag;
    }

    public TaggedLexeme(String lex, String tag) {
        this.tag = tag;
        lexeme = OperatorLexemes.getLexemeByName(lex);
        if (lexeme == null) {
            System.out.println("Not operator!");
            throw new RuntimeException("Not operator");
        }
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public void setLexeme(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
