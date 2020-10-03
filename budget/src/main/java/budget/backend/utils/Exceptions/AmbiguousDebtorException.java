package budget.backend.utils.Exceptions;

import budget.backend.money.Debt;

public class AmbiguousDebtorException extends RuntimeException {

  private static final long serialVersionUID = 2L;
  private String message;
  private Debt d;

  public AmbiguousDebtorException(){
    this.message = "";
    this.d = null;
  }

  public AmbiguousDebtorException(String message, Debt d){
    this.message = message;
    this.d = d;
  }

  public AmbiguousDebtorException(Debt d){
    this.message = "Please set Debtor";
    this.d = d;
  }

  public String getMessage(){
    return message;
  }

  public Debt getDebt(){
    return this.d;
  }

}
