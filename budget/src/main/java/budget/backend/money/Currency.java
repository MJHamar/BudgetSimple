package budget.backend.money;

import budget.backend.utils.CurrencyExchanger;
import budget.backend.utils.DataChecker;
import java.lang.IllegalArgumentException;

public class Currency implements Comparable<Currency> {
  
  private int amount; 
  private String currency;
  private DataChecker dataChecker;

  /**
   * Constructor to make a default instance
   * 
   * Default values are: amount = 0, currency = "HUF"
   */
  public Currency() {
    this.dataChecker = new DataChecker();
    this.amount = 0;
    this.currency = "HUF";
  }

  /**
   * Constructor to make an instance with defined amount and currency
   * 
   * @param amount
   * @param currency
   * @throws IllegalArgumentException
   */
  public Currency(int amount, String currency) throws IllegalArgumentException{
    this.dataChecker = new DataChecker();
    this.amount = amount;
    try {
      dataChecker.verifyCurrency(currency);
      this.currency = currency;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    }
  }

  /**
   * Constructor to make an instance from a composed String defined by @see Currency.toString()
   * 
   * @param composedString
   * @throws IllegalArgumentException
   */
  public Currency(String composedString) throws IllegalArgumentException{
    this.dataChecker = new DataChecker();
    try{
      dataChecker.verifyCurrency(composedString);
      String help = composedString.substring(1, composedString.indexOf(" "));
      this.amount = Integer.valueOf(help);
      help = composedString.substring(composedString.indexOf(" ")+1, composedString.length()-2);
    } catch (Exception e){
      System.out.println(e.getMessage());
      throw e;
    }
  }

  /**
   * 
   * @param amount
   */
  public void setAmount(int amount){
    this.amount = amount;
  }

  /**
   * 
   * @return the amount
   */
  public int getAmount(){
    return this.amount;
  }

  /**
   * 
   * @param currency
   * @throws IllegalArgumentException
   */
  public void setCurrency(String currency) throws IllegalArgumentException{
    try {
      dataChecker.verifyCurrency(currency);
      this.currency = currency;
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  /**
   * 
   * @return the currency
   */
  public String getCurrency(){
    return this.currency;
  }

  /**
   * Decide which object is bigger based on the amounts related to the object in the given object's currency
   * 
   * @param o the object to be compared.
   * 
   * @return a negative integer, zero, or a positive integer as this object is
   *         less than, equal to, or greater than the specified object.
   */
  @Override
  public int compareTo(Currency o) {

    CurrencyExchanger ex = new CurrencyExchanger();
    int thisConv = ex.convert(this.amount, this.currency, o.getCurrency());
    
    return thisConv-o.getAmount();

  }

  /**
   * Export the class' fields to a String. 
   * 
   * Format: [<amount> <currency>];
   * @return the composed String with the given format.
   */
  public String toString() {
    String ret = "";

    ret += "[" + amount + " " + currency + "]";

    return ret;
  }

}