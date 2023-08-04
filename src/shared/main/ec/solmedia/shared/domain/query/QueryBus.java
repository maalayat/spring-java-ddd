package ec.solmedia.shared.domain.query;

public interface QueryBus {

  <R extends Response> R ask(Query query) throws QueryNotRegistered;
}
