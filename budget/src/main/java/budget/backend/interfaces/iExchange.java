package budget.backend.interfaces;
import java.time.LocalDateTime;
import budget.backend.tags.*;

public interface iExchange extends Comparable<iExchange>{

  public String toString();

  public int getId();

  /**
   * 
   * @return return the amount stored by this object. 
   */
  public int getAmount();

  /**
   * @return return a LocalDateTime object representing the date of creation
   */
  public LocalDateTime getDate();

  /**
   * @return the currency of this object
   */
  public String getCurrency();

  /**
   * @return a list of Tag objects
   */
  public Tag getLabel();

  /**
   * Compare two iExchange objects by the following rules
   * 
   *  Primarily, total order is decided by the amount in the default HUF currency
   * 
   *  Secondarily, by the date newer iExchange objects are "bigger"
   * 
   *  Lastly by their IDs. if both the amount and the date are the same, their unique ID will ensure that a total order can be maintained.
   * 
   * @param o   an iExchange object
   * 
   * @return x<0 if the given object is less than this object
   * 
   * @return x>0 otherwise. x=0 is not possible in this implementation
   */
  public int compareTo(iExchange o);

}

