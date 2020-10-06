package budget.backend.stores;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.security.MessageDigest;
import java.text.MessageFormat;

import budget.backend.interfaces.iExchangeStore;
import budget.backend.money.Currency;
import budget.backend.money.Debt;
import budget.backend.money.Exchange;
import budget.backend.money.Expense;
import budget.backend.money.Income;
import budget.backend.structures.AVLSearchTree;
import budget.backend.structures.LimitedStack;
import budget.backend.structures.Tuple;
import budget.backend.tags.Tag;
import budget.backend.users.User;
import budget.backend.utils.CurrencyExchanger;
import budget.backend.utils.DataChecker;
import budget.backend.utils.Exceptions.AmbiguousDebtorException;

public class ExchangeStore implements iExchangeStore {

  //structures that make it easier to search through the Exchange objects
  private HashMap<Tag,Exchange> tagMap;
  /**
   * dateTree orders elements by date, if two have the same date then by title, if both title and name are the same, it orders by ID hence creating total order
  */
  private AVLSearchTree<Tuple<Date,Tuple<String,String>>,Exchange> dateTree;
  /**
   * amountTree orders elements by amount, if two have the same title then by date, if
   * both amount and date are the same, it orders by ID hence creating total order
   */
  private AVLSearchTree<Tuple<Currency,Tuple<Date,String>>,Exchange> amountTree;

  private DataChecker dataChecker;

  //findSimilar booster
  private LimitedStack<LinkedList<Exchange>> searchCache;
  private LimitedStack<String> searchHistory;

  /**
   * create a new ExchangeStore instance
   */
  public ExchangeStore(){
    this.tagMap = new HashMap<>();
    this.dateTree = new AVLSearchTree<>();
    this.amountTree = new AVLSearchTree<>();
    this.dataChecker = new DataChecker();
    this.searchCache = new LimitedStack<>();
    this.searchHistory = new LimitedStack<>();
  }


  @Override
  public boolean readFile(BufferedReader in) {
    // TODO decrypt data
    return false;
  }

  @Override
  public boolean writeFile(FileWriter out) {
    // TODO encrypt data
    return false;
  }

