package budget.backend.structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

public class LimitedStackTest 
{
  @Test
  public void constructorTest()
  {
    LimitedStack<Integer> st = new LimitedStack<>();
    assertTrue("non-empty stack", st.empty());

    st = new LimitedStack<>(10);
    assertTrue("limit is " + st.getLimit() + ", it should be 10.", st.getLimit() == 10);
  }

  @Test
  public void pushTest(){
    LimitedStack<Integer> st = new LimitedStack<>();
    int e = st.push(10);
    assertTrue(e == 10);
  }

  @Test
  public void popTest(){
    LimitedStack<Integer> st = new LimitedStack<>();
    int e = st.push(10);
    assertTrue(st.pop() == 10);

    st.push(20);
    assertTrue(st.pop() == 20);

    st.push(30);
    st.push(40);
    assertTrue(st.pop() == 40);
  }

  @Test
  public void peekTest(){
    LimitedStack<Integer> st = new LimitedStack<>();

    st.push(10);
    assertTrue(st.peek() == 10 && !st.empty());
  }

  @Test
  public void limitTest(){
    LimitedStack<Integer> st = new LimitedStack<>(1);
    st.push(10);
    st.push(20);
    assertTrue(st.size() == 1 && st.peek() == 20);
  }

  @Test
  public void clearTest(){
    LimitedStack<Integer> st = new LimitedStack<>();
    st.push(110);
    st.clear();
    assertTrue(st.empty());

    st.clear();
    assertTrue(st.empty());

  }
}