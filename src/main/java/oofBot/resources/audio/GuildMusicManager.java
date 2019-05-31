package oofBot.resources.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {
	public final AudioPlayer player;
	
	public final AudioPlayerSendHandler sendHandler;
	
	public final TrackScheduler scheduler;
	
	public GuildMusicManager(AudioPlayerManager manager) {
		player = manager.createPlayer();
		scheduler = new TrackScheduler(player);
		sendHandler = new AudioPlayerSendHandler(player);
		player.addListener(scheduler);
	}
}
