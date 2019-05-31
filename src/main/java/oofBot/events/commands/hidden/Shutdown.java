package oofBot.events.commands.hidden;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Shutdown extends Command {
	
	public Shutdown() {
		this.name = "shutdown";
		this.aliases = new String[] {"off", "Off", "Shutdown"};
		this.hidden = true;
		this.guildOnly = false;
		this.category = new Category("Hidden");
		this.ownerCommand = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		event.reply("Shutting down peepoBot.");
		event.getJDA().shutdown();
		System.exit(0);
	}
}