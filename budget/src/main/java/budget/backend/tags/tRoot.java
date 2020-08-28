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
  
  public static final int id = 00000;
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
   */
  public void addDescendant(Tag e) {
    descendants.add(e);
  }

  @Override
  public int getId() {
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

}