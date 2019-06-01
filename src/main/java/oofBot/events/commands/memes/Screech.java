package oofBot.events.commands.memes;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;
import oofBot.resources.ToggleCommand;
import oofBot.resources.ToggleListManager;
import oofBot.resources.audio.MusicPlayerManager;

public class Screech extends ToggleCommand {
	
	public Sceech() {
		this.name = "ree";
		this.aliases = new String[] {"Ree"};
		this.help = "REEEEEEEE";
		this.category = new Category("Memes");
	}
	
	@Override
	protected void execute(CommandEvent event) {
		ToggleListManager toggleMan = ToggleListManager.getInstance();
		if (toggleMan.getGuildToggleList(event.getGuild()).getToggles().get(this).booleanValue() == true) {
			event.reply("This command is disabled by a server administrator.");
			return;
		}
		
		MusicPlayerManager manager = MusicPlayerManager.getInstance();
		if ((manager.getGuildMusicManager(event.getGuild()).scheduler.getQueue() != null) && (manager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack() != null)) {
			event.reply("Peepo is playing a song/queue and cannot reee.");
			return;
		}
		
		VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
		if (connectedChannel == null) {
			event.reply("You are not connected to a voice channel for peepo to reee in.");
			return;
		}
		
		AudioManager guildManager = event.getGuild().getAudioManager();
		if (!event.getSelfMember().getVoiceState().inVoiceChannel()) {
			guildManager.openAudioConnection(connectedChannel);
		}
		
		manager.loadAndPlay(event.getTextChannel(), "https://www.youtube.com/watch?v=e7AeEOb9Ebg");
		
		manager.getGuildMusicManager(event.getGuild()).player.setVolume(MusicPlayerManager.volume);
	}

}
