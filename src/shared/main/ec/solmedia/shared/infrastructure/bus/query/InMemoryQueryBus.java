package ec.solmedia.shared.infrastructure.bus.query;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.query.Query;
import ec.solmedia.shared.domain.query.QueryBus;
import ec.solmedia.shared.domain.query.QueryHandler;
import ec.solmedia.shared.domain.query.QueryNotRegistered;
import ec.solmedia.shared.domain.query.Response;
import org.springframework.context.ApplicationContext;

@Service
public final class InMemoryQueryBus implements QueryBus {

  private final QueryHandlersInformation information;
  private final ApplicationContext context;

  public InMemoryQueryBus(QueryHandlersInformation information, ApplicationContext context) {
    this.information = information;
    this.context = context;
  }

  @Override
  public Response ask(Query query) throws QueryNotRegistered {
    Class<? extends QueryHandler> queryHandlerClass = information.search(query.getClass());

    QueryHandler handler = context.getBean(queryHandlerClass);

    return handler.handle(query);
  }
}
