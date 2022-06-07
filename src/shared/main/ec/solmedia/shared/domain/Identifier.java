package ec.solmedia.shared.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class Identifier implements Serializable {

  protected final String value;

  public Identifier(String value) {
    this.value = ensureValidUuid(value).toString();
  }

  protected Identifier() {
    this.value = null;
  }

  public String value() {
    return value;
  }

  public String getValue() {
    return value;
  }

  private UUID ensureValidUuid(String value) throws IllegalArgumentException {
    return UUID.fromString(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Identifier that = (Identifier) o;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