  @Override
  public boolean add(String composedString) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Exchange delete(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Exchange find(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Exchange define(User user, char type, Currency currency, String title, LinkedList<Tag> labels)
      throws AmbiguousDebtorException {
    try {
      dataChecker.verifyUser(user);
      dataChecker.verifyType(type); 
      Exchange ret = null;
      String newID = this.generateId(user.getId(), new Date(), type);
      switch (type){
        case iExchangeStore._INCOME:
          ret = new Income(newID, currency, new Date(), labels);
          break;
        case iExchangeStore._EXPENSE:
          ret = new Expense(newID, currency, new Date(), labels);
          break;
        case iExchangeStore._DEBT:
          ret = new Debt(newID, currency, new Date(), labels, new User(), user);
          throw new AmbiguousDebtorException((Debt)ret);

        default: //undefined 
          throw new IllegalArgumentException("undefined Exchange type");
      }  
      //put into structures 
      for (Tag t : labels)
        tagMap.put(t, ret);
      Tuple<String, String> dateInner = new Tuple<String,String>(title, newID);
      Tuple<Date,Tuple<String,String>> dateOuter = new Tuple<>(ret.getDate(),dateInner);
      dateTree.insert(dateOuter, ret);
      Tuple<Date, String> amountInner = new Tuple<Date, String>(ret.getDate(), newID);
      Tuple<Currency, Tuple<Date, String>> amountOuter = 
                    new Tuple<>(ret.getCurrency(), amountInner);
      amountTree.insert(amountOuter, ret);
      return ret;
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Exchange define(User user, char type, Currency currency, String title, LinkedList<Tag> labels, Date date)
      throws AmbiguousDebtorException {
    try {
      dataChecker.verifyUser(user);
      dataChecker.verifyType(type);
      Exchange ret = null;
      String newID = this.generateId(user.getId(), date, type);
      switch (type) {
        case iExchangeStore._INCOME:
          ret = new Income(newID, currency, date, labels);
          break;
        case iExchangeStore._EXPENSE:
          ret = new Expense(newID, currency, date, labels);
          break;
        case iExchangeStore._DEBT:
          ret = new Debt(newID, currency, date, labels, new User(), user);
          throw new AmbiguousDebtorException((Debt) ret);

        default: // undefined
          throw new IllegalArgumentException("undefined Exchange type");
      }
      // put into structures
      for (Tag t : labels)
        tagMap.put(t, ret);
      Tuple<String, String> dateInner = new Tuple<String, String>(title, newID);
      Tuple<Date, Tuple<String, String>> dateOuter = new Tuple<>(ret.getDate(), dateInner);
      dateTree.insert(dateOuter, ret);
      Tuple<Date, String> amountInner = new Tuple<Date, String>(ret.getDate(), newID);
      Tuple<Currency, Tuple<Date, String>> amountOuter = new Tuple<>(ret.getCurrency(), amountInner);
      amountTree.insert(amountOuter, ret);
      return ret;
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Exchange defineDebt(User creditor, Currency currency, String title, LinkedList<Tag> labels, User debtor) {
    try {
      dataChecker.verifyUser(creditor);
      dataChecker.verifyUser(debtor);
      String newID = this.generateId(creditor.getId(), new Date(), iExchangeStore._DEBT);
      Exchange ret = new Debt(newID, currency, new Date(), labels, debtor, creditor);
      // put into structures
      for (Tag t : labels)
        tagMap.put(t, ret);
      Tuple<String, String> dateInner = new Tuple<String, String>(title, newID);
      Tuple<Date, Tuple<String, String>> dateOuter = new Tuple<>(ret.getDate(), dateInner);
      dateTree.insert(dateOuter, ret);
      Tuple<Date, String> amountInner = new Tuple<Date, String>(ret.getDate(), newID);
      Tuple<Currency, Tuple<Date, String>> amountOuter = new Tuple<>(ret.getCurrency(), amountInner);
      amountTree.insert(amountOuter, ret);
      return ret;
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Exchange defineDebt(User creditor, Currency currency, String title, LinkedList<Tag> labels, Date date,
      User debtor) {
    try {
      dataChecker.verifyUser(creditor);
      dataChecker.verifyUser(debtor);
      String newID = this.generateId(creditor.getId(), new Date(), iExchangeStore._DEBT);
      Exchange ret = new Debt(newID, currency, new Date(), labels, debtor, creditor);
      // put into structures
      for (Tag t : labels)
        tagMap.put(t, ret);
      Tuple<String, String> dateInner = new Tuple<String, String>(title, newID);
      Tuple<Date, Tuple<String, String>> dateOuter = new Tuple<>(ret.getDate(), dateInner);
      dateTree.insert(dateOuter, ret);
      Tuple<Date, String> amountInner = new Tuple<Date, String>(ret.getDate(), newID);
      Tuple<Currency, Tuple<Date, String>> amountOuter = new Tuple<>(ret.getCurrency(), amountInner);
      amountTree.insert(amountOuter, ret);
      return ret;
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public LinkedList<Exchange> findSimilar(String pattern) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getAllByDate(char type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getAllByAmount(char type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagByDate(Tag t, char type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagByAmount(Tag t, char type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagsByDate(LinkedList<Tag> ts, char type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagsByAmount(LinkedList<Tag> ts, char type) {
    // TODO Auto-generated method stub
    return null;
  }

  private final String generateId(String userID, Date date, char type) throws IllegalArgumentException{
    String ret = "";
    if (type == 'u') throw new IllegalArgumentException("Undefined type of Exchange object");
    else {
      try {
        ret += type;
        int salt = (int) Math.random() * 10000;
        MessageDigest md = MessageDigest.getInstance("MD5");
        // Add password bytes to digest
        String concat = userID+date+salt;
        md.update(concat.getBytes());
        // Get the hash's bytes
        byte[] bytes = md.digest();
        // This bytes[] has bytes in decimal format;
        // Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
          sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        // Get complete hashed password in hex format
        ret += "_" + sb.toString();
      } catch (Exception e) {
        throw new IllegalArgumentException("unable to generate ID");
      }
      
    }
    return ret;
  }

  
  
}