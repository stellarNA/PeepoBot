package oofBot.events.commands.hidden;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

public class TestCommand extends Command {
	protected EventWaiter waiter;
	
	public TestCommand(EventWaiter waiter) {
		this.name = "test";
		this.ownerCommand = true;
		this.hidden = true;
		this.guildOnly = false;
		this.waiter = waiter;
	}

	@Override
	protected void execute(CommandEvent event) {
		
	}
}
