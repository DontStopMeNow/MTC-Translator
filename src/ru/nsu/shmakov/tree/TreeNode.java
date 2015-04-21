package ru.nsu.shmakov.tree;

import ru.nsu.shmakov.lexemes.Lexeme;

import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by kyb1k on 11.04.2015.
 */
public class TreeNode {
    Lexeme lexeme;
    ArrayList<TreeNode> children;
    TreeNode parent;
    String type;

    public TreeNode(Lexeme lexeme, ArrayList<TreeNode> children, TreeNode parent) {
        this.lexeme = lexeme;
        this.children = children;
        this.parent = parent;
        type = "";
    }

    public TreeNode(Lexeme lexeme, String type) {
        this.lexeme = lexeme;
        this.children = new ArrayList<TreeNode>();
        this.parent = null;
        this.type = type;
    }

    public TreeNode(Lexeme lexeme) {
        this.lexeme = lexeme;
        this.children = new ArrayList<TreeNode>();
        this.parent = null;
        this.type = "";
    }

    public TreeNode(Lexeme lexeme, ArrayList<TreeNode> children, TreeNode parent, String type) {
        this.lexeme = lexeme;
        this.children = children;
        this.parent = parent;
        this.type = type;
    }

    public boolean equalType(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode another = (TreeNode) o;
        return type.equals(another.type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void addChild(TreeNode child) {
        if (child != null)
            children.add(child);
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public void setLexeme(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public String serrTree() {
        StringBuilder sb = new StringBuilder();
        sb.append('<');
        sb.append(lexeme.toString());
        sb.append('>');
        for(TreeNode node : children) {
            sb.append(node.serrTree());
        }
        sb.append("</");
        sb.append(lexeme.toString());
        sb.append('>');
        return sb.toString();
    }
}
