package stack;

import java.util.LinkedList;

public class TqsStack<T> {
    private LinkedList<T> backingList;
    private int sizelimit;
    TqsStack(){backingList = new LinkedList<>(); sizelimit = Integer.MAX_VALUE;
    }
    TqsStack(int limit){backingList = new LinkedList<>(); sizelimit = limit;}
    public void push(T item){
        if (backingList.size()>=sizelimit){throw new IllegalStateException();}
        backingList.add(item);}
    public T pop(){return backingList.removeLast();}
    public boolean isEmpty(){return backingList.isEmpty();}
    public T peek(){ return backingList.getLast();}
    public int size(){return backingList.size();}
}
