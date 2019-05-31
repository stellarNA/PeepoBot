package oofBot.events.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import oofBot.resources.audio.MusicPlayerManager;

public class SkipSong extends Command {
	
	public SkipSong() {
		this.name = "skip";
		this.aliases = new String[] {"Skip", "next", "Next"};
		this.help = "Skips the current song playing in the queue.";
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
		
		manager.getGuildMusicManager(event.getGuild()).scheduler.nextTrack();
		event.reply("Skipped the currently playing track.");
	}
}
