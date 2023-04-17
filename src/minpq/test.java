package minpq;

import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[]args){
//        ExtrinsicMinPQ minPQ = new OptimizedHeapMinPQ();
        ExtrinsicMinPQ minPQ = new HeapMinPQ();
//        ExtrinsicMinPQ minPQ = new UnsortedArrayMinPQ();
//        ExtrinsicMinPQ minPQ = new DoubleMapMinPQ();
        minPQ.add("5",5);
        minPQ.add("1",1);
        minPQ.add("2",2);
        minPQ.add("3",3);
        minPQ.add("4",4);
        minPQ.add("6",6);

//        ((UnsortedArrayMinPQ)minPQ).printList();
//        ((HeapMinPQ)minPQ).printList();
//        ((OptimizedHeapMinPQ) minPQ).printList();

        System.out.println("-----------");
        System.out.println(minPQ.contains("4"));
        System.out.println(minPQ.peekMin());
        minPQ.removeMin();
        System.out.println(minPQ.peekMin());
        minPQ.changePriority("6",1);
        System.out.println(minPQ.peekMin());
        System.out.println(minPQ.size());
        System.out.println(minPQ.peekMin());
        System.out.println("-----------");

//        ((UnsortedArrayMinPQ)minPQ).printList();
//        ((HeapMinPQ) minPQ).printList();
//        ((OptimizedHeapMinPQ) minPQ).printList();
//        ((OptimizedHeapMinPQ) minPQ).indexMap();
        System.out.println("-----------");
        System.out.println(minPQ.removeMin());
        System.out.println(minPQ.removeMin());
        System.out.println(minPQ.removeMin());
        System.out.println(minPQ.removeMin());
        System.out.println(minPQ.removeMin());
    }
}
