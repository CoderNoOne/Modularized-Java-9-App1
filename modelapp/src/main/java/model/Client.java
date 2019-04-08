package model;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class Client {

  private String firstname;
  private String lastname;
  private int age;
  private BigDecimal cash;

  public Client(String firstname, String lastname, int age, BigDecimal cash) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.age = age;
    this.cash = cash;
  }

  public Client() {
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public BigDecimal getCash() {
    return cash;
  }

  public void setCash(BigDecimal cash) {
    this.cash = cash;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Client client = (Client) o;
    return age == client.age &&
            Objects.equals(firstname, client.firstname) &&
            Objects.equals(lastname, client.lastname) &&
            Objects.equals(cash, client.cash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, lastname, age, cash);
  }

  @Override
  public String toString() {
    return "Client{" +
            "firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", age=" + age +
            ", cash=" + cash +
            '}';
  }
}
