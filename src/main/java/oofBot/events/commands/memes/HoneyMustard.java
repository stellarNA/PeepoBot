package oofBot.events.commands.memes;

import java.awt.Color;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;

public class HoneyMustard extends Command {
	
	public HoneyMustard() {
		this.name = "honeymustard";
		this.aliases = new String[] {"hunmus", "honeymussy"};
		this.help = "Embeds a picture of a tendie taking a dip :ok_hand:";
		this.category = new Category("Memes");
	}
	
	@Override
	protected void execute(CommandEvent event) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("HoNeY mUsSy");
		builder.setColor(Color.decode("#fad489"));
		builder.setImage("https://cdn.discordapp.com/attachments/550195618182594573/550516078782971904/HoneyMust.png");
		event.reply(builder.build());
	}

}
