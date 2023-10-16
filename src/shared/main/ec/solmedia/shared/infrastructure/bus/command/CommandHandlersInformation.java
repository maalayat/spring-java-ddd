package ec.solmedia.shared.infrastructure.bus.command;

import ec.solmedia.shared.domain.Service;
import ec.solmedia.shared.domain.command.Command;
import ec.solmedia.shared.domain.command.CommandHandler;
import ec.solmedia.shared.domain.command.CommandNotRegistered;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;


@Service
public final class CommandHandlersInformation {

  private HashMap<Class<? extends Command>, Class<? extends CommandHandler>> indexedCommandHandlers;

  public CommandHandlersInformation() {
    final var reflections = new Reflections("ec.solmedia");
    final var classes = reflections.getSubTypesOf(CommandHandler.class);

    indexedCommandHandlers = formatHandlers(classes);
  }

  private HashMap<Class<? extends Command>, Class<? extends CommandHandler>> formatHandlers(
      Set<Class<? extends CommandHandler>> commandHandlers) {
    final var handlers = new HashMap<Class<? extends Command>, Class<? extends CommandHandler>>();

    for (Class<? extends CommandHandler> handler : commandHandlers) {
      ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];
      Class<? extends Command> commandClass = (Class<? extends Command>) paramType.getActualTypeArguments()[0];

      handlers.put(commandClass, handler);
    }

    return handlers;
  }

  public Class<? extends CommandHandler> search(Class<? extends Command> commandClass)
      throws CommandNotRegistered {
    final var commandHandlerClass = indexedCommandHandlers.get(commandClass);

    if (null == commandHandlerClass) {
      throw new CommandNotRegistered(commandClass);
    }

    return commandHandlerClass;
  }
}
