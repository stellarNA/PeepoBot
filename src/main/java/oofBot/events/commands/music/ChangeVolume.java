package oofBot.events.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;


import oofBot.main.PeepoBot;
import oofBot.resources.audio.MusicPlayerManager;

public class ChangeVolume extends Command {
	
	public ChangeVolume() {
		this.name = "volume";
		this.aliases = new String[] {"Volume", "setvolume", "setVolume"};
		this.help = "Changes the volume of the bot's sound, requires a reset to take effect. (Warning: default is 100 in a range from 0 - 150, changing from the default volume may affect sound quality)";
		this.arguments = "[volume number]";
		this.category = new Category("Music");
		this.cooldown = 5;
	}

	@Override
	protected void execute(CommandEvent event) {
		if (event.getArgs().isEmpty()) {
			event.reply("You must provide arguments for this command.\n`" + PeepoBot.PREFIX + "volume " + arguments + "`");
			return;
		}
		
		try {
			MusicPlayerManager.volume = Integer.valueOf(event.getArgs());
			event.reply("Volume changed to " + Integer.valueOf(event.getArgs()));
		}
		catch (Exception e) {
			event.reply("Arguments must be a number.\n`" + PeepoBot.PREFIX + "volume " + arguments + "`");
		}
	}
}
