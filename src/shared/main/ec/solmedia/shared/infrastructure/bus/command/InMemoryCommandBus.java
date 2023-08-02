package ec.solmedia.shared.infrastructure.bus.command;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.command.Command;
import ec.solmedia.shared.domain.command.CommandBus;
import ec.solmedia.shared.domain.command.CommandNotRegistered;
import org.springframework.context.ApplicationContext;


@Service
public final class InMemoryCommandBus implements CommandBus {

  private final CommandHandlersInformation information;
  private final ApplicationContext context;

  public InMemoryCommandBus(CommandHandlersInformation information, ApplicationContext context) {
    this.information = information;
    this.context = context;
  }

  @Override
  public void dispatch(Command command) throws CommandNotRegistered {
    final var commandHandlerClass = information.search(command.getClass());

    final var handler = context.getBean(commandHandlerClass);

    handler.handle(command);
  }
}
