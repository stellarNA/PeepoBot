package oofBot.events.commands.music;

import java.awt.Color;
import java.util.Queue;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.EmbedBuilder;
import oofBot.resources.audio.MusicPlayerManager;

public class ShuffleQueue extends Command {
	
	public ShuffleQueue() {
		this.name = "shuffle";
		this.aliases = new String[] {"Shuffle"};
		this.help = "Shuffles the queue";
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
			return;
		}
		
		manager.getGuildMusicManager(event.getGuild()).scheduler.shuffleQueue();
		event.reply("The queue has been shuffled.");
		Queue<AudioTrack> queue = manager.getGuildMusicManager(event.getGuild()).scheduler.getQueue();
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Shuffled queue.");
		builder.setColor(Color.GREEN);
		int i = 1;
		builder.addField("Playing :", manager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack().getInfo().title, false);
		for (AudioTrack track : queue) {
			builder.addField(i + ".", track.getInfo().title, false);
			i++;
		}
		
		event.reply(builder.build());
	}
}
