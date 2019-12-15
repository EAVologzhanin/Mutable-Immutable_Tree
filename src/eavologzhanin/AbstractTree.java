package eavologzhanin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.BinaryOperator;

public abstract class AbstractTree<T extends Number> {
   private Node<T> root;
   BinaryOperator<T> adder;
   private T sum; // Не пригодилась
   Comparator<T> comparator;
   T zero;

   AbstractTree(BinaryOperator<T> adder, Comparator<T> comparator, Node<T> node, T zero) {
      this.adder = adder;
      this.comparator = comparator;
      this.root = node;
      this.zero = zero;
   }
   /*
   * Ищет все точки суммы поддеревьев которых меньше 0*/
   public Collection<Node<T>> findNegativeNodes(Node<T> node){
      Collection<Node<T>> collection = new ArrayList<>();
      for (Node<T> nodes : node.getChildren()) {
         collection.addAll(findNegativeNodes(nodes));
      }
      if(comparator.compare(getSum(node), zero)<0){
             collection.add(node);
      }
      return collection;
   }

   public Node<T> getRoot(){
      return root;
   }

   public T getSum(){
      return getSum(root);
   }
   /*
   * рекурсивный поиск суммы, начиная с заданного узла*/
   public T getSum(Node<T> rootSubTree){
      T subSum = zero;
      for(Node<T> nodes: rootSubTree.getChildren()){
         subSum = adder.apply(subSum, getSum(nodes));
      }
      return adder.apply(subSum, rootSubTree.getValue());
   }

   public int getSize(){
      return getSize(root);
   }

   public int getSize(Node<T> node){
      int size = 1;
      for (Node<T> tNode : node.getChildren()) {
          size += getSize(tNode);
      }
      return size;
   }

   abstract AbstractTree<T> removeSubtree(Node<T> rootSubTree);
   abstract AbstractTree<T> maximize();
   abstract AbstractTree<T> maximize(int k);

   private void print(Node<T> node, Integer n) {
      if (node.getParent() == null) System.out.println(getRoot().getValue().toString());
      n++;
      for (Node<T> tNode : node.getChildren()) {
         tNode.print(n);
         print(tNode, n);
      }
   }

   public void print(){
      print(root,0);
   }
}
