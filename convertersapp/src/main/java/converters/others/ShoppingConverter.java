package converters.others;

import converters.json.ClientWithProductsFileJsonConverter;
import exceptions.AppException;
import model.Client;
import model.ClientWithProducts;
import model.Product;
import validator.ClientValidator;
import validator.ProductValidator;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShoppingConverter {

  private static final ClientValidator clientValidator = new ClientValidator();
  private static final ProductValidator productValidator = new ProductValidator();

  private ShoppingConverter() {
  }

  public static Map<Client, Map<Product, Integer>> toShoppingMap(final String jsonFilename) {

    return new ClientWithProductsFileJsonConverter(jsonFilename).fromJson()
            .orElseThrow(() -> new AppException("SPECIFIED JSON FILE: " + jsonFilename + " DOESN'T EXIST"))
            .stream()
            .filter(cWP -> checkClientValidity(cWP.getClient()))
            .collect(Collectors.groupingBy(ClientWithProducts::getClient,
                    Collectors.flatMapping(cWP -> cWP.getProducts().stream(),
                            Collectors.filtering(ShoppingConverter::checkProductValidity
                                    , Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1))))));
  }

  private static boolean checkClientValidity(Client client) {

    Map<String, String> clientErrors = clientValidator.validate(client);
    if (clientValidator.hasErrors()) {
      System.out.println("CLIENT : " + client);
      System.out.println(clientErrors.entrySet().stream().map(e -> e.getKey() + " : " + e.getValue()).collect(Collectors.joining("\n")));
    }
    return !clientValidator.hasErrors();
  }

  private static boolean checkProductValidity(Product product) {

    Map<String, String> orderErrors = productValidator.validate(product);

    if (productValidator.hasErrors()) {
      System.out.println("PRODUCT : " + product);
      System.out.println(orderErrors.entrySet().stream().map(e -> e.getKey() + " : " + e.getValue()).collect(Collectors.joining("\n")));
    }

    return !productValidator.hasErrors();
  }

}
