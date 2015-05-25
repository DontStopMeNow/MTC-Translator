package ru.nsu.shmakov.virtualmachine;

/**
 * Created by Иван on 25.05.2015.
 */
public interface MyVariable extends Cloneable {
    public int getLevel();
    public MyVariable clone();
    public String toString();
}
