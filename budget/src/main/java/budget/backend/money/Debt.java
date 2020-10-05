package budget.backend.money;

import java.util.Date;
import java.util.LinkedList;

import budget.backend.tags.Tag;
import budget.backend.users.User;
import budget.backend.utils.Exceptions.CurrencyException;

public class Debt extends Exchange {

  private User debtor; /** the user that owes the money */
  private User creditor; /** the user that is owed */

  public Debt(String id, Currency currency, Date date, LinkedList<Tag> labels, User debtor, User creditor){
    super(id, currency, date, labels);
    dataChecker.verifyUser(debtor);
    dataChecker.verifyUser(creditor);
    this.debtor = debtor;
    this.creditor = creditor;
  }

  @Override
  public void setDate(Date date) {
    try {
      super.dataChecker.verifyDate(date);
      super.date = date;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void setCurrency(Currency currency) throws CurrencyException {
    try {
      super.dataChecker.verifyCurrency(currency);
      if (currency.getAmount() < 0)
        throw new CurrencyException("Income amount must be positive!");
      super.currency = currency;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  
  public void resolveDebt(){
    //TODO: add income to the creditor and expense to the debtor. They will both have a resoved debt label and the label that this object has.
  }

  @Override
  public String toString(){
    String ret = "";

    ret = super.toString();
    ret += "D:" + debtor.getId();
    ret += "C:" + creditor.getId();

    return ret;
  }
  
  
}