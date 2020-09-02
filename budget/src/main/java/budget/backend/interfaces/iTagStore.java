package budget.backend.interfaces;

import java.io.BufferedReader;
import java.io.FileWriter;

/**
 * This interface is responsible for the abstraction of the stores subpackage. It defines the most important methods that all stores need to have.
 */
public interface iTagStore {
  
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
  public iTag delete(String id);

  /**
   * Find an object in the structure with the given id. 
   * @param id
   * @return false if the id was not found
   */
  public iTag find(String id);

  /**
   * Output all the stored data in a way, that the structure can easily be rebuilt from it
   * @return a String containing all necessary information in the structure
   */
  public String toString();

}