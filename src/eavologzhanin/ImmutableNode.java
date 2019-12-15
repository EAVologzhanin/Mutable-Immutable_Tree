package eavologzhanin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

public class ImmutableNode <T extends Number> implements Node<T> {
    private T value;
    private ImmutableNode<T> parent;
    private Collection<Node<T>> children;

    public ImmutableNode(T value, ImmutableNode<T> parent, Collection<Node<T>> children){
        this.value = value;
        this.parent = parent;
        this.children = Objects.requireNonNullElseGet(children, ArrayList::new);
    }
    public ImmutableNode(T value, ImmutableNode<T> parent){
        this.value = value;
        this.parent = parent;
        this.children = new ArrayList<>();
    }
    ImmutableNode(ImmutableNode<T> parent, Node<T> forCopy, Node<T> rootSubTree){
        this.parent = parent;
        this.value = forCopy.getValue();
        Collection<Node<T>> newChildren = new ArrayList<Node<T>>();
        for (Node<T> nextIter : forCopy.getChildren()) {
            if (nextIter != rootSubTree) {
                newChildren.add(new ImmutableNode<T>(this, nextIter, rootSubTree));
            }
        }
        this.children = newChildren;
    }
    public ImmutableNode(T i, ImmutableNode<T> ImmutableNode, Function<ImmutableNode<T>, Collection<? extends Node<T>>> immutableNodeCollectionFunction) {
        value = i;
        parent = ImmutableNode;
        if(immutableNodeCollectionFunction!=null){
            children = (Collection<Node<T>>) immutableNodeCollectionFunction.apply(this);
        }
    }

    @Override
    public Node<T> getParent() {
        return this.parent;
    }

    @Override
    public Node<T> getChild(int index) {
        if(index<children.size()){
            return ((ArrayList<Node<T>>)children).get(index);
        } else return null;
    }

    @Override
    public Collection<Node<T>> getChildren() {
        return this.children;
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

    @Override
    public T getValue() {
        return value;
    }
}
