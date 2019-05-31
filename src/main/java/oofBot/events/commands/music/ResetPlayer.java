package oofBot.events.commands.music;

import java.util.Map;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import oofBot.resources.audio.GuildMusicManager;
import oofBot.resources.audio.MusicPlayerManager;
import oofBot.resources.audio.TrackScheduler;

public class ResetPlayer extends Command {
	
	public ResetPlayer() {
		this.name = "reset";
		this.aliases = new String[] {"Reset"};
		this.help = "Resets the player. (Destroys the currently running queue)";
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
			event.reply("Peepo is not playing a queue.");
		}
		TrackScheduler scheduler = manager.getGuildMusicManager(event.getGuild()).scheduler;
		AudioPlayer player = manager.getGuildMusicManager(event.getGuild()).player;
		Map<Long, GuildMusicManager> musicManagers = manager.getMusicManagers();
		
		synchronized (musicManagers) {
			scheduler.getQueue().clear();
			player.destroy();
			event.getGuild().getAudioManager().setSendingHandler(null);
			musicManagers.remove(event.getGuild().getIdLong());
		}
		
		GuildMusicManager guildManager = manager.getGuildMusicManager(event.getGuild());
		event.getGuild().getAudioManager().setSendingHandler(guildManager.sendHandler);
		event.reply("The player has been reset.");
	}
}
