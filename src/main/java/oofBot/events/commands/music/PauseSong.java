package oofBot.events.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import oofBot.resources.audio.MusicPlayerManager;

public class PauseSong extends Command {
	
	public PauseSong() {
		this.name = "pause";
		this.aliases = new String[] {"Pause", "unpause", "Unpause"};
		this.help = "Pauses the currently playing song";
		this.category = new Category("Music");
	}

	@Override
	protected void execute(CommandEvent event) {
		if (!event.getSelfMember().getVoiceState().inVoiceChannel()) {
			event.reply("Peepo is not in a voice channel.");
			return;
		}
		
		MusicPlayerManager manager = MusicPlayerManager.getInstance();
		if (manager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack() == null) {
			event.reply("Peepo is not playing a track to pause.");
			return;
		}
		
		AudioPlayer player = manager.getGuildMusicManager(event.getGuild()).player;
		player.setPaused(!player.isPaused());
		if (player.isPaused()) {
			event.reply("The player has been paused.");
		}
		else {
			event.reply("The player has been unpaused.");
		}
	}
}
