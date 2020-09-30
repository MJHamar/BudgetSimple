package budget.backend.interfaces;

import java.io.BufferedReader;
import java.io.FileWriter;

import budget.backend.tags.Tag;

/**
 * This interface is responsible for the abstraction of the stores subpackage. It defines the most important methods that all stores need to have.
 */
public interface iTagStore {
  
  /**
   * Given a BufferedReader, read a textfile and create the objects that are saved within
   * @param in
   * @return false if the file was unreadable or had the wrong format. True otherwise
 * @throws Exception
   */
  public boolean readFile(BufferedReader in) throws RuntimeException;

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
   * define a new user-defined Tag object. This method generates a new ID for the tree structure with the given conventions
   * 
   *    The id of the Tag is a 9-digit number, that represents the trip from the root of the tree to the Tag, by stating which descendant of the parent the tag is. There are 9 descendants possible with an index from 1-9. A Tag with 0 index is not allowed.
   * @param root
   * @param name
   * @throws IllegalArgumentException
   */
  public Tag define(iTag root, String name) throws IllegalArgumentException;

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