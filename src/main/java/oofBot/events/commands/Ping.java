package oofBot.events.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Ping extends Command {
	
	public Ping() {
		this.name = "ping";
		this.aliases = new String[] {"Ping"};
		this.help = "Simple command for pinging the bot.";
		this.category = new Category("General");
	}
	
	@Override
	protected void execute(CommandEvent event) {
		event.reply("Pong! (" + event.getJDA().getPing() + "ms)");
	}

}
