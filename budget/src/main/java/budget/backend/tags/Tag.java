package budget.backend.tags;

import java.util.LinkedList;

import budget.backend.interfaces.iTag;

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

  @Override
  public int getId() {
    return this.id;  
  }

  @Override
  public LinkedList<Tag> getDescendants() {
    return this.descendants;
  }

  public void addDescendant(Tag e){
    descendants.add(e);
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