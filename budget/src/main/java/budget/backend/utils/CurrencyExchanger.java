package budget.backend.utils;

public class CurrencyExchanger {
  
  //TODO: should be a hashmap
  private String[] knownCurrencies = {
    "HUF",
    "EUR",
    "GBP"
  };

  public boolean isKnown(String currency){

    //linear search through the array to find the currency
    for (int i = 0; i < knownCurrencies.length; i++){
      if (knownCurrencies[i] == currency) return true;
    }

    return false;
  }

  /**
   * Convert a given amount from a given currency to a new currency, based on real-time data
   * @param amount
   * @param oldCurrency
   * @param newCurrency
   * @return the amount in the new currency
   */
  public int convert(int amount, String oldCurrency, String newCurrency) {
    //TODO: convert by real-time data
	  return amount;
  }

}