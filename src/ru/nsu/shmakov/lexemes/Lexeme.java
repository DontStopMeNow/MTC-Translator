package ru.nsu.shmakov.lexemes;

/**
 * Created by Иван on 14.03.2015.
 */
public class Lexeme {
    String value;
    LexemeType type;

    public Lexeme(String value, LexemeType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public LexemeType getType() {
        return type;
    }
}
