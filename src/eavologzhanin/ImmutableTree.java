package eavologzhanin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.BinaryOperator;

public class ImmutableTree<T extends  Number> extends AbstractTree<T> {

    public ImmutableTree(BinaryOperator<T> adder, Comparator<T> comparator, Node<T> node, T zero) {
        super(adder, comparator, node, zero);
    }

    /*
    * Метод удаляет переданный узел в новой копии дерева
    * */
    @Override
    public AbstractTree<T> removeSubtree(Node<T> rootSubTree) {
        if(rootSubTree.getParent()==null) return new ImmutableTree<>(adder,comparator,new ImmutableNode<T>(zero,null),zero);
        return new ImmutableTree<>(adder,comparator,new ImmutableNode<T>(null,getRoot(),rootSubTree),zero);
    }

    /*
    * Ищет вершину с отрицательной подсуммой
    * */
    public Node<T> findNegativeNode(Node<T> node){
        for (Node<T> nodes : node.getChildren()) {
            Node<T> checkNode = findNegativeNode(nodes);
            if(checkNode!=null) return checkNode;
        }
        if(comparator.compare(getSum(node), zero)<0){
            return node;
        }
        return null;
    }
    /*
    * Возвращает максимальное дерево, удаляя из копии коллекцию отрицательных поддеревьев*/
    @Override
    public AbstractTree<T> maximize() {
        ImmutableTree<T> newTree = new ImmutableTree<T>(adder,comparator,new ImmutableNode<T>(null,getRoot(),null), zero);
        Node<T> delete;
        do{
            delete = findNegativeNode(newTree.getRoot());
            if(delete!=null) newTree= (ImmutableTree<T>) newTree.removeSubtree(delete);
        }while(delete!=null);
        return newTree;
    }

    @Override
    AbstractTree<T> maximize(int k) {
        return null;
    } // Полный перебор..
}
