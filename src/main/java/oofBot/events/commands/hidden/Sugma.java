package oofBot.events.commands.hidden;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.User;
import oofBot.main.PeepoBot;

public class Sugma extends Command {

	public Sugma() {
		this.name = "sugma";
		this.aliases = new String[] {"Sugma"};
		this.hidden = true;
		this.guildOnly = true;
		this.category = new Category("Hidden");
		this.ownerCommand = true;
		this.arguments = "[user#xxxx] <reason>";
	}

	@Override
	protected void execute(CommandEvent event) {
		String name = event.getArgs().substring(0, event.getArgs().indexOf("#"));
		String discriminator = event.getArgs().substring(event.getArgs().indexOf("#"));
		User user = PeepoBot.jda.getUsersByName(name, false).stream().filter(it -> it.getDiscriminator().equals(discriminator)).findFirst().orElse(null);
		
		if (user == null) {
			return;
		}
		else if (user.isBot()) {
			return;
		}
		
		event.getGuild().getController().ban(user, 0).reason("Sugma bot bitch");
	}
}
