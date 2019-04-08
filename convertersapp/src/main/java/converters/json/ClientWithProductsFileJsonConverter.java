package converters.json;

import model.ClientWithProducts;

import java.util.List;

public class ClientWithProductsFileJsonConverter extends JsonConverter<List<ClientWithProducts>> {
  public ClientWithProductsFileJsonConverter(String jsonFilename) {
    super(jsonFilename);
  }
}

