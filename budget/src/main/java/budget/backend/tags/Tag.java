package budget.backend.tags;

import java.util.LinkedList;

import budget.backend.interfaces.iTag;
import budget.backend.utils.DataChecker;

/**
 * This class gives the opinion for the user to create custom tags with which the user will be able to better track its moneyflow, make more representative statistics
 * 
 * Tags form a tree structure. 
 */
public class Tag implements iTag {
  
  private iTag parent;
  private LinkedList<Tag> descendants;
  private String id; /** a 9-digit number */
  private String name;
  private DataChecker dataChecker;

  public Tag(){
    this.dataChecker = new DataChecker();
    this.parent = null;
    this.descendants = new LinkedList<>();
    this.id = "100000000";
    this.name = "Misc";
    System.out.println("Please set parent with an existing tRoot instance to have a full tree");
  }

  public Tag(iTag root){
    this.dataChecker = new DataChecker();
    dataChecker.verifyTRoot((tRoot)root);
    this.parent = root;
    this.descendants = new LinkedList<>();
    this.id = "100000000";
    this.name = "Misc";
  }

  /**
   * Construct a Tag object using the given composedString. This method cannot determine neither the parent object, nor the descendants. 
   * @param composedString
   * @throws IllegalArgumentException
   */
  public Tag(String composedString) throws IllegalArgumentException{
    this.dataChecker = new DataChecker();
    String[] help = composedString.split(";");
    try {
      this.id = help[0];
      this.descendants = new LinkedList<>();
      this.parent = null;
      if (help[1] != "")
        this.name = help[1];
      else throw new IllegalArgumentException("Name field must not be empty!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    }
  }

  public Tag(iTag parent, LinkedList<Tag> descendants, String id, String name) throws IllegalArgumentException{
    this.dataChecker = new DataChecker();
    try {
      if (parent instanceof tRoot) dataChecker.verifyTRoot((tRoot)parent);
      else dataChecker.verifyTag((Tag)parent);
      this.parent = parent;
      dataChecker.verifyTagId(id);
      this.id = id;
      this.descendants = new LinkedList<>();
      for (Tag t : descendants)
        dataChecker.verifyTag(t);
      this.descendants = descendants;
      this.name = name;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    }

  }

  @Override
  public String getId() {
    return this.id;  
  }

  @Override
  public LinkedList<Tag> getDescendants() {
    return this.descendants;
  }

  public iTag getParent(){
    return this.parent;
  }
  public void setParent(iTag t){
    if (t instanceof tRoot) dataChecker.verifyTRoot((tRoot)t);
    else dataChecker.verifyTag((Tag)t);
  }

  /**
   * Add a new descendant to the object
   * 
   * @param e
   * @return true if the insertion succeeded
   */
  public boolean addDescendant(Tag e){
    return descendants.add(e);
  }
 
  /**
   * try adding a list of descendants to the tree
   * 
   * @param es
   * @return false if any of the insertions failed
   */
  public boolean addDescendants(LinkedList<Tag> es){
    boolean ret = true;
    for (Tag t : es){ 
      if (!addDescendant(t)) ret = false;
    }
    return ret;
  }

  @Override
  public String toString(){
    String ret = "";

    ret += id + ";";
    ret += name + ";";
    if (this.parent != null) ret += parent.getId();

    return ret;
  }

  @Override
  public String getName() {
    return this.name;
  }



}