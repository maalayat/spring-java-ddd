package ec.solmedia.mooc.courses_counter.infrastructure.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.solmedia.mooc.courses.infrastructure.entities.CourseIdEntity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

@Slf4j
@Converter(autoApply = true)
public class JpaConverterJson implements AttributeConverter<List<CourseIdEntity>, String> {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(List<CourseIdEntity> list) {
    try {
      return objectMapper.writeValueAsString(list);
    } catch (JsonProcessingException ex) {
      log.error("Error to convert to database column: {}", ex.getLocalizedMessage(), ex);
      return Strings.EMPTY;
    }
  }

  @Override
  public List<CourseIdEntity> convertToEntityAttribute(String jsonString) {
    try {
      return objectMapper.readValue(jsonString, new TypeReference<>() {
      });
    } catch (IOException ex) {
      log.error("Error to convert to entity attribute: {}", ex.getLocalizedMessage(), ex);
      return Collections.emptyList();
    }
  }

}
