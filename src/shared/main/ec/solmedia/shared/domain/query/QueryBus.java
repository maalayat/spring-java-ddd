package ec.solmedia.shared.domain.query;

public interface QueryBus {

  void ask(Query query) throws QueryNotRegistered;
}
