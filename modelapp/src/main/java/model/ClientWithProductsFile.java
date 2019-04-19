package model;

import java.util.List;
import java.util.Objects;

public class ClientWithProductsFile {

  private List<ClientWithProducts> clientWithProductsList;

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

}




