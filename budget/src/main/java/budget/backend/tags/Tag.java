package budget.backend.tags;

import java.util.LinkedList;

import budget.backend.interfaces.iTag;

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

  public Tag(){
    this.parent = null;
    this.descendants = new LinkedList<>();
    this.id = 10000;
    this.name = "Misc";
    System.out.println("Please set parent with an existing tRoot instance to have a full tree");
  }

  public Tag(iTag root){
    this.parent = root;
  }

  public Tag(String composedString){
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

  @Override
  public int getId() {
    return this.id;  
  }

  @Override
  public LinkedList<Tag> getDescendants() {
    return this.descendants;
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
    ret += parent.getId();

    return ret;
  }



}