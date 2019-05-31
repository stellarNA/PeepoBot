package oofBot.events.commands.memes;

import java.awt.Color;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import oofBot.main.PeepoBot;
import oofBot.resources.ToggleCommand;

public class Bean extends ToggleCommand {
	protected boolean disabled = false;
	
	public Bean() {
		this.name = "bean";
		this.aliases = new String[] {"Bean"};
		this.help = "Bean your friends!";
		this.arguments = "[user#xxxx]";
		this.category = new Category("Memes");
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (disabled) {
			event.reply("This command is disabled by an administator on this server.");
			return;
		}
		else if (event.getArgs().isEmpty()) {
			event.reply("You must provide arguments for the command.\n`" + PeepoBot.PREFIX + "bean [user#xxxx]`");
			return;
		}
		else if (event.getArgs().split(" ").length > 1) {
			event.reply("You must provide only one argument for the command.\n`" + PeepoBot.PREFIX + "bean [user#xxxx]`");
			return;
		}
		
		String name = event.getArgs().substring(0, event.getArgs().indexOf("#"));
		String discriminator = event.getArgs().substring(event.getArgs().indexOf("#") + 1);
		User user = PeepoBot.jda.getUsersByName(name, false).stream().filter(it -> it.getDiscriminator().equals(discriminator)).findFirst().orElse(null);
		
		if (user == null) {
			event.reply("You must mention a valid user for the command.\n`" + PeepoBot.PREFIX + "bean [@target]`");
			return;
		}
		else if (user.isBot()) {
			event.reply("You cannot bean the bot u moron.");
			return;
		}
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.addField("You've been freakin beaned, ", user.getName(), true);
		builder.setColor(Color.decode("#799f14"));
		builder.setImage("https://cdn.discordapp.com/attachments/550195618182594573/550552673682718720/beaned.jpg");
		user.openPrivateChannel().queue(channel -> channel.sendMessage(builder.build()).queue());
	}

}
