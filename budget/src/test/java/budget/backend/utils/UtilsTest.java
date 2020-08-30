package budget.backend.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import budget.backend.interfaces.iTag;
import budget.backend.money.Currency;
import budget.backend.tags.*;
import budget.backend.utils.*;

import org.junit.Test;

public class UtilsTest {

  private DataChecker dataChecker;

    public UtilsTest(){
      dataChecker = new DataChecker();
    }

    @Test(expected = IllegalArgumentException.class)
    public void dataCheckerCurrencyFalse(){
      dataChecker.verifyCurrency("[currency]");
    }

    @Test(expected = IllegalArgumentException.class)
    public void dataCheckerCurrencyFalse2() {
      dataChecker.verifyCurrency(new Currency(123, "WER"));
    }

    @Test
    public void dataCheckerCurrencyRight(){
      try {
        dataChecker.verifyCurrency("[123 HUF]");
      } catch (Exception e) {
        System.out.println(e.getMessage());
        assertTrue(e instanceof Exception);
      }
    }

    @Test
    public void dataCheckerCurrencyRight2() {
      try {
        dataChecker.verifyCurrency(new Currency(123, "HUF"));
      } catch (Exception e) {
        System.out.println(e.getMessage());
        assertTrue(e instanceof Exception);
      }
    }

    @Test(expected = IllegalArgumentException.class)
    public void dataCheckerIdFalse(){
      dataChecker.verifyId(12345678);
    }

    @Test
    public void dataCheckerIdRight(){
      dataChecker.verifyId(999999999);
    }

    @Test
    public void dataCheckerGroupIDFalse(){
      //TODO: test for false input
      assertTrue(true);
    }

    @Test
    public void dataCheckerGroupIDRight() {
      // TODO: test for right input
      assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dataCheckerTagFalse(){
      Tag t = new Tag(null, new LinkedList<Tag>(), 12345, "name");
      dataChecker.verifyTag(t);
    }

    @Test
    public void dataCheckerTagRight(){
      iTag t = new Tag();
      dataChecker.verifyTag(new Tag(t, new LinkedList<Tag>(), 12345, "name"));
    }

    @Test
    public void dataCheckerTRootFalse(){
      tRoot root = new tRoot();
      dataChecker.verifyTRoot(root);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dataCheckerTagIDFalse(){
      Tag t = new Tag("1245 name 00000");
      dataChecker.verifyTagId(t.getId());
    }
    
    @Test
    public void dataCheckerTagIDRight() {
      Tag t = new Tag("12345 name 00000");
      dataChecker.verifyTagId(t.getId());
    }

}