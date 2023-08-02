package ec.solmedia.shared.domain.command;

public interface CommandBus {

  void dispatch(Command command) throws CommandNotRegistered;
}
