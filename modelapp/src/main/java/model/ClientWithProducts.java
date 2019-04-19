package model;

import java.util.List;
import java.util.Objects;

public class ClientWithProducts {

  private Client client;
  private List<Product> products;

  public ClientWithProducts(Client client, List<Product> products) {
    this.client = client;
    this.products = products;
  }

  public ClientWithProducts() {
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ClientWithProducts that = (ClientWithProducts) o;
    return Objects.equals(client, that.client) &&
            Objects.equals(products, that.products);
  }

  @Override
  public int hashCode() {
    return Objects.hash(client, products);
  }

  @Override
  public String toString() {
    return "ClientWithProducts{" +
            "client=" + client +
            ", products=" + products +
            '}';
  }
}
