package oofBot.events.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Say extends Command {
	
	public Say() {
		this.name = "say";
		this.aliases = new String[] {"Say"};
		this.arguments = "[message]";
		this.help = "PeepoBot says the message in your place.";
		this.category = new Category("General");
	}
	@Override
	protected void execute(CommandEvent event) {
		event.getMessage().delete().queue();
		event.reply(event.getArgs());
	}

}
