package budget.backend.money;

import java.time.LocalDateTime;

import budget.backend.interfaces.iExchange;
import budget.backend.tags.*;

public abstract class Exchange implements iExchange {

  public int id = 000000000; // 9-digit number
  public int amount = 0; // 0 by default
  public LocalDateTime date = LocalDateTime.now();
  public String currency = "HUF"; // 3-digit indentifiers defined in budget.backend.utils.CurrencyExchanger
  public Tag label = new tMisc(); // miscellaneous is the default tag

  public int compareTo(iExchange o){
    int amount = 0;

    

    return amount;
  }

}