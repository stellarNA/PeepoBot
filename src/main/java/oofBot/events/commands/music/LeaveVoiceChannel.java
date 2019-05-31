package oofBot.events.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class LeaveVoiceChannel extends Command {
	
	public LeaveVoiceChannel() {
		this.name = "leave";
		this.aliases = new String[] {"Leave"};
		this.help = "Allows the bot to leave the voice channel it is currently connected to.";
		this.category = new Category("Music");
	}
	
	@Override
	protected void execute(CommandEvent event) {
		VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
		
		if (connectedChannel == null) {
			event.reply("Peepo is not connected to a voice channel.");
			return;
		}
		
		event.getGuild().getAudioManager().setSendingHandler(null);
		event.getGuild().getAudioManager().closeAudioConnection();
		event.reply("Disconnected from voice channel.");
	}

}
