package budget.backend.money;

import java.util.Date;

import budget.backend.tags.Tag;
import budget.backend.utils.Exceptions.CurrencyException;

public class Income extends Exchange {

  public Income(String id, Currency currency, Date date, Tag label){
    super(currency, date, label);
    setId(id);
  }

  @Override
  public void setId(String id) {
    try {
      super.dataChecker.verifyId(id);
      super.id = id;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
  }

  @Override
  public void setDate(Date date) {
    try {
      super.dataChecker.verifyDate(date);
      super.date = date;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void setCurrency(Currency currency) throws CurrencyException {
    try {
      super.dataChecker.verifyCurrency(currency);
      if (currency.getAmount() < 0) 
        throw new CurrencyException("Income amount must be positive!");
      super.currency = currency;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  
}