package main;

import exceptions.AppException;
import services.ShoppingService;
import services.UserDataService;

import java.text.MessageFormat;
import java.util.Arrays;

public class MenuService {

  private final ShoppingService shoppingService;
  private final UserDataService userDataService;


  public MenuService(final String jsonFilename) {

    shoppingService = new ShoppingService(jsonFilename);
    userDataService = new UserDataService();
  }

  public void mainMenu() {
    menuOptions();
    while (true)
      try {
        int option = userDataService.getInt("INPUT YOUR OPTION: ");
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
    shoppingService.ProductCategories().forEach(cat -> System.out.println("Category: " + cat));
  }

  private static void menuOptions() {

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

  private void option8() {
    System.out.println(shoppingService.clientsDebt());
  }

  private void option7() {
    System.out.println(shoppingService.mostPopularCategoryByClientsAge());
  }

  private void option6() {
    System.out.println(shoppingService.clientsWithMostPurchaseValueInEachCategory());
  }

  private void option5() {
    System.out.println(shoppingService.cheapiestInEachCategory());
  }

  private void option4() {
    System.out.println(shoppingService.mostExpensiveInEachCategory());
  }

  private void option3() {
    System.out.println(shoppingService.averageProductPriceInCategory());
  }

  private void option2() {
    printProductCategories();
    final String categoryName = userDataService.getString("INPUT PRODCUT CATEGORY");
    System.out.println(shoppingService.whoPaidTheMostInSpecifiedCategory(categoryName));
  }

  private void option1() {

    System.out.println(shoppingService.whoPaidTheMost());
  }

}
