package oofBot.events.mentions;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

// waiting until JDA 4 to implement for easier recognition of mentions
public class BotMention extends ListenerAdapter {
	
	public void onGuildMessageReceivedEvent(GuildMessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();
		event.getChannel().sendMessage(event.getGuild().getSelfMember().getAsMention()).queue();
		
		if (message.contains(event.getGuild().getSelfMember().getAsMention())) {
			switch(message) {
			case "gay":
				event.getChannel().sendMessage("no u. :arrows_counterclockwise:").queue();
			case "praise":
				event.getChannel().sendMessage("your benevolent bot overlord is among you. :pray:").queue();
			default:
				event.getChannel().sendMessage("test").queue();
			}
		}
	}
	
}
