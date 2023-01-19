package ec.solmedia.app.mooc.controller.health_check;

import ec.solmedia.app.mooc.controller.ApplicationTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class HealthCheckGetControllerTest extends ApplicationTestCase {

  @Test
  @DisplayName("Check the app is working ok")
  public void checkTheAppIsWorkingOk() throws Exception {
    this.assertResponse("/health-check", 200, "{'status': 'ok'}");
  }
}
