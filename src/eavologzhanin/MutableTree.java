package eavologzhanin;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.BinaryOperator;

public class MutableTree<T extends Number> extends AbstractTree<T> {

    public MutableTree(BinaryOperator<T> adder, Comparator<T> comparator, MutableNode<T> node, T zero) {
        super(adder, comparator, node,zero);
    }

    /*
    * Входит ли узел rootSubTree в поддерево, начиная с node
    * */
    private boolean isInTree(Node<T> node, Node<T> rootSubTree){
        if(node==rootSubTree) return true;
        for(Node<T> nodes: node.getChildren()){
            if (isInTree(nodes,rootSubTree)) return true;
        }
        return false;
    }

    /*
    * Если узел имеет родителя, то просто удаляем этот узел из списка родителей, соответственно и все поддерево
    * !!!
    * Долго думал, как лучше сделать, если поддерево является самим деревом.
    * Выбирал между 2 вариантами: 1) оставить корень 2) имитировать удаление дерева целиком
    * Выбрал 2 вариант, так как пустое дерево является подмножеством деревьев.
    * !!!
    * */
    @Override
    public AbstractTree<T> removeSubtree(Node<T> rootSubTree) {
        if(isInTree(getRoot(), rootSubTree)){
            if(rootSubTree.getParent()!=null){
                ((MutableNode<T>)rootSubTree.getParent()).removeChild((MutableNode<T>) rootSubTree);
            }else{
                ((MutableNode<T>)getRoot()).setChildren(null); // Имитация удаления корня
                ((MutableNode<T>)getRoot()).setValue(zero); // после которой верну дерево с пустым корнем
            }
        }
        return this;
    }

    /*
    * Получаю коллекцию вершин с отризательной суммой поддерева, после чего
    * После чего начиная с листьев удаляю их из дерева,
    * при этом пересчитывая сумму каждого поддерева, так как она могла изменить
    * */
    @Override
    public AbstractTree<T> maximize() {
        Collection<Node<T>> negativeValues = findNegativeNodes(getRoot());
        for (Node<T> deleteNode:negativeValues){
            if(comparator.compare(getSum(deleteNode),zero) < 0){
                removeSubtree(deleteNode);
            }
        }
        return this;
    }

    @Override
    AbstractTree<T> maximize(int k) { // Кроме полного перебора ничего адекатного реализовать не получилось
        return null;
    }
}
