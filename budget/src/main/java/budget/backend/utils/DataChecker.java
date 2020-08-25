package budget.backend.utils;

import java.lang.IllegalArgumentException;
import java.util.Date;

import budget.backend.money.Currency;
import budget.backend.utils.CurrencyExchanger;

/**
 * Class to verify all posslible, user defined data inputs in the package
 */
public class DataChecker {
  
  public void verifyCurrency(String currency) throws IllegalArgumentException
  {
    CurrencyExchanger ex = new CurrencyExchanger();
    if (!ex.isKnown(currency))
      throw new IllegalArgumentException("unknown currency");

  }

  public void verifyCurrency(Currency currency) throws IllegalArgumentException
  {
    CurrencyExchanger ex = new CurrencyExchanger();
    if (!ex.isKnown(currency.getCurrency()))
      throw new IllegalArgumentException("unknown currency");

  }

  public void verifyId(int id) throws IllegalArgumentException{
    if (id < 100000000 || id > 999999999)
      throw new IllegalArgumentException("Wrong length of ID");
    //TODO: verify that id is unique
  }

  public void verifyDate(Date date) throws IllegalArgumentException {
    Date now = new Date();
    if (now.compareTo(date) < 0) throw new IllegalArgumentException("Given date is in the future!");
  }
}