package services;

import exceptions.AppException;
import model.Client;
import model.ClientWithProducts;
import model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

  private static List<ClientWithProducts> clientWithProductsList = new ArrayList<>();

  private FileService() {
  }

  private static void readFiles(final String... filenames) {
    Arrays.stream(filenames).forEach(FileService::readFile);
  }

  public static List<ClientWithProducts> cumulateFiles(final String... filenames) {
    clientWithProductsList.clear();
    readFiles(filenames);

    return clientWithProductsList
            .stream()
            .collect(Collectors.groupingBy(ClientWithProducts::getClient,
                    Collectors.flatMapping(clientwithProducts -> clientwithProducts.getProducts().stream(), Collectors.toList())))
            .entrySet()
            .stream()
            .map(e -> new ClientWithProducts(e.getKey(), e.getValue()))
            .collect(Collectors.toList());

  }

  private static Client readClient(String line) {

    return Arrays.stream(line.split("[\\s]+"))
            .limit(1).map(clientInf -> clientInf.split("[;]"))
            .map(x -> new Client(x[0], x[1], Integer.parseInt(x[2])
                    , BigDecimal.valueOf(Double.parseDouble(x[3]))))
            .findFirst().orElseThrow(() -> new AppException("NO CLIENT FOUND"));
  }


  private static List<Product> readProducts(String line) {

    return Arrays.stream(line.split("[\\s]+"))
            .skip(1)
            .map(s -> {
              if (s.startsWith("[") && s.endsWith("]"))
                s = s.substring(1, s.length() - 1);
              else if (s.endsWith("]"))
                s = s.substring(0, s.length() - 1);
              else if (s.startsWith("[")) {
                s = s.substring(1);
              }
              return s;
            }).map(productInfo -> productInfo.split("[;]"))
            .map(x -> new Product(x[0], x[1], BigDecimal.valueOf(Double.parseDouble(x[2]))))
            .collect(Collectors.toList());

  }

  private static void readFile(String file) {
    try (Stream<String> lines = Files.lines(Paths.get(file))) {

      lines.
              forEach(line -> {
                Client client = readClient(line);
                List<Product> productList = readProducts(line);
                clientWithProductsList.add(new ClientWithProducts(client, productList));
              });

    } catch (IOException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      System.out.println(e.getMessage());
      System.out.println(e.getCause());
      System.out.println(e.getLocalizedMessage());
      throw new AppException("Problem with File " + file);
    }
  }

}
