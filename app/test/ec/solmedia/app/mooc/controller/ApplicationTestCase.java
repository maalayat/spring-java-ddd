package ec.solmedia.app.mooc.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.domain.event.bus.EventBus;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class ApplicationTestCase {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EventBus eventBus;

  public void assertResponse(
      String endpoint, Integer expectedStatusCode, String expectedResponse) throws Exception {
    final var response = expectedResponse.isEmpty()
        ? content().string("")
        : content().json(expectedResponse);

    mockMvc
        .perform(get(endpoint))
        .andExpect(status().is(expectedStatusCode))
        .andExpect(response);
  }

  public void assertRequestWithBody(
      String method, String endpoint, String body, Integer expectedStatusCode) throws Exception {
    mockMvc
        .perform(request(HttpMethod.valueOf(method), endpoint).content(body)
            .contentType(APPLICATION_JSON))
        .andExpect(status().is(expectedStatusCode))
        .andExpect(content().string(""));
  }

  protected void givenISendAnEventToTheBus(DomainEvent... domainEvent) {
    eventBus.publish(Arrays.asList(domainEvent));
  }
}
