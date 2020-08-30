package budget.backend.interfaces;

import java.io.BufferedReader;

/**
 * This interface is responsible for the abstraction of the stores subpackage. It defines the most important methods that all stores need to have.
 */
public interface iStore {
  
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
  public boolean writeFile(BufferedReader out);

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
  public boolean delete(String id);

  /**
   * Find an object in the structure with the given id. 
   * @param id
   * @return false if the id was not found
   */
  public boolean find(String id);

  public String toString();

}