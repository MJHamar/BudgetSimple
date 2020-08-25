package budget.backend.utils.Exceptions;

public class CurrencyException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private String message;

  public CurrencyException(){
    this.message = "";
  }

  public CurrencyException(String message){
    this.message = message;
  }

  public String getMessage(){
    return this.message;
  }

}