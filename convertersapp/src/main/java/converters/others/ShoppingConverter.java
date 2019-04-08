package converters.others;

import converters.json.ClientWithProductsFileJsonConverter;
import exceptions.AppException;
import model.Client;
import model.ClientWithProducts;
import model.Product;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ShoppingConverter {

  public Map<Client, Map<Product, Integer>> toShoppingMap(String jsonFilename) {

    if (jsonFilename == null || jsonFilename.equals("")) {
      throw new AppException("YOU DIDN'T INPUT ANY JSON FILE!");
    }
    if (!jsonFilename.matches("[\\w]+\\.json")) {
      throw new AppException("SOME OF INPUT FILE WASN'T OF JSON FORMAT");
    }

    return new ClientWithProductsFileJsonConverter(jsonFilename)
            .fromJson()
            .orElseThrow(() -> new AppException("File " + jsonFilename + " is empty"))
            .stream()
            .collect(Collectors.groupingBy(ClientWithProducts::getClient,
                    Collectors.flatMapping(cWP -> cWP.getProducts().stream(),
                            Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)))));

  }
}



