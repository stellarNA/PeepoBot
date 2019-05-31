package oofBot.events.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import oofBot.resources.audio.MusicPlayerManager;
import oofBot.resources.audio.TrackScheduler;

public class StopQueue extends Command {
	
	public StopQueue() {
		this.name = "stop";
		this.aliases = new String[] {"Stop"};
		this.help = "Clears the current queue.";
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
			event.reply("Peepo is not playing a song.");
			return;
		}
		TrackScheduler scheduler = manager.getGuildMusicManager(event.getGuild()).scheduler;
		AudioPlayer player = manager.getGuildMusicManager(event.getGuild()).player;
		scheduler.getQueue().clear();
		player.stopTrack();
		player.setPaused(false);
		event.reply("The queue has been cleared.");
	}
}
