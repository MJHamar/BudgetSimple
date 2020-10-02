package budget.backend.interfaces;

import java.util.LinkedList;

import budget.backend.tags.Tag;

public interface iTag {
  
  public String getId();
  public String toString();
  public LinkedList<Tag> getDescendants();
  public String getName();
  public boolean addDescendant(Tag descendant);
  public boolean addDescendants(LinkedList<Tag> descendants);
  /**
   * Remove a given descendant from the descendants field.
   * @param id
   * @return the removed object, or null if the object did not apper in the descendants list
   * This method is slightly slower than removeDescendats(Tag)
   */
  public Tag removeDescendant(String id);
  /**
   * Remove a given descendant from the descendants field.
   * 
   * @param id
   * @return the removed object, or null if the object did not apper in the
   *         descendants list This method is slightly faster than
   *         removeDescendats(String)
   */
  public Tag removeDescendant(Tag t);

}