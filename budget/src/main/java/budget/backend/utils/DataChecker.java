package budget.backend.utils;

import java.lang.IllegalArgumentException;

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

}