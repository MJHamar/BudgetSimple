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

}