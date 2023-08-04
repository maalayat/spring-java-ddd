package ec.solmedia.shared.domain.query;

public interface QueryHandler<T extends Query> {

  void handle(T query);

}
