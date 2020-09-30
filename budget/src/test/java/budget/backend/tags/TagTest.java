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
		
	}

	@Test 
	public void tNull(){
		Tag nullTest = new Tag();
		boolean t = nullTest.getDescendants().size() == 0 && nullTest.getId() == "100000000" && nullTest.getParent() == null && nullTest.getName() == "Misc";
		assertTrue(t);
	}

	@Test	
	public void defTest(){
		Tag oneTest = new Tag(root);
		boolean t = oneTest.getDescendants().size() == 0 && oneTest.getId() == "100000000" && oneTest.getParent() == root && oneTest.getName() == "Misc";
		assertTrue(t);
	}

	@Test
	public void constructorTest(){
		Tag twoTest = new Tag("123456789;abc;000000000");
		boolean t = 
			twoTest.getId().compareTo("123456789") == 0 &&
			twoTest.getParent() == null &&
			twoTest.getName().compareTo("abc") == 0 &&
			twoTest.getDescendants().size() == 0;
		assertTrue(t);
	}

	/* assertion errors to be resolved later
	@Test(expected = IllegalArgumentException.class)
	public void tFail(){
		Tag faliTest = new Tag("123 Elem 00000");
	}

	@Test(expected = IllegalArgumentException.class)
	public void tFail2(){
		Tag failTest2 = new Tag("12345  scac");
	}
	*/
}
