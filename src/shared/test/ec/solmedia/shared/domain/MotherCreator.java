package ec.solmedia.shared.domain;

import net.datafaker.Faker;

public class MotherCreator {

  public static final Faker faker = new Faker();

  public static Faker random() {
    return faker;
  }

}
