package budget.backend.interfaces;

import java.util.LinkedList;

import budget.backend.tags.Tag;

public interface iTag {
  
  public int getId();
  public String toString();
  public LinkedList<Tag> getDescendants();
  public String getName();

}