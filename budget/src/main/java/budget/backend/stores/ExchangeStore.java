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
import budget.backend.money.Income;
import budget.backend.structures.AVLSearchTree;
import budget.backend.structures.LimitedStack;
import budget.backend.tags.Tag;
import budget.backend.users.User;
import budget.backend.utils.DataChecker;
import budget.backend.utils.Exceptions.AmbiguousDebtorException;

public class ExchangeStore implements iExchangeStore {

  //structures that make it easier to search through the Exchange objects
  private HashMap<Tag,Exchange> tagMap;
  /**
   * dateTree orders elements by date, if two have the same date then by title, if both title and name are the same, it orders by ID hence creating total order
  */
  private AVLSearchTree<Date,Exchange> dateTree;
  /**
   * nameTree orders elements by title, if two have the same title then by date, if
   * both title and name are the same, it orders by ID hence creating total order
   */
  private AVLSearchTree<Date,Exchange> nameTree;

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
    this.nameTree = new AVLSearchTree<>();
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
      if (type == iExchangeStore._UNDEFINED)
        throw new IllegalArgumentException("undefined Exchange type");
      else {
        String newID = this.generateId(user.getId(), new Date(), type);
        switch (type){
          case iExchangeStore._INCOME:
            Exchange ret = new Income(newID, currency, new Date(), labels);
        }
      }
      
    } catch (Exception e) {
      throw e;
    }
    return null;
  }

  @Override
  public Exchange define(User user, char type, Currency currency, String title, LinkedList<Tag> labels, Date date)
      throws AmbiguousDebtorException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Debt defineDebt(User user, Currency currency, String title, LinkedList<Tag> labels, User debtor) {
    // TODO Auto-generated method stub
    return null;
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
  public LinkedList<Exchange> getAllByName(char type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagByDate(Tag t, char type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagByName(Tag t, char type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagsByDate(LinkedList<Tag> ts, char type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagsByName(LinkedList<Tag> ts, char type) {
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