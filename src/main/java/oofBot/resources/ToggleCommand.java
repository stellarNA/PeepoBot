package oofBot.resources;

import com.jagrosh.jdautilities.command.Command;

public abstract class ToggleCommand extends Command {
	protected static boolean disabled;
	
	public void toggle() {
		disabled = !disabled;
	}
	
	public String toString() {
		if (disabled) {
			return name + " = disabled";
		}
		else {
			return name + " = enabled";
		}
	}
}
