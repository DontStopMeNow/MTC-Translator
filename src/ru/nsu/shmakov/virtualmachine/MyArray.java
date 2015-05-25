package ru.nsu.shmakov.virtualmachine;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Иван on 25.05.2015.
 */
public class MyArray<T extends MyVariable> implements MyVariable {
    private int level;
    private HashMap<Integer, T> values;

    public MyArray() {
        values = new HashMap<Integer, T>();
    }

    public MyArray(HashMap<Integer, T> values) {
        this.values = values;
    }

    @Override
    public int getLevel() {
        return level;
    }

    public T get(int i) {
        return values.get(i);
    }

    public void put(int i, T value) {
        values.put(i, value);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public HashMap<Integer, T> getValues() {
        return values;
    }

    public void setValues(HashMap<Integer, T> values) {
        this.values = values;
    }

    public MyArray<T> clone() {
        MyArray res = new MyArray();
        Set<Integer> keys = values.keySet();
        for (Integer key : keys) {
            T tmp = (T) values.get(key).clone();
            res.values.put(key.intValue(), tmp);
        }

        return res;
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("{").append(level).append("}").append("(");
        Set<Integer> keys = values.keySet();
        for (Integer key : keys) {
            T tmp = (T) values.get(key).clone();
            res.append('[').append(key).append(',').append(tmp.toString()).append("] ");
        }
        res.append(')');
        return res.toString();
    }

    /*private ArrayList<MyInt> values;

    public MyArrayList() {
        values = new ArrayList<MyInt>();
    }

    public MyArrayList(ArrayList<MyInt> values) {
        this.values = values;
    }

    public void swap(MyArrayList another) {
        for (int i = 0; i < values.size(); i++) {
            MyInt tmp = values.get(i).clone();
            values.set(i, another.values.get(i).clone());
            another.values.set(i, tmp);
        }
    }

    public MyArrayList clone() {
        ArrayList<MyInt> res = new ArrayList<MyInt>();
        for (int i = 0; i < values.size(); i++) {
            MyInt tmp = values.get(i).clone();
            return.add(tmp);
        }
        return new MyArrayList(res);
    }

    public MyInt get(int i) {
        if (i < 0 || i >= values.size())
            throw new RuntimeException("Out of range!");
        return values.get(i);
    }

    public void add(MyInt value) {
        values.add(value);
    }

    public void set(int i, MyInt value) {
        if (i < 0 || i >= values.size())
            throw new RuntimeException("Out of range!");
        values.set(i, value);
    }*/
}
