package ec.solmedia.shared.domain.query;

public class QueryNotRegistered extends Exception {

  public QueryNotRegistered(Class<? extends Query> queryClass) {
    super(String.format("The query %s has not been registered", queryClass.getName()));
  }
}
