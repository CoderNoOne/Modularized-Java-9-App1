package main;

import converters.json.ClientWithProductsFileJsonConverter;
import exceptions.AppException;
import model.ClientWithProducts;
import services.FileService;
import services.ShoppingService;
import services.UserDataService;

import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuService {

  private final ShoppingService shoppingService;
  private final UserDataService userDataService;

  public MenuService(final String jsonFilename, final String... filenames) {

    if (jsonFilename == null || !jsonFilename.matches("[\\w]+\\.json")) {
      throw new AppException("WRONG JSON FILE FORMAT!");
    }

    if (!areFilesValid(filenames)) {
      throw new AppException("CRITICAL EXCEPTION - AT LEAST ONE OF YOUR INPUT FILE CANNOT BE READ. CHECK YOUR FILES AGAIN");
    }

    convertFilesIntoJson(jsonFilename, filenames);
    shoppingService = new ShoppingService(jsonFilename);
    userDataService = new UserDataService();
  }

  private static boolean areFilesValid(final String... filenames) {
    return filenames != null
           && Arrays.stream(filenames).noneMatch(Objects::isNull)
           && Arrays.stream(filenames).map(".\\mainapp\\"::concat).map(Paths::get).allMatch(Files::isRegularFile);
  }

  private void convertFilesIntoJson(final String jsonFilename, final String... filenames) {

    var clientWithProductsFileJsonConverter = new ClientWithProductsFileJsonConverter(jsonFilename);

    List<ClientWithProducts> clientWithProducts = FileService.mergeAllFilesIntoClientWithProductsList(
            Arrays.stream(filenames).map(".\\mainapp\\"::concat).toArray(String[]::new));

    clientWithProductsFileJsonConverter.toJson(clientWithProducts);
  }

  public void mainMenu() {
    menuOptions();
    while (true)
      try {

        int option = userDataService.getInt("\n\t\t\t\t\u001B[35m INPUT YOUR OPTION: ");
        switch (option) {
          case 1:
            option1();
            break;
          case 2:
            option2();
            break;
          case 3:
            option3();
            break;
          case 4:
            option4();
            break;
          case 5:
            option5();
            break;
          case 6:
            option6();
            break;
          case 7:
            option7();
            break;
          case 8:
            option8();
            break;
          case 9:
            userDataService.close();
            return;
          case 10:
            menuOptions();
            break;
          default:
            throw new AppException("INPUT OPTION IS NOT DEFINED");
        }
      } catch (AppException e) {
        System.out.println(e.getExceptionMessage());
        System.err.println(Arrays.toString(e.getStackTrace()));
      }
  }


  private void printProductCategories() {
    shoppingService.productCategories().forEach(cat -> System.out.println("Category: " + cat));
  }

  private static void menuOptions() {

    System.out.println("\n\t\t\t\t\t\u001B[36m MENU OPTIONS");
    String menu = MessageFormat.format(
            "\nOption no. 1 - {0}\n" +
                    "Option no. 2 - {1}\n" +
                    "Option no. 3 - {2}\n" +
                    "Option no. 4 - {3}\n" +
                    "Option no. 5 - {4}\n" +
                    "Option no. 6 - {5}\n" +
                    "Option no. 7 - {6}\n" +
                    "Option no. 8 - {7}\n" +
                    "Option no. 9 - {8}\n" +
                    "Option no. 10 - {9}\n",

            "Who paid the most for all purchases",
            "Who paid the most in specified category",
            "Average product price in each products' category",
            "Most expensive product bought by any client in each products' category",
            "The cheapiest product in each products' category",
            "The clients with most purchases in each products' category",
            "The most popular category by clients' age",
            "Show debt for clients",
            "Exit the program",
            "Show the menu options"
    );

    System.out.println(menu);

  }

  private void option1() {

    shoppingService.whoPaidTheMost().forEach((client, value) ->
            System.out.println("Client who paid the most for all purchases: " + client + " -> Shopping value: " + value));
  }

  private void option2() {
    printProductCategories();
    final String categoryName = userDataService.getString("INPUT PRODUCT CATEGORY FROM ABOVE LIST");

    System.out.println("Client who paid the most in " + categoryName + " product category -> " +
            shoppingService.whoPaidTheMostInSpecifiedCategory(categoryName));
  }

  private void option3() {

    shoppingService.averageProductPriceInCategory().forEach((category, avgPrice) ->
            System.out.println("Category " + category + " -> Average product price: " + avgPrice.setScale(2, RoundingMode.HALF_UP)));
  }

  private void option4() {

    shoppingService.mostExpensiveProductInEachCategory().forEach((category, product) ->
            System.out.println("Category: " + category + " -> Most expensive product: " + product.getName() + "| price: " + product.getPrize()));
  }

  private void option5() {

    System.out.println("\n\t\tCHEAPIEST PRODUCTS IN REGARDS TO CATEGORY\n");
    shoppingService.cheapiestProductInEachCategory().forEach((category, product) ->
            System.out.println("Product category: " + category + " -> Cheapiest product: " + product.getName() + "| price: " + product.getPrize()));
  }

  private void option6() {
    shoppingService.clientsWithMostPurchaseValueInEachCategory().forEach((category, client) ->
            System.out.println("Product category: " + category + " -> Client with most purchase value in that category: " + client));
  }


  private void option7() {
    shoppingService.mostPopularCategoryByClientsAge().forEach((age, category) ->
            System.out.println("Clients age: " + age + " -> Most popular category: " + category));
  }

  private void option8() {
    if (!shoppingService.clientsDebt().isEmpty()) {
      shoppingService.clientsDebt().forEach((client, debt)
              -> System.out.println("Client: " + client + " -> Debt: " + debt.setScale(2, RoundingMode.HALF_UP)));
    } else {
      System.out.println("NO CLIENT HAS A DEBT!\n");
    }
  }


}
