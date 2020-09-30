package budget.backend.stores;

import budget.backend.tags.Tag;
import budget.backend.tags.tRoot;
import budget.backend.utils.DataChecker;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import budget.backend.interfaces.iTagStore;
import budget.backend.structures.BinaryTreeHeap;
import budget.backend.structures.LimitedStack;
import budget.backend.interfaces.iTag;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
  private iTag root = new tRoot();

  /** serachCache and searchHistory together represent the previous patterns and results, speeding up searching */
  private LimitedStack<LinkedList<Tag>> searchCache;
  private LimitedStack<String> searchHistory;

  public TagStore() {
    this.map = new HashMap<>();
    this.map.put(root.getId(), root);
    this.dataChecker = new DataChecker();
    this.searchCache = new LimitedStack<>(10);
    this.searchHistory = new LimitedStack<>(10);
  }

  @Override
  public boolean readFile(BufferedReader in) {

    // going from top to bottom, for all Tags, create them using their parents
    // (which always comes before the descendant and add them to the structure)
    boolean success = true;
    //TODO: decrypt data

    try {
      String line = in.readLine();
      if ((line == null) || (line.compareTo("tRoot") != 0)) 
        throw new IOException("Non-readable file");
      else {
        line = in.readLine();
      }
      //each line is a composed string of a Tag instance
      while (line != null){
        //split the line into components
        String[] parts = line.split(";");
        for (String s : parts)
        //find parent, check for null
        if (parts.length == 3){
          iTag parent = map.get(parts[2]);
          if (parent == null) success = false;
          //create new Tag instance
          Tag t = new Tag(parent, new LinkedList<Tag>(), parts[0], parts[1]);
          //add t as a descendant of the parent
          parent.addDescendant(t);
          map.put(t.getId(), t);
        }
        line = in.readLine();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      success = false;
    }
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
      String[] help = composedString.split(";");
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

  /**
   * Add a predefined Tag to the structure. Parent has to be a known tag to the structure, or a default tag.
   * 
   * If the parent is not found, but the id is verified, add this to one of the default tags.
   * @param t
   * @return true if the tag was added to the structure
   */
  public boolean add(Tag t) throws Exception
  {
    try {
      dataChecker.verifyTag(t);
      iTag parent = this.find(t.getParent().getId());
      if (parent == null){
        String newParentID = t.getId().substring(0, 1);
        newParentID += "0000"; //recreate the id of a default tag
        parent = this.find(newParentID);
        t.setParent((Tag)parent);
      }
      //add to parent as descendant
      parent.getDescendants().add(t);

      map.put(t.getId(), t);
      
    } catch (Exception e) {
      throw e;
    }
    return true;
  }

  @Override
  public Tag define(iTag parent, String name) throws IllegalArgumentException
  {
    try{
      //find parent
      if (map.containsKey(parent.getId())){
        // create new ID
        String newId = parent.getId();
        int firstZero = newId.indexOf("0");
        if (firstZero > 0){
          newId = newId.substring(0, firstZero);
          if (parent.getDescendants().size() == 9) 
            throw new IllegalArgumentException("Parent descendants out of bound");
          else
            newId += String.valueOf(parent.getDescendants().size()+1);
          while (newId.length() < 9) newId += "0";
        } else {
          throw new IllegalArgumentException("Tree structure is at maximum height!");
        }
        iTag t = new Tag(parent, new LinkedList<Tag>(),newId, name);
        dataChecker.verifyTag((Tag) t);
        // add new tag to the structure
        parent.addDescendant((Tag) t);
        map.put(newId, t);
        return (Tag) t;
      }
      //parent was not found
      else 
        throw new IllegalArgumentException("structure does not contain the given parent object!");
    }
    catch(Exception e){
      throw e;
    }
  }

  @Override
  public iTag delete(String id) 
  {
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
  public iTag find(String id) 
  {
    return map.get(id);
  }

  public LinkedList<Tag> findSimilar(String pattern)
  {
    //TODO: figure out best choice, and finish this function


    //run through linearly on the dataset and sort all matching patterns
    //first find those that start with this letter
    Pattern p = Pattern.compile("^"+pattern, Pattern.CASE_INSENSITIVE);
    Matcher m;
    String historyHelper;
    LinkedList<Tag> cacheHelper;
    //pop all elements from the stack that does not match the current pattern
    do{
      historyHelper = searchHistory.pop();
      cacheHelper = searchCache.pop();
      m = p.matcher(historyHelper);
    } while (!m.find());
    //found a matching String in the history, continue with the found LinkedList. There is no need to find all non-matching Strings again
    if (m.find())
    {
      searchCache.push(cacheHelper);
      searchHistory.push(historyHelper);
      for (Tag t : cacheHelper){
        m = p.matcher(t.getName());
      }
    }
    
    //cache the result for easier search on the next pattern
    //if zero matches occurred, or the input string is less than the previus, delete cache and search the whole array again
    return null;
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

  @SuppressWarnings("unchecked")
  protected LinkedList<Tag> bestMatch(LinkedList<Tag> list, String pattern)
  {
    //TODO: make private

    //selection sort the list, removing any elements that do not fit the pattern
    Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    Pattern p1 = Pattern.compile("^" + pattern, Pattern.CASE_INSENSITIVE);
    //heap-sort the elements, full match on p gets nr 0, match on p1 gets nr 1, match on p gets nr 2
    BinaryTreeHeap<Integer, Tag> tree = new BinaryTreeHeap<>();

    for (Tag t : list){
      Matcher m = p.matcher(t.getName());
      Matcher m1 = p1.matcher(t.getName());
      if (m.find())
        tree.insert(0,t);
      else if (m1.matches())
        tree.insert(1, t);
      else if (m.matches())
        tree.insert(2, t);
    }
    //export the sorted list from the tree
    LinkedList<Tag> ret = new LinkedList<>();
    for (int i = 0; i < tree.getSize(); i++)
      ret.add(tree.removeMinV());
    return ret;
  }
  


}