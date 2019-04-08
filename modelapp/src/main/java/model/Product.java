package model;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Builder

public class Product {

  private String name;
  private String category;
  private BigDecimal prize;


  public Product(String name, String category, BigDecimal prize) {
    this.name = name;
    this.category = category;
    this.prize = prize;
  }

  public Product() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public BigDecimal getPrize() {
    return prize;
  }

  public void setPrize(BigDecimal prize) {
    this.prize = prize;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(name, product.name) &&
            Objects.equals(category, product.category) &&
            Objects.equals(prize, product.prize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, category, prize);
  }

  @Override
  public String toString() {
    return "Product{" +
            "name='" + name + '\'' +
            ", category='" + category + '\'' +
            ", prize=" + prize +
            '}';
  }
}
