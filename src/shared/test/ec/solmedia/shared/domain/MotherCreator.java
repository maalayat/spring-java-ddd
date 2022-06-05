package ec.solmedia.shared.domain;

import com.github.javafaker.Faker;

public class MotherCreator {

  public static final Faker faker = new Faker();

  public static Faker random() {
    return faker;
  }

}
