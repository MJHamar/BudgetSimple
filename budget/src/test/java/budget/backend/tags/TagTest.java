package budget.backend.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import budget.backend.tags.*;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TagTest {

	private final tRoot root = new tRoot();


	public TagTest(){
      System.out.println("---------TESTING THE TAGS----------");
    }

	@Test 
	public void tNull(){
		Tag nullTest = new Tag();
		boolean t = nullTest.getDescendants().size() == 0 && nullTest.getId() == 10000 && nullTest.getParent() == null && nullTest.getName() == "Misc";
		assertTrue(t);
	}

	@Test	
	public void defTest(){
		Tag oneTest = new Tag(root);
		boolean t = oneTest.getDescendants().size() == 0 && oneTest.getId() == 10000 && oneTest.getParent() == root && oneTest.getName() == "Misc";
		assertTrue(t);
	}

	public void constructorTest(){
		Tag twoTest = new Tag("12345 Elem 00000");
		
	}

	public void tFail(){

	}
	/*
	private Tag faliTest = new Tag("123 Elem 00000");
	private Tag failTest2 = new Tag("12345  scac"); */
}
