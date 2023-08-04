package ec.solmedia.shared.infrastructure.bus.query;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.query.Query;
import ec.solmedia.shared.domain.query.QueryHandler;
import ec.solmedia.shared.domain.query.QueryNotRegistered;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;


@Service
public final class QueryHandlersInformation {

  HashMap<Class<? extends Query>, Class<? extends QueryHandler>> indexedQueryHandlers;

  public QueryHandlersInformation() {
    Reflections reflections = new Reflections("tv.codely");
    Set<Class<? extends QueryHandler>> classes = reflections.getSubTypesOf(QueryHandler.class);

    indexedQueryHandlers = formatHandlers(classes);
  }

  private HashMap<Class<? extends Query>, Class<? extends QueryHandler>> formatHandlers(
      Set<Class<? extends QueryHandler>> queryHandlers
  ) {
    HashMap<Class<? extends Query>, Class<? extends QueryHandler>> handlers = new HashMap<>();

    for (Class<? extends QueryHandler> handler : queryHandlers) {
      ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];
      Class<? extends Query> queryClass = (Class<? extends Query>) paramType.getActualTypeArguments()[0];

      handlers.put(queryClass, handler);
    }

    return handlers;
  }

  public Class<? extends QueryHandler> search(Class<? extends Query> queryClass)
      throws QueryNotRegistered {
    Class<? extends QueryHandler> queryHandlerClass = indexedQueryHandlers.get(queryClass);

    if (null == queryHandlerClass) {
      throw new QueryNotRegistered(queryClass);
    }

    return queryHandlerClass;
  }
}
