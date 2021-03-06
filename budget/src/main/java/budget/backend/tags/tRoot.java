package budget.backend.tags;

import java.util.LinkedList;

import budget.backend.interfaces.iTag;

/**
 * The purpose if this class is to have an exact root for the User's own Tag hierarchy, this class may not be added as a real Tag to Exchange instances.
 * 
 * The descendants of this class are the default tags that are generalized and the user cannot modify them. 
 * 
 * User-defined tags may only be descendants of these default tags. A user-defined tag should not be a direct descendant of tRoot. 
 */
public class tRoot implements iTag {
  
  public static final String id = "000000000";
  public LinkedList<Tag> descendants;

  /**
   * create a new tRoot instance
   */
  public tRoot(){
    descendants = new LinkedList<>();
  }

  /**
   * Add a new descendant to the object
   * @param e
   * @return true if the insertion succeeded
   */
  public boolean addDescendant(Tag e) {
    return descendants.add(e);
  }

  /**
   * try adding a list of descendants to the tree
   * @param es
   * @return false if any of the insertions failed
   */
  public boolean addDescendants(LinkedList<Tag> es) {
    boolean ret = true;
    for (Tag t : es) {
      if (!addDescendant(t))
        ret = false;
    }
    return ret;
  }

  @Override
  public String getId() {
    return tRoot.id;
  }

  @Override
  public LinkedList<Tag> getDescendants() {
    return this.descendants;
  }

  @Override
  public String toString(){
    return "tRoot";
  }

  @Override
  public String getName() {
    return "root";
  }

  @Override
  public Tag removeDescendant(String id) {
    for (Tag t : descendants) {
      if (t.getId().compareTo(id) == 0) {
        descendants.remove(t);
        return t;
      }
    }
    return null;
  }

  @Override
  public Tag removeDescendant(Tag t) {
    if (descendants.remove(t))
      return t;
    else
      return null;
  }

}