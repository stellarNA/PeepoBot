package oofBot.events.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;

public class BotInvite extends Command {
	
	public BotInvite() {
		this.name = "invite";
		this.aliases = new String[] {"Invite", "botInvite", "botinvite", "BotInvite", "Botinvite"};
		this.help = "Provides a link to invite the bot to a discord server.";
		this.category = new Category("General");
		this.guildOnly = false;
	}

	@Override
	protected void execute(CommandEvent event) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("peepoBot invite link.");
		builder.setImage("https://cdn.discordapp.com/attachments/550195618182594573/554021653303263232/download.jpg");
		builder.addField("URL", "https://discordapp.com/oauth2/authorize?client_id=550469273752174617&scope=bot&permissions=1329065281", true);
		event.reply(builder.build());
	}
}
