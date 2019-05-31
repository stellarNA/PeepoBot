package oofBot.events.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

public class JoinVoiceChannel extends Command {
	
	public JoinVoiceChannel() {
		this.name = "join";
		this.aliases = new String[] {"Join"};
		this.help = "Allows the bot to join the voice channel you are currently connected to.";
		this.category = new Category("Music");
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (event.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
			event.reply("Peepo is already connected to the voice channel.");
			return;
		}
		
		VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
		if (connectedChannel == null) {
			event.reply("You are not in a voice channel.");
			return;
		}
		
		AudioManager audioManager = event.getGuild().getAudioManager();
		if (audioManager.isAttemptingToConnect()) {
			event.reply("calm down u autistic fuck");
			return;
		}
		
		audioManager.openAudioConnection(connectedChannel);
		event.reply("Connected to channel.");
	}

}
