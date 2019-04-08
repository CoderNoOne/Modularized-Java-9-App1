package services;

import converters.others.ShoppingConverter;
import exceptions.AppException;
import model.Client;
import model.Product;
import services.collectors.AverageProductPriceCollector;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShoppingService {

  private final Map<Client, Map<Product, Integer>> shopping;

  public ShoppingService(final String jsonFilename) {
    shopping = new ShoppingConverter().toShoppingMap(jsonFilename);
  }


  public Set<String> ProductCategories() {
    return shopping.values()
            .stream()
            .flatMap(e -> e.keySet().stream())
            .map(Product::getCategory)
            .collect(Collectors.toSet());
  }

  public Client whoPaidTheMost() {

    return shopping.entrySet().stream()
            .collect(
                    Collectors.groupingBy(Map.Entry::getKey,
                            Collectors.flatMapping(e -> e.getValue().entrySet().stream().map(innerEntry -> innerEntry.getKey().getPrize().multiply(BigDecimal.valueOf(innerEntry.getValue()))),
                                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue()).get().getKey();
  }

  public Client whoPaidTheMostInSpecifiedCategory(final String category) {

    boolean isValidArg = false;
    for (String productCategory : ProductCategories()) {
      if (category.equalsIgnoreCase(productCategory)) {
        isValidArg = true;
        break;
      }
    }

    if (!isValidArg) throw new AppException("INPUT CATEGORY DOESN'T EXIST");


    return shopping.entrySet().stream()
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.flatMapping(map -> map.getValue().entrySet().stream(),
                    Collectors.mapping(ee -> ee.getKey().getPrize().multiply(new BigDecimal(ee.getValue())),
                            Collectors.reducing(BigDecimal.ZERO, BigDecimal::max))))).entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();


  }

  public Map<String, BigDecimal> averageProductPriceInCategory() {


    return shopping.entrySet().stream()
            .flatMap(entry -> entry.getValue().keySet().stream())
            .collect(Collectors.groupingBy(Product::getCategory, new AverageProductPriceCollector()));
  }


  public Map<String, Optional<Product>> mostExpensiveInEachCategory() {

    return shopping.entrySet().stream()
            .flatMap(e -> e.getValue().keySet().stream())
            .collect(Collectors.groupingBy(Product::getCategory,
                    Collectors.maxBy(Comparator.comparing(Product::getPrize))));
  }


  public Map<String, Product> cheapiestInEachCategory() {

    return shopping.entrySet().stream()
            .flatMap(e -> e.getValue().keySet().stream())
            .collect(Collectors.groupingBy(Product::getCategory,
                    Collectors.collectingAndThen(Collectors.toList(),
                            list -> list.stream().min(Comparator.comparing(Product::getPrize)).get())));

  }


  public Map<String, Client> clientsWithMostPurchaseValueInEachCategory() {


    return shopping.values().stream()
            .flatMap(e -> e.keySet().stream().map(Product::getCategory))
            .distinct()
            .collect(Collectors.toMap(
                    Function.identity(),
                    category -> shopping.entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getKey,
                            Collectors.flatMapping(e -> e.getValue().entrySet().stream(),
                                    Collectors.filtering(map -> map.getKey().getCategory().equals(category),
                                            Collectors.mapping(map -> map.getKey().getPrize().multiply(new BigDecimal(map.getValue())),
                                                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))))
                            .entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey()));
  }

  public Map<Integer, String> mostPopularCategoryByClientsAge() {

    return shopping.entrySet()
            .stream()
            .collect(Collectors.groupingBy(e -> e.getKey().getAge(),
                    Collectors.flatMapping(e -> e.getValue().entrySet().stream(),
                            Collectors.groupingBy(e -> e.getKey().getCategory(),
                                    Collectors.summingInt(Map.Entry::getValue)))))
            .entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey,
                    e -> e.getValue().entrySet().stream()
                            .filter(innerMap -> innerMap.getValue().equals(e.getValue().entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue()))
                            .map(Map.Entry::getKey).collect(Collectors.joining(", "))));
  }

  public Map<Client, BigDecimal> clientsDebt() {

    return shopping.entrySet().stream()
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().entrySet().stream().map(ee -> ee.getKey().getPrize().multiply(BigDecimal.valueOf(ee.getValue()))).reduce(BigDecimal.ZERO, BigDecimal::add)))
            .entrySet().stream().filter(e -> e.getKey().getCash().subtract(e.getValue()).compareTo(BigDecimal.ZERO) < 0)
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getKey().getCash().subtract(e.getValue())));

  }

}