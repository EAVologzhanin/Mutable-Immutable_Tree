package eavologzhanin;

import java.util.*;

public class MutableNode<T extends Number> implements Node<T>{
    private T value;
    private MutableNode<T> parent;
    private Collection<Node<T>> children;

    public MutableNode(T value, MutableNode<T> parent, Collection<Node<T>> children) {
        this.value = value;
        this.parent = parent;
        this.children = new ArrayList<>();
        if(children!=null) this.children.addAll(children);
        if(parent!=null) this.parent.addChild(this);
    }
    public MutableNode(T value, MutableNode<T> parent) {
        this(value, parent, null);
    }

    public MutableNode(T value) {
        this.value = value;
        parent = null;
        children = new ArrayList<>();
    }

    public void setValue(T value){
        this.value = value;
    }

    public void setParent(MutableNode<T> parent){
        this.parent = parent;
    }

    public void setChildren(Collection<MutableNode<T>> children){
        this.children.clear();
        if(children==null) this.children = new ArrayList<Node<T>>();
        else{
            for (MutableNode<T> child : children) {
                addChild(child);
            }
        }
    }
    public void addChild(MutableNode<T> child){
        this.children.add(child);
        child.setParent(this);
    }
    public void removeChild(MutableNode<T> child){
        this.children.remove(child);
    }

    @Override
    public Node<T> getParent() {
        return parent;
    }

    @Override
    public Node<T> getChild(int index) {
        if(index<children.size()){
            return ((ArrayList<Node<T>>)children).get(index);
        } else return null;
    }

    @Override
    public Collection<Node<T>> getChildren() {
        return children;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void print(int indents) {
        String str = "";
        for (int i = 0; i < indents; i++) {
            str+="\t" +"\t";
        }
        System.out.println(str);
        System.out.println(str+getValue());

    }
}
