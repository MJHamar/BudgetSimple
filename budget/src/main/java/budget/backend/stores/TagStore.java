package budget.backend.stores;

import budget.backend.tags.Tag;
import budget.backend.tags.tRoot;
import budget.backend.utils.DataChecker;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import budget.backend.interfaces.iTagStore;
import budget.backend.interfaces.iTag;

/**
 * This iStore object is responsible for managing the user-defied tags, allow
 * creation, saving modification and deletion.
 */
public class TagStore implements iTagStore {

  /**
   * Tags themselves refer to each-other in a tre--like way, therefore it is not
   * necessary to store them in an actual data structure. For easyer insertion and
   * serach, a hashmap is used.
   */
  private HashMap<String, iTag> map;
  private DataChecker dataChecker;
  public final iTag root = new tRoot();

  public TagStore() {
    this.map = new HashMap<>();
    this.map.put(root.getId(), root);
    this.dataChecker = new DataChecker();
  }

  @Override
  public boolean readFile(BufferedReader in) {

    // going from top to bottom, for all Tags, create them using their parents
    // (which always comes before the descendant and add them to the structure)
    boolean success = true;
    //TODO: decrypt data

    return success;
  }

  /**
   * Write the information stored in the structure to a given file
   */
  @Override
  public boolean writeFile(FileWriter out) {
    try {
      //TODO: encrypt data
      out.write(toString());
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public boolean add(String composedString) {
    boolean success = true;
    Tag n = null;
    try {
      String[] help = composedString.split(" ");
      if (help.length != 3) success = false;
      else{
        iTag parent = find(help[2]);
        n = new Tag(parent, new LinkedList<Tag>(), help[0], help[1]);
        dataChecker.verifyTag(n);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      success = false;
    } finally{
      if (success && n != null)
        map.put(n.getId(), n);
    }
      return success;
  }

  @Override
  public iTag delete(String id) {
    Tag t = null;
    try {
      dataChecker.verifyTagId(id);
      t = (Tag)map.remove(id);
      if (t != null){
        t.getParent().addDescendants(t.getDescendants());
      }

    } catch (Exception e) {
      System.out.print(e.getMessage());
    }
    
    return t;
    
  }

  @Override
  public iTag find(String id) {
    return map.get(id);
  }

  /**
   * Output all data stored in the structure while ensuring that no descendant will come before its ascendant
   * @retrun the composedString format of all Tag instances in the tree, one in each line
   */
  @Override
  public String toString(){

    return toStringFrom(root);
  }

  /**
   * Recursively call all descendants of the given tag. This method ensures that all elements of the sub-tree are parsed in a linear runtime
   * @param t
   * @return all information stored in the sub-tree
   */
  private String toStringFrom(iTag t){
    String ret = t.toString() + "\n";

    for (Tag tt : t.getDescendants())
      ret += toStringFrom(tt);

    return ret;
  }
  


}