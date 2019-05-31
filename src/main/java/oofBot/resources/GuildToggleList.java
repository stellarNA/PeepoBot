package oofBot.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jagrosh.jdautilities.command.Command;

import oofBot.main.PeepoBot;

public class GuildToggleList {
	private final Map<ToggleCommand, Boolean> toggledCommands;
	
	public GuildToggleList() {
		toggledCommands = new HashMap<ToggleCommand, Boolean>();
		List<Command> list = PeepoBot.commandClient.getCommands();
		for (Command command : list) {
			if (command instanceof ToggleCommand) {
				toggledCommands.put((ToggleCommand) command, new Boolean(false));
			}
		}
	}
	
	public Map<ToggleCommand, Boolean> getToggles() {
		return toggledCommands;
	}
	
	public void toggleCommandInList(ToggleCommand command) {
		Boolean bool = toggledCommands.get(command);
		bool = new Boolean(!bool.booleanValue());
	}
}
