package budget.backend.money;

import budget.backend.structures.Tuple;
import java.util.Date;

import javax.swing.text.PlainDocument;

import budget.backend.interfaces.iExchange;
import budget.backend.tags.*;
import budget.backend.utils.DataChecker;

public abstract class Exchange implements iExchange {

  protected int id; // 9-digit number
  protected Currency currency;
  protected Date date;
  protected Tag label; // miscellaneous is the default tag
  protected DataChecker dataChecker;

  /**
   * Constructor to initialise a default Exchange class, where all values are set to default
   */
  public Exchange(){
    this.id = 000000000;
    this.currency = new Currency();
    this.date = new Date();
    this.label = new tMisc();
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
      this.label = label;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * @see budget.backend.interfaces.iExchange
   */
  public int compareTo(iExchange o){

    Tuple<Currency, Tuple<Date, Integer>> key1 = new Tuple<>(this.currency, new Tuple<>(this.date, this.id));
    Tuple<Currency, Tuple<Date, Integer>> key2 = new Tuple<>(o.getCurrency(), new Tuple<>(o.getDate(), o.getId()));

    
    return key1.compareTo(key2);
  }

  /**
   * @return the id of this object
   */
  public int getId(){
    return this.id;
  }

  /**
   * @return a {@link budget.backend.money.Currency} object
   */
  public Currency getCurrency(){
    return this.currency;
  }

  /**
   * @return a Date object
   */
  public Date getDate(){
    return this.date;
  }

  /**
   * @return a {@link budget.backend.tags.Tag} object
   */
  public Tag getLabel(){
    return this.label;
  }

  /**
   * Export the class' fields to a String
   * 
   * Format: <id> [currency.amount currency.currency] <date> <label>
   * @return the String in the given format
   */
  public String toString(){
    String ret = "";

    ret += id + " " + currency.toString() + " " + date.toString() + " " + label.toString();

    return ret;
  }

}