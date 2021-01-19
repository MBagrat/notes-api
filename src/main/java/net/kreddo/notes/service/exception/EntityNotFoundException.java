package net.kreddo.notes.service.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.util.StringUtils;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String entityName, String... searchParamsMap) {
    super(EntityNotFoundException.generateMessage(entityName, toMap(String.class, String.class, searchParamsMap)));
  }

  private static String generateMessage(String entityName, Map<String, String> searchParams) {
    return StringUtils.capitalize(entityName) + " was not found for parameters " + searchParams;
  }

  private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, String... entries) {
    if (entries.length % 2 == 1) {
      throw new IllegalArgumentException("Invalid entries");
    }
    return IntStream.range(0, entries.length / 2)
        .map(i -> i * 2)
        .collect(
            HashMap::new,
            (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
  }
}