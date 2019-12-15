package eavologzhanin;

import java.util.Collection;

public interface Node<T extends Number> extends Wrapper<T>{
    Node<T> getParent();
    Node<T> getChild(int index);
    Collection<Node<T>> getChildren();
    void print(int indents);
}
