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
  private int id; /** a 5-digit number */
  private String name;
  private DataChecker dataChecker;

  public Tag(){
    this.dataChecker = new DataChecker();
    this.parent = null;
    this.descendants = new LinkedList<>();
    this.id = 10000;
    this.name = "Misc";
    System.out.println("Please set parent with an existing tRoot instance to have a full tree");
  }

  public Tag(iTag root){
    this.dataChecker = new DataChecker();
    this.parent = root;
    this.descendants = new LinkedList<>();
    this.id = 10000;
    this.name = "Misc";
  }

  public Tag(String composedString){
    this.dataChecker = new DataChecker();
    String[] help = composedString.split(" ");
    try {
      this.id = Integer.valueOf(help[0]);
      this.descendants = new LinkedList<>();
      this.parent = null;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      System.out.println("Not sufficient information, please set descendants and parent.");
    }
  }

  public Tag(iTag parent, LinkedList<Tag> descendants, int id, String name){
    this.dataChecker = new DataChecker();
    try {
      if (parent instanceof tRoot)dataChecker.verifyTRoot((tRoot)parent);
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
  public int getId() {
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

    ret += id + " ";
    ret += name + " ";
    if (this.parent != null) ret += parent.getId();

    return ret;
  }

  @Override
  public String getName() {
    return this.name;
  }



}