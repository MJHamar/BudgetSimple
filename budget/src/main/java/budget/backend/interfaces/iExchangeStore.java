package budget.backend.interfaces;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.LinkedList;

import budget.backend.money.Currency;
import budget.backend.money.Debt;
import budget.backend.money.Exchange;
import budget.backend.tags.Tag;
import budget.backend.users.User;
import budget.backend.utils.Exceptions.AmbiguousDebtorException;

/**
 * This interface is responsible for the abstraction of the stores subpackage. It defines the most important methods that all stores need to have.
 */
public interface iExchangeStore {

  public static final byte _UNDEFINED = 0;
  public static final byte _INCOME = 1;
  public static final byte _EXPENSE = 2;
  public static final byte _DEBT = 3;
  
  /**
   * Given a BufferedReader, read a textfile and create the objects that are saved within
   * @param in
   * @return false if the file was unreadable or had the wrong format. True otherwise
   */
  public boolean readFile(BufferedReader in);

  /**
   * Given a BufferedReader, write the information stored in the structure to a textfile.
   * @param out
   * @return false if the file was unwritable or there was an incostistency in the structure. True otherwise
   */
  public boolean writeFile(FileWriter out);

  /**
   * Add a new object to the structure given a composedString that is unique for all stored objects. and contain all necessary information about it
   * @param composedString
   * @return false if the format of the composedString was not right
   */
  public boolean add(String composedString);

  /**
   * Given an id, delete the object from the structure
   * @param id
   * @return false if the deletion failed
   */
  public Exchange delete(String id);

  /**
   * Find an object in the structure with the given id. 
   * @param id
   * @return false if the id was not found
   */
  public Exchange find(String id);

  /**
   * Define a new Exchange object in the structure. Not sufficient information is
   * given to unambiguously define a Debt object. This needs to be resolved in the
   * implementation
   * 
   * The date of this new object will be set to the current date.
   * 
   * @param user
   * @param type     either iExchangeStore._INCOME, iExchangeStore.EXPENSE or
   *                 iExchangeStore._DEBT
   * @param title    user-defined string
   * @param labels
   * @param currency
   * @return the defined Exchange object or null, if an exception occurred.
   *         Normally, the defined Exchange object could not be verified by the
   *         dataChecker. 
   * @throws budget.backend.utils.Exceptions.AmbiguousDebtorException
   */
  public Exchange define(User user, byte type, Currency currency, String title, LinkedList<Tag> labels) throws AmbiguousDebtorException;

  /**
   * Define a new Exchange object specifying the date it was created. Not
   * sufficient information is given to unambiguously define a Debt object. This
   * needs to be resolved in the implementation
   * 
   * 
   * @param user
   * @param type   either iExchangeStore._INCOME, iExchangeStore.EXPENSE or
   *               iExchangeStore._DEBT
   * @param title  user-defined string
   * @param labels
   * @throws budget.backend.utils.Exceptions.AmbiguousDebtorException
   * @param currency
   * @param date
   * @return the defined Exchange object or null, if an exception occurred. Normally, the defined Exchange object could not be verified by the dataChecker. 
   */
  public Exchange define(User user, byte type, Currency currency, String title, LinkedList<Tag> labels, Date date) throws AmbiguousDebtorException;

  /**
   * Define a Debt object stating the creditor and the debtor. 
   * 
   * @param user the creditor
   * @param currency a Currency object
   * @param title user-defined title
   * @param labels
   * @param debtor the debtor
   * @return the defined Debt object or null, if the object was not created. Normally because it could not be verified by the DataChecker.
   */
  public Debt defineDebt(User creditor, Currency currency, String title, LinkedList<Tag> labels, User debtor);

  /**
   * Find all elements of the structure that has the same pattern in their title
   * @param pattern
   * @return a list of Exchange objects
   */
  public LinkedList<Exchange> findSimilar(String pattern);

  /**
   * Output all the stored data in a way, that the structure can easily be rebuilt from it
   * @return a String containing all necessary information in the structure
   */
  public String toString();

  //various getter methods

  /**
   * get all instances in the structure ordered by date created
   * @param type one of the final fields of the iExchangeStore interface
   * @return
   */
  public LinkedList<Exchange> getAllByDate(byte type);
  
  /**
   * 
   * get all instances in the structure ordered by name
   * 
   * @param type one of the final fields of the iExchangeStore interface* @return
   */
  public LinkedList<Exchange> getAllByName(byte type);

  /**
   * 
   * get all instances in the structure with the given Tag ordered by date created
   * 
   * @param type one of the final fields of the iExchangeStore interface* @return
   */
  public LinkedList<Exchange> getTagByDate(Tag t, byte type);

  /**
   * 
   * get all instances in the structure with the given Tag ordered by name
   * 
   * @param type one of the final fields of the iExchangeStore interface* @return
   */
  public LinkedList<Exchange> getTagByName(Tag t, byte type);

  /**
   * 
   * get all instances in the structure with the given list of tags ordered by date created
   * 
   * @param type one of the final fields of the iExchangeStore interface* @return
   */
  public LinkedList<Exchange> getTagsByDate(LinkedList<Tag> ts, byte type);

  /**
   * 
   * get all instances in the structure with the given list of tags ordered by name
   * 
   * @param type one of the final fields of the iExchangeStore interface* @return
   */
  public LinkedList<Exchange> getTagsByName(LinkedList<Tag> ts, byte type); 


}