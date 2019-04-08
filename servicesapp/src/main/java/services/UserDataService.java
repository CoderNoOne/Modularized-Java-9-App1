package services;

import exceptions.AppException;

import java.util.Scanner;

public class UserDataService {
  private Scanner sc = new Scanner(System.in);

  public int getInt(String message) {
    System.out.println(message);

    String text = sc.nextLine();
    if (!text.matches("[\\d]+")) {
      throw new AppException("INT VALUE IS NOT CORRECT: " + text);
    }

    return Integer.parseInt(text);
  }


  public String getString(String inputMessage) {

    System.out.println(inputMessage);

    String input = sc.nextLine();

    if (input.length() == 0) {
      throw new AppException("YOU DIDN'T INPUT ANY VALUE");
    }

    return input;
  }


  public void close() {
    if (sc != null) {
      sc.close();
      sc = null;
    }
  }
}
