package ec.solmedia.shared.domain;

import com.google.common.base.CaseFormat;

public class NamingUtil {

  public static String toSnake(String text) {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, text);
  }

  public static String toCamelFirstLower(String text) {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, text);
  }

}
