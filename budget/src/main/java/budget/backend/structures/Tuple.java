package budget.backend.structures;

import java.lang.Comparable;
import java.lang.Exception.*;

/**
 * Java implementation of a Tuple, which is able to store at most two Comparable instances. The class itself is an instance of Comparable, which gives the ability to create arbitrary layered tuples. 
 * Comparison returns 0 IFF the two compared objects are equal.
 * Ordering of tuples is done by applying compareTo() on the first element and only evaluating the second element, if the result from the first comparison sown equality. This ensures that the total order property is not violated. 
 * With this method, we can define a totally ordered set of Object containing two comparable instances.
 * comparison of tuples is only defined on tuples of the same type. I.e. Tuple<String,Date> cannot be compared to Tuple<Integer, Float> 
 * @param <E> A Comparable instance 
 * @param <F> A Comparable instance
 */
public class Tuple<E extends Comparable<E>,F extends Comparable<F>> implements Comparable<Tuple<E,F>> {

  private E first;
  private F second;

  public Tuple(){
    this.first = null;
    this.second = null;
  }
  
  public Tuple(E first){
    this.first = first;
    this.second = null;
  }

  public Tuple(E first, F second){
    this.first = first;
    this.second = second;
  }

  public void setFirst(E first){
    this.first = first;
  }

  public void setSecond(F second) {
    this.second = second;
  }

  public E getFirst(){
    return this.first;
  }

  public F getSecond(){
    return this.second;
  }

  public boolean equals(Tuple anotherTuple) {
    return ((this.first == anotherTuple.getFirst()) && (this.second == anotherTuple.getSecond()));
  }

  @SuppressWarnings("unchecked")
  @Override
  public int compareTo(Tuple anotherTuple){
    try {
      //The first element has higher priority, therefore second is only evaluated if the first argument returns 0
      int comp = first.compareTo((E)anotherTuple.getFirst());
      if (comp == 0)
        return second.compareTo((F)anotherTuple.getSecond());
      return comp;
    } catch (Exception e) {
      System.out.println("!!!!!!!!!!!!Exception!!!!!!!!!!!!!!");
      System.out.println(e);
      System.out.println("Argument types do not match");
      return 0;
    }
  }

  @Override
  public String toString(){
    return "<" + this.first + ";" + this.second + ">";
  }


  
}