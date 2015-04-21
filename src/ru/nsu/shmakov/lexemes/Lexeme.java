package ru.nsu.shmakov.lexemes;

/**
 * Created by Иван on 14.03.2015.
 */
public class Lexeme {
    String value;
    LexemeType type;
    String varType;


    public Lexeme(String value, LexemeType type) {
        this.value = value;
        this.type = type;
        varType = "";
    }

    public Lexeme(String value, LexemeType type, String varType) {
        this.value = value;
        this.type = type;
        this.varType = varType;
    }

    public String getValue() {
        return value;
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lexeme another = (Lexeme) o;
        return value.equals(another.value) && type.equals(another.type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        sb.append(" ");
        sb.append(value);
        sb.append(" ");
        sb.append(varType);
        return sb.toString();
    }


    public void setValue(String value) {
        this.value = value;
    }

    public LexemeType getType() {
        return type;
    }

    public void setType(LexemeType type) {
        this.type = type;
    }
}
