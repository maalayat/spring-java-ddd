package ec.solmedia.shared.domain.command;

public class CommandNotRegistered extends Exception{

  public CommandNotRegistered(Class<? extends Command> command) {
    super(String.format("The command <%s> hasn't a command handler associated", command.toString()));
  }
}
