package oofBot.resources.audio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.bandcamp.BandcampAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.beam.BeamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.vimeo.VimeoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class MusicPlayerManager {
	private static MusicPlayerManager instance;
	public static int volume = 100;
	private final AudioPlayerManager playerManager;
	private final Map<Long, GuildMusicManager> musicManagers;
	
	private MusicPlayerManager() {
		musicManagers = new HashMap<>();
		instance = this;
		
		playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerLocalSource(playerManager);
		AudioSourceManagers.registerRemoteSources(playerManager);
		playerManager.registerSourceManager(new YoutubeAudioSourceManager());
		playerManager.registerSourceManager(new BandcampAudioSourceManager());
		playerManager.registerSourceManager(new BeamAudioSourceManager());
		playerManager.registerSourceManager(new HttpAudioSourceManager());
		playerManager.registerSourceManager(new LocalAudioSourceManager());
		playerManager.registerSourceManager(new VimeoAudioSourceManager());
		playerManager.registerSourceManager(new SoundCloudAudioSourceManager());
		playerManager.registerSourceManager(new TwitchStreamAudioSourceManager());
	}
	
	public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
		long guildID = guild.getIdLong();
		GuildMusicManager musicManager = musicManagers.get(guildID);
		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			musicManagers.put(guildID, musicManager);
		}
		
		guild.getAudioManager().setSendingHandler(musicManager.sendHandler);
		
		return musicManager;
	}
	
	public void loadAndPlay(TextChannel channel, String trackURL) {
		GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
		
		playerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				channel.sendMessage("Peepo is adding `" + track.getInfo().title + "` to queue.").queue();
				
				play(musicManager, track);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();
				List<AudioTrack> tracks = playlist.getTracks();
				
				if (firstTrack == null) {
					firstTrack = playlist.getTracks().get(0);
				}
				
				if (playlist.getTracks().size() > 1) {
					channel.sendMessage("Adding `" + playlist.getTracks().size() +"` tracks to queue from playlist: `" + playlist.getName() + "`").queue();
					tracks.forEach(musicManager.scheduler :: queue);
				}
				else {
					channel.sendMessage("Peepo is adding `" + firstTrack.getInfo().title + "` from playlist `" + playlist.getName() + "`.").queue();
					play(musicManager, firstTrack);
				}
			}

			@Override
			public void noMatches() {
				channel.sendMessage("Peepo couldn't find what you were looking for.").queue();;
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				channel.sendMessage("Peepo could not play: `" + exception.getMessage() + "`,\nSeverity:" + exception.severity).queue();
			}
			
		});
	}
	
	private void play(GuildMusicManager musicManager, AudioTrack track) {
		musicManager.scheduler.queue(track);
	}
	
	public static synchronized MusicPlayerManager getInstance() {
		if (instance == null) {
			instance = new MusicPlayerManager();
		}
		
		return instance;
	}
	
	public AudioPlayerManager getPlayer() {
		return playerManager;
	}
	
	public Map<Long, GuildMusicManager> getMusicManagers() {
		return musicManagers;
	}
}
