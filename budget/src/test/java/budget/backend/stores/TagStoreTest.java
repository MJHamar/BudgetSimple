package budget.backend.stores;

import budget.backend.interfaces.iTag;
import budget.backend.tags.Tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.util.LinkedList;

import org.junit.Test;

import budget.backend.tags.tRoot;

public class TagStoreTest {

  private TagStore store = new TagStore();

  public TagStoreTest() throws FileNotFoundException 
  {
    
  }

  @Test
  public void addTest(){
    store.add("800000000;regular expense;000000000");
    assertEquals("regular expense", store.find("800000000").getName());
  }

  @Test
  public void defaultTest() throws Exception {
    try {
      File file = new File("/Users/hamarmiklos/OneDrive - University of Warwick/projects/BudgetSimple/budget/tags.txt");
      BufferedReader r = new BufferedReader(new FileReader(file));

      store.readFile(r);

      assertEquals("regular expense", store.find("800000000").getName());
      assertEquals("food", store.find("700000000").getName());

    } catch (Exception e) {
      throw e;
    }
  }

  @Test
  public void defineTest() throws Exception {
    try {
      File file = new File("/Users/hamarmiklos/OneDrive - University of Warwick/projects/BudgetSimple/budget/tags.txt");
      BufferedReader r = new BufferedReader(new FileReader(file));

      store.readFile(r);

      Tag def = store.define(store.find("700000000"), "street food");

      assertEquals("street food", def.getName());
      assertEquals("700000000", def.getParent().getId());

    } catch (Exception e) {
      throw e;
    }
  }
 
  @Test
  //Working is verified, but don't want to constantly create the same files
  public void writeTest() throws Exception
  {
    try {
      File file = new File("/Users/hamarmiklos/OneDrive - University of Warwick/projects/BudgetSimple/budget/tags.txt");
      BufferedReader r = new BufferedReader(new FileReader(file));

      store.readFile(r);

      store.define(store.find("700000000"), "street food");
      store.define(store.find("400000000"), "Fruit");
      store.define(store.find("410000000"), "Apples");

      File file2 = new File("/Users/hamarmiklos/OneDrive - University of Warwick/projects/BudgetSimple/budget/tagsNew.txt");
      file2.createNewFile();

      FileWriter w = new FileWriter(file2, false);
      store.writeFile(w);
      w.close();

    } catch (Exception e) {
      throw e;
    }
  }

  @Test
  public void bestMatchTest() {
    iTag root = new tRoot();
    LinkedList<iTag> list = new LinkedList<>();
    Tag t1 = new Tag(root);// misc 100000
    Tag t2 = new Tag(root, new LinkedList<Tag>(), "110000000", "memory");
    Tag t3 = new Tag(root, new LinkedList<Tag>(), "120000000", "mem");
    Tag t4 = new Tag(root, new LinkedList<Tag>(), "130000000", "Memoy");
    Tag t5 = new Tag(root, new LinkedList<Tag>(), "140000000", "orymem");
    list.add(t1);
    list.add(t2);
    list.add(t3);
    list.add(t4);
    list.add(t5);

    LinkedList<iTag> m1 = store.bestMatch(list, "m");
    LinkedList<iTag> e1 = new LinkedList<>();
    e1.add(t3);
    e1.add(t2);
    e1.add(t4);
    e1.add(t1);
    e1.add(t5);
    assertEquals(e1, m1);

    m1= store.bestMatch(list, "mem");
    e1.clear();
    e1.add(t3);
    e1.add(t2);
    e1.add(t4);
    e1.add(t5);
    assertEquals(e1, m1);

  }

  @Test 
  public void findSimilarTest(){
    File file = new File("/Users/hamarmiklos/OneDrive - University of Warwick/projects/BudgetSimple/budget/tags.txt");
    BufferedReader r;
    try {
      r = new BufferedReader(new FileReader(file));
      store.readFile(r);

      LinkedList<iTag> list = store.findSimilar("r");
      String m1 = "";
      for (iTag t : list)
        m1 += t.getName() + ";";
      assertEquals("regular expense;groceries;party;travel;", m1);
      
      list = store.findSimilar("ar");
      m1 = "";
      for (iTag t : list)
        m1 += t.getName() + ";";
      assertEquals("party;regular expense;", m1);

      list = store.findSimilar("re");
      m1 = "";
      for (iTag t : list)
        m1 += t.getName() + ";";
      assertEquals("regular expense;", m1);

      list = store.findSimilar("g");
      m1 = "";
      for (iTag t : list)
        m1 += t.getName() + ";";
      assertEquals("groceries;regular expense;shopping;", m1);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  @Test
  public void findDescendantsTest(){
    File file = new File("/Users/hamarmiklos/OneDrive - University of Warwick/projects/BudgetSimple/budget/tagsNew.txt");
    BufferedReader r;
    try {
      r = new BufferedReader(new FileReader(file));
      store.readFile(r);

      iTag t = store.find("400000000");
      LinkedList<iTag> list = store.getAllDescendants(t);
      String s = "";
      for (iTag tt : list){
        assertTrue(tt.getId().charAt(0) == '4');
      }

    } catch (Exception e){
      e.printStackTrace();
    }
  }


}
