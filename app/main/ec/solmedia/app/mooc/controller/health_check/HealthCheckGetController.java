package ec.solmedia.app.mooc.controller.health_check;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class HealthCheckGetController {

  @RequestMapping("/health-check")
  public Map<String, String> index() {
    final var status = new HashMap<String, String>();
    status.put("status", "ok");

    return status;
  }
}
