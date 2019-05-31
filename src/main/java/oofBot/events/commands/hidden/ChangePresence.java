package oofBot.events.commands.hidden;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.Game;

public class ChangePresence extends Command {
	
	public ChangePresence() {
		this.name = "presence";
		this.aliases = new String[] {"Presence"};
		this.category = new Category("Hidden");
		this.hidden = true;
		this.ownerCommand = true;
		this.arguments = "[Message]";
		this.guildOnly = false;
	}

	@Override
	protected void execute(CommandEvent event) {
		if (event.getArgs().isEmpty()) {
			event.reply("Must provide an argument.");
			return;
		}
		
		event.getJDA().getPresence().setGame(Game.playing(event.getArgs()));
	}
}
