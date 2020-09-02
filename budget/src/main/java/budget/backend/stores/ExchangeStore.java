package budget.backend.stores;

import java.io.BufferedReader;
import java.io.FileWriter;

import budget.backend.interfaces.iExchangeStore;
import budget.backend.money.Exchange;

public class ExchangeStore implements iExchangeStore {

  @Override
  public boolean readFile(BufferedReader in) {
    // TODO decrypt data
    return false;
  }

  @Override
  public boolean writeFile(FileWriter out) {
    // TODO encrypt data
    return false;
  }

  @Override
  public boolean add(String composedString) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Exchange delete(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Exchange find(String id) {
    // TODO Auto-generated method stub
    return null;
  }
  
}