package budget.backend.interfaces;

import java.util.Date;
import java.util.LinkedList;

import budget.backend.money.Currency;
import budget.backend.tags.*;

public interface iExchange extends Comparable<iExchange>{

  public String toString();

  public int getId();
  public void setId(int id);

  /**
   * @return return a Date object representing the date of creation
   */
  public Date getDate();
  public void setDate(Date date);

  /**
   * @return the currency of this object
   */
  public Currency getCurrency();
  public void setCurrency(Currency currency);

  /**
   * @return a list of Tag objects
   */
  public LinkedList<Tag> getLabel();
  public void addLabel(Tag label);

  public String getGroupID();
  public void setGroupID(String groupID);

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
   * @return x<0 if this object is less than the given object
   * 
   * @return x>0 otherwise. x=0 is not possible in this implementation
   */
  public int compareTo(iExchange o);

 
}

