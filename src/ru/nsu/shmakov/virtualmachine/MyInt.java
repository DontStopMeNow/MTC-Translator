package ru.nsu.shmakov.virtualmachine;

/**
 * Created by Иван on 25.05.2015.
 */
public class MyInt implements MyVariable {
    Integer value;
    int level = 0;

    public MyInt(Integer value) {
        this.value = value;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public MyVariable clone() {
        return new MyInt(value.intValue());
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String toString() {
        return new String(value.toString());
    }

    /*public MyInt() {
        value = new Integer(0);
    }

    public MyInt(Integer value) {
        this.value = value;
    }

    public MyInt(MyInt another) {
        this.value = another.value.intValue();
    }

    public void swap(MyInt another) {
        int tmp = another.value.intValue();
        another.value = this.value.intValue();
        this.value = tmp;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public MyInt clone() {
        return new MyInt(this);
    }*/
}
