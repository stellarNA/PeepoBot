package oofBot.events.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import oofBot.resources.audio.MusicPlayerManager;
import oofBot.resources.audio.TrackScheduler;

public class LoopSong extends Command {
	
	public LoopSong() {
		this.name = "loop";
		this.aliases = new String[] {"Loop"};
		this.help = "Toggles the bot to loop the currently palying song.";
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
		scheduler.setLooping(!scheduler.isLooping());
		
		if (scheduler.isLooping()) {
			event.reply("The currently playing song is now looping");
		}
		else {
			event.reply("The currently playing song is no longer looping");
		}
	}
}
