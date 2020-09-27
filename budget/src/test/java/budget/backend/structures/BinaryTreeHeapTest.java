package budget.backend.structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BinaryTreeHeapTest {
  //tester for the binary tree heap

  private BinaryTreeHeap<String, Object> tree1;
  private BinaryTreeHeap<String, Integer> tree2;

  public BinaryTreeHeapTest(){
    tree1 = new BinaryTreeHeap<>();
    tree2 = new BinaryTreeHeap<>();
  }


  @Test(expected = NullPointerException.class)
  public void nullTest(){
    tree1.add(null);
  }

  @Test(expected = NullPointerException.class)
  public void nullTest2(){
    tree2.add(null, null);
  }

  @Test
  public void addTest(){
    tree1.add("qwe");
    tree1.add("asd");
    assertEquals("asd", tree1.getMinK());
    assertEquals(null, tree1.getMinV());

    tree2.add("asd", 1234);
    assertEquals("asd", tree2.getMinK());
    assertEquals((int)1234, (int)tree2.getMinV());
  }

  @Test
  public void removeTest(){
    tree1.add("asd");
    assertEquals("asd", tree1.removeMinK());
    assertEquals(null, tree1.removeMinK());
  }

  @Test
  public void sizeTest(){
    tree1.add("a");
    assertEquals(1, tree1.getSize());
    tree1.add("b");
    assertEquals(2, tree1.getSize());
    tree1.add("c");
    assertEquals(3, tree1.getSize());

    tree1.removeMinK();
    tree1.removeMinK();
    tree1.removeMinK();
    assertEquals(0, tree1.getSize());
  }

  @Test
  public void emptyTest(){
    tree1.add("a");
    tree1.add("c");
    tree1.removeMinK();
    tree1.removeMinK();
    assertTrue(tree1.isEmpty());
    tree1.removeMinK();
    assertTrue(tree1.isEmpty());
  }

  @Test
  public void sortingTest(){

    tree1.add("c");
    tree1.add("a");
    tree1.add("e");
    tree1.add("b");
    tree1.add("d");

    String n = "";

    while(!tree1.isEmpty()){
      n += tree1.removeMinK();
    }

    assertEquals("abcde", n);

  }

  @Test 
  public void repeatingKeysTest(){
    tree1.add("a");
    tree1.add("a");
    tree1.add("b");
    tree1.add("c");
    tree1.add("d");
    tree1.add("a");
    tree1.add("b");

    String n = "";

    while (!tree1.isEmpty()) {
      n += tree1.removeMinK();
    }

    assertEquals("aaabbcd", n);

  }

}
