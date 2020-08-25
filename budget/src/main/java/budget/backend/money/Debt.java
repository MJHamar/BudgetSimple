package budget.backend.money;

import java.util.Date;

import budget.backend.tags.Tag;
import budget.backend.users.User;

public class Debt extends Exchange {

  private User debtor; /** the user that owes the money */
  private User creditor; /** the user that is owed */

  @Override
  public void setId(int id) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setDate(Date date) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setCurrency(Currency currency) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setLabel(Tag label) {
    // TODO Auto-generated method stub

  }

  public void resolveDebt(){
    
  }

  
  
}