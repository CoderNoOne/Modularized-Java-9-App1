package main;

import converters.json.ClientWithProductsFileJsonConverter;
import model.ClientWithProducts;
import services.FileService;

import java.util.List;

public class App {

  public static void main(String[] args) {


    var clientWithProductsFileJsonConverter = new ClientWithProductsFileJsonConverter("JsonFileNew.json");

    List<ClientWithProducts> clientWithProducts = FileService.cumulateFiles(
            ".\\mainapp\\File", ".\\mainapp\\File1", ".\\mainapp\\File2", ".\\mainapp\\File3");

    clientWithProductsFileJsonConverter.toJson(clientWithProducts);

    new MenuService("jsonFileNew.json").mainMenu();


  }
}
