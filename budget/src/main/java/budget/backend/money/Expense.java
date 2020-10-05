package budget.backend.money;

import java.util.Date;
import java.util.LinkedList;

import budget.backend.tags.Tag;
import budget.backend.utils.Exceptions.CurrencyException;

public class Expense extends Exchange {

  public Expense(String id, Currency currency, Date date, LinkedList<Tag> labels) {
    super(id, currency, date, labels);  
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
      if (currency.getAmount() > 0)
        throw new CurrencyException("Expense amount must be negative!");
      super.currency = currency;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }



}