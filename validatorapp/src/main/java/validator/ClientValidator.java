package validator;

import model.Client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ClientValidator {

  private Map<String, String> errors = new HashMap<>();

  public Map<String, String> validate(Client client) {
    errors.clear();

    if (client == null) {
      errors.put("Client", "Client object is null");
      return errors;
    }

    if (!isAgeValid(client)) {
      errors.put("Client age", "client is not adult");
    }

    if (!isNameValid(client)) {
      errors.put("Client name", "Client firstName should start wit a capital letter");
    }

    if (!isSurnameValid(client)) {
      errors.put("Client lastName", "Client lastName should start with capital letter");
    }

    if (!isCashValid(client)) {
      errors.put("Client cash", "Client cash should be greater than zero");
    }

    return errors;
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  private boolean isAgeValid(Client client) {
    return client.getAge() >= 18;
  }

  private boolean isSurnameValid(Client client) {
    return client.getLastname().matches("[A-Z][a-z]+");
  }

  private boolean isNameValid(Client client) {
    return client.getFirstname().matches("[A-Z][a-z]+");
  }

  private boolean isCashValid(Client client) {
    return client.getCash().compareTo(BigDecimal.ZERO) > 0;
  }

}
