package validator;

import model.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductValidator {

  private Map<String, String> errors = new HashMap<>();

  public Map<String, String> validate(Product product) {
    errors.clear();

    if (product == null) {
      errors.put("Product", "Product object is null");
      return errors;
    }

    if (!isProductCategoryNameValid(product)) {
      errors.put("Product category", "Product category name should start with a capital letter");
    }

    if (!isProductPriceValid(product)) {
      errors.put("Product price", "Product price should be greater than zero");
    }

    if (!isProductNameValid(product)) {
      errors.put("Product name", "Product name should start with a capital letter");
    }
    return errors;
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  private boolean isProductNameValid(Product product) {
    return product.getName().matches("[A-Z]([A-Za-z]|[\\d])+");
  }

  private boolean isProductPriceValid(Product product) {
    return product.getPrize().compareTo(BigDecimal.ZERO) > 0;
  }

  private boolean isProductCategoryNameValid(Product product) {
    return product.getCategory().matches("[A-Z]([A-Za-z]|[\\d])+");
  }
}
