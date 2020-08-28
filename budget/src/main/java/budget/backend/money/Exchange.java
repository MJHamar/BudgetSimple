package budget.backend.money;

import budget.backend.structures.Tuple;
import java.util.Date;
import java.util.LinkedList;

import budget.backend.interfaces.iExchange;
import budget.backend.tags.*;
import budget.backend.utils.DataChecker;

public abstract class Exchange implements iExchange {

  protected int id; // 9-digit number
  protected Currency currency;
  protected Date date;
  protected LinkedList<Tag> labels; // miscellaneous is the default tag
  protected DataChecker dataChecker;
  protected String groupID; // user id in case of a simple income/expense

  /**
   * Constructor to initialise a default Exchange class, where all values are set to default
   */
  public Exchange(){
    this.id = 000000000;
    this.currency = new Currency();
    this.date = new Date();
    this.labels = new LinkedList<>();
    this.labels.add(new Tag());
    this.dataChecker = new DataChecker();
  }
  
  /**
   * Constructor to make a specified Exchange, with all fields specified
   * @param id
   * @param currency
   * @param date
   * @param label
   */
  public Exchange(Currency currency, Date date, Tag label){
    this.dataChecker = new DataChecker();

    try {
      dataChecker.verifyCurrency(currency);
      this.currency = currency;
      dataChecker.verifyDate(date);
      this.date = date;
      this.labels = new LinkedList<>();
      this.labels.add(label);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * @see budget.backend.interfaces.iExchange
   */
  @Override
  public int compareTo(iExchange o){

    Tuple<Currency, Tuple<Date, Integer>> key1 = new Tuple<>(this.currency, new Tuple<>(this.date, this.id));
    Tuple<Currency, Tuple<Date, Integer>> key2 = new Tuple<>(o.getCurrency(), new Tuple<>(o.getDate(), o.getId()));

    
    return key1.compareTo(key2);
  }

  /**
   * @return the id of this object
   */
  @Override
  public int getId(){
    return this.id;
  }

  /**
   * @return a {@link budget.backend.money.Currency} object
   */
  @Override
  public Currency getCurrency(){
    return this.currency;
  }

  /**
   * @return a Date object
   */
  @Override
  public Date getDate(){
    return this.date;
  }

  /**
   * @return a {@link budget.backend.tags.Tag} object
   */
  @Override
  public LinkedList<Tag> getLabel(){
    return this.labels;
  }
  @Override
  public void addLabel(Tag label){
    this.labels.add(label);
  }

  @Override
  public String getGroupID(){
    return this.groupID;
  } 
  @Override
  public void setGroupID(String groupID) {
    try {
      this.dataChecker.verifyGroupID(groupID);
      this.groupID = groupID;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Export the class' fields to a String
   * 
   * Format: <id> [currency.amount currency.currency] <date> <label>
   * @return the String in the given format
   */
  @Override
  public String toString(){
    String ret = "";

    ret += id + " " + currency.toString() + " " + date.toString() + " " + labels.toString() + " " + this.groupID;

    return ret;
  }

}