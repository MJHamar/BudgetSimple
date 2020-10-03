package budget.backend.stores;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import budget.backend.interfaces.iExchangeStore;
import budget.backend.money.Currency;
import budget.backend.money.Debt;
import budget.backend.money.Exchange;
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
  public Exchange define(User user, byte type, Currency currency, String title, LinkedList<Tag> labels)
      throws AmbiguousDebtorException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Exchange define(User user, byte type, Currency currency, String title, LinkedList<Tag> labels, Date date)
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
  public LinkedList<Exchange> getAllByDate(byte type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getAllByName(byte type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagByDate(Tag t, byte type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagByName(Tag t, byte type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagsByDate(LinkedList<Tag> ts, byte type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LinkedList<Exchange> getTagsByName(LinkedList<Tag> ts, byte type) {
    // TODO Auto-generated method stub
    return null;
  }
  
}