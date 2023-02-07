package ec.solmedia.shared.infrastructure;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.UuidGenerator;
import java.util.UUID;

@Service
public class JavaUuidGenerator implements UuidGenerator {

  @Override
  public String generate() {
    return UUID.randomUUID().toString();
  }
}
