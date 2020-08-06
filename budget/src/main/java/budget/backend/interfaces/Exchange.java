package budget.backend.interfaces;
import java.time.LocalDateTime;
import budget.backend.tags.*;

public interface Exchange extends Comparable<Exchange>{

  public int id = 000000000; //9-digit number
  public int amount = 0; // 0 by default
  public LocalDateTime date = LocalDateTime.now();
  public String currency = "HUF"; //3-digit indentifiers defined in budget.backend.utils.CurrencyExchanger
  public Tag label = new tMisc(); //miscellaneous is the default tag

  public boolean readExchange(String path);

  public boolean exportExchange(String path);

  public String toString();

  public int getId();

  public int getAmount();

  public LocalDateTime getDate();

  public String getCurrency();

  public Tag getLabel();

  public int compareTo(Exchange o);

}

