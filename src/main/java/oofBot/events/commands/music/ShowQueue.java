package oofBot.events.commands.music;

import java.awt.Color;
import java.util.Queue;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Paginator;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import oofBot.resources.audio.MusicPlayerManager;


public class ShowQueue extends Command {
	protected EventWaiter waiter;
	
	public ShowQueue(EventWaiter waiter) {
		this.name = "queue";
		this.aliases = new String[] {"Queue"};
		this.category = new Category("Music");
		this.help = "Shows the current music queue.";
		this.waiter = waiter;
	}

	@Override
	protected void execute(CommandEvent event) {
		if (!event.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
			event.reply("Peepo is not connected to a voice channel.");
			return;
		}
		
		MusicPlayerManager manager = MusicPlayerManager.getInstance();
		Queue<AudioTrack> queue = manager.getGuildMusicManager(event.getGuild()).scheduler.getQueue();
		if (manager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack() == null) {
			event.reply("Peepo is not playing a queue.");
			return;
		}
		
		Paginator.Builder builder = new Paginator.Builder();
		builder.setUsers(event.getMember().getUser());
		builder.setColor(Color.GREEN);
		builder.setEventWaiter(waiter);
		builder.setItemsPerPage(25);
		builder.setText("PeepoBot's Queue");
		builder.showPageNumbers(true);
		builder.useNumberedItems(true);
		builder.addItems("Playing: " + manager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack().getInfo().title);
		
		for (AudioTrack track : queue) {
			builder.addItems(track.getInfo().title);
		}
		
		Paginator menu = builder.build();
		menu.display(event.getChannel());
	}
}
