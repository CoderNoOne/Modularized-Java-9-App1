package model;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
/*
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder*/


public class ClientWithProductsFile {

  private List<ClientWithProducts> clientWithProductsList;

  /*
  public ClientWithProductsFile(final String... filenames) {

    if (filenames == null || filenames.length == 0)
      throw new AppException("YOU DIDN'T SPECIFY ANY FILE TO READ");

    clientWithProductsList = FileService.readFiles(filenames);

  }
*/

  public List<ClientWithProducts> getClientWithProductsList() {
    return clientWithProductsList;
  }

  public void setClientWithProductsList(List<ClientWithProducts> clientWithProductsList) {
    this.clientWithProductsList = clientWithProductsList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ClientWithProductsFile that = (ClientWithProductsFile) o;
    return Objects.equals(clientWithProductsList, that.clientWithProductsList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientWithProductsList);
  }

  @Override
  public String toString() {
    return "ClientWithProductsFile{" +
            "clientWithProductsList=" + clientWithProductsList +
            '}';
  }

  /*= new ArrayList<>();



  public List<ClientWithProducts> readFiles(String... filenames) {

    if (filenames == null || filenames.length == 0)
      throw new AppException("YOU DIDN'T SPECIFY ANY FILE TO READ");

    Arrays.stream(filenames).forEach(this::readFile);

  }

  private Client readClient(String line) {

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

  private void readFile(String file) {
    try (Stream<String> lines = Files.lines(Paths.get(file))) {

      lines.
              forEach(line -> {
                Client client = readClient(line);
                List<Product> productList = readProducts(line);
                clientWithProductsList.add(new ClientWithProducts(client, productList));
              });

    } catch (IOException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      throw new AppException("Problem with File " + file);
    }
  }*/
}




