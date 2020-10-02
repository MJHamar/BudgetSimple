package budget.backend.structures;

import java.util.LinkedList;

public class LimitedStack<E>
{
  
  private LinkedList<E> stack;
  private int limit;

  /**
   * construct a simple limitless stack
   */
  public LimitedStack(){
    this.limit = 0;
    this.stack = new LinkedList<>();
  }

  /**
   * construct a stack with a limit. limit=0 means that the stack is limitless. Negative arguments are not allowed
   * @param limit
   * @throws IllegalArgumentException if the argument is negative
   */
  public LimitedStack(int limit) throws IllegalArgumentException{
    setLimit(limit);
    this.stack = new LinkedList<>();
  }

  /**
   * push a new object to the stack and maintain the limit if necessary
   * @param o
   * @return the object that has been pushed to the stack
   */
  public E push(E o)
  {
    stack.addFirst(o);
    if (limit > 0 && size() > limit)
      stack.removeLast();
    return o;
  }

  /**
   * pop the top element of the stack
   * @return the popped element, or null, if the stack is empty
   */
  public E pop()
  {
    if (size() > 0) return stack.removeFirst();
    else return null;
  }

  /**
   * @return the top element of the stack without popping it
   */
  public E peek()
  {
    return stack.getFirst();
  }

  /**
   * Returns the 1-based position where an object is on this stack. If the object
   * o occurs as an item in this stack, this method returns the distance from the
   * top of the stack of the occurrence nearest the top of the stack; the topmost
   * item on the stack is considered to be at distance 1. The equals method is
   * used to compare o to the items in this stack.
   * 
   * @param o
   * @return the 1-based position from the top of the stack where the object is
   *         located; the return value -1 indicates that the object is not on the
   *         stack.
   */
  public int search(E o)
  {
    int ret = 1;
    for (E e : stack)
    {
      if (e.equals(o)) 
        return ret;
      ret++;
    }
    return -1;

  }

  /**
   * 
   * @return the size of the stack
   */
  public int size()
  {
    return stack.size();
  }

  /**
   * 
   * @return true if the stack if empty
   */
  public boolean empty() {
    return stack.size() == 0;
  }

  public void clear(){
    this.stack = new LinkedList<>();
  }

  /**
   * Set the limit of the stack. If the argument is 0, the stack will be limitless. Negative agruments are not allowed
   * @param limit
   * @throws IllegalArgumentException
   */
  public void setLimit(int limit) throws IllegalArgumentException
  {
    if (limit < 0) throw new IllegalArgumentException("Stack limit has to be positive or 0, if undefined");
    else
    {
      this.limit = limit;
    }
  }

  public int getLimit(){
    return this.limit;
  }

  public boolean isEmpty(){
    return stack.isEmpty();
  }

  
}