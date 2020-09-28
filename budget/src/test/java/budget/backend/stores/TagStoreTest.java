package budget.backend.stores;

import budget.backend.interfaces.iTag;
import budget.backend.tags.Tag;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

import budget.backend.tags.tRoot;

public class TagStoreTest {
  
  private TagStore store = new TagStore();

  @Test
  public void bestMatchTest(){
    iTag root = new tRoot();
    LinkedList<Tag> list = new LinkedList<>();
    Tag t1 = new Tag(root);//misc 100000
    Tag t2 = new Tag(root, new LinkedList<Tag>(), "11000", "memory");
    Tag t3 = new Tag(root, new LinkedList<Tag>(), "12000", "mem");
    Tag t4 = new Tag(root, new LinkedList<Tag>(), "13000", "Memoy");
    Tag t5 = new Tag(root, new LinkedList<Tag>(), "14000", "orymem");
    list.add(t1);
    list.add(t2);
    list.add(t3);
    list.add(t4);
    list.add(t5);
    
    LinkedList<Tag> m1 = store.bestMatch(list, "m");
    LinkedList<Tag> e1 = new LinkedList<>();
    e1.add(t3);
    e1.add(t2);
    e1.add(t4);
    e1.add(t5);
    e1.add(t1);
    assertEquals(e1, m1);
    
  }

}
