package ec.solmedia.shared.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class JsonUtil {

  public static String encode(HashMap<String, Serializable> map) {
    try {
      return new ObjectMapper().writeValueAsString(map);
    } catch (JsonProcessingException e) {
      return "";
    }
  }

  public static HashMap<String, Serializable> decode(String body) {
    try {
      return new ObjectMapper().readValue(body, HashMap.class);
    } catch (IOException e) {
      return null;
    }
  }
}
