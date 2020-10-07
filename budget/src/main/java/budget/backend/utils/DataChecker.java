package budget.backend.utils;

import java.lang.IllegalArgumentException;
import java.util.Date;

import budget.backend.interfaces.iExchange;
import budget.backend.interfaces.iExchangeStore;
import budget.backend.interfaces.iTag;
import budget.backend.money.Currency;
import budget.backend.tags.tRoot;
import budget.backend.users.User;
import budget.backend.tags.Tag;
import budget.backend.utils.CurrencyExchanger;

/**
 * Class to verify all posslible, user defined data inputs in the package
 */
public class DataChecker {
  
  public void verifyCurrency(String currency) throws IllegalArgumentException
  {
    //first, find out if it is a composed Currency or just a 3letter abbrevation
    if (currency.length() != 3){
      try {
        currency = currency.substring(currency.indexOf(" "), currency.length()-2);
      } catch (Exception e) {
        throw new IllegalArgumentException("unrecognisable composed currency string");
      }
    }
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

  public void verifyId(String id) throws IllegalArgumentException
  {
    //TODO: verify the hashed ID
    //Hashed Id has length 34, and starts with [uied]_ 
    char type = id.charAt(0);
    if (type != iExchange._DEBT && type != iExchange._EXPENSE && type != iExchange._INCOME)
      throw new IllegalArgumentException("Unable to define type of exchange");
    if (id.length() != 34)
      throw new IllegalArgumentException("Wrong length of ExchangeID");
    
  }

  public void verifyDate(Date date) throws IllegalArgumentException
  {
    Date now = new Date();
    if (now.compareTo(date) < 0) throw new IllegalArgumentException("Given date is in the future!");
  }

  public void verifyGroupID(String groupID) throws IllegalArgumentException
  {
    //TODO: figure out what makes a groupID a groupID
  }

  public void verifyTag(Tag t) throws IllegalArgumentException
  {
    try {
      verifyTagId(t.getId());
      if (t.getName() == "" || t.getDescendants() == null || t.getParent() == null)
        throw new IllegalArgumentException("Badly set Tag");
    } catch (Exception e) {
      throw new IllegalArgumentException("Badly set Tag: " + t);
    }
    
    
  }

  public void verifyTRoot(tRoot t) throws IllegalArgumentException
  {
    if (t.getDescendants() == null || t.getName() != "root" || t.getId() != "000000000")
      throw new IllegalArgumentException("Badly set tRoot");
  }

  public void verifyTagId(String id) throws IllegalArgumentException
  {
    if (id == "000000000") throw new IllegalArgumentException("do not use the id reserved for tRoot");
    if (Integer.valueOf(id) < 100000000 || Integer.valueOf(id) > 999999999) throw new IllegalArgumentException("erroneous Tag id");
  }

  public void verifyUser(User user) throws IllegalArgumentException{
    //TODO
  }

  public void verifyType(char type) throws IllegalArgumentException{
    if (type != iExchangeStore._DEBT && type != iExchangeStore._INCOME && type != iExchangeStore._EXPENSE && type != iExchangeStore._UNDEFINED)
      throw new IllegalArgumentException("badly set Exchange type!");
  }
}