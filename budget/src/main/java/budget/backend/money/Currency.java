package budget.backend.money;

import budget.backend.utils.CurrencyExchanger;
import budget.backend.utils.DataChecker;
import java.lang.IllegalArgumentException;

public class Currency implements Comparable<Currency> {
  
  private int amount; 
  private String currency;
  private DataChecker dataChecker;

  public Currency() {
    this.dataChecker = new DataChecker();
    this.amount = 0;
    this.currency = "HUF";
  }

  public Currency(int amount, String currency) throws IllegalArgumentException{
    this.dataChecker = new DataChecker();
    this.amount = amount;
    try {
      dataChecker.verifyCurrency(currency);
      this.currency = currency;
    } catch (IllegalArgumentException e) {
      System.out.println("  [ERROR]:        " + e.getMessage());
      throw e;
    }
  }


  public void setAmount(int amount){
    this.amount = amount;
  }

  public int getAmount(){
    return this.amount;
  }

  public void setCurrency(String currency) throws IllegalArgumentException{
    try {
      dataChecker.verifyCurrency(currency);
      this.currency = currency;
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

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

}