package oofBot.resources;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.SelectionDialog;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class ToggleListManager {
	private static ToggleListManager instance;
	private final Map<Long, GuildToggleList> toggleLists;
	private int dialogIndex;
	
	private ToggleListManager() {
		toggleLists = new HashMap<>();
		instance = this;
		dialogIndex = 0;
	}
	
	public synchronized GuildToggleList getGuildToggleList(Guild guild) {
		long guildID = guild.getIdLong();
		GuildToggleList toggleList = toggleLists.get(guildID);
		if (toggleList == null) {
			toggleList = new GuildToggleList();
			toggleLists.put(guildID, toggleList);
		}
		
		return toggleList;
	}
	
	public void toggleComand(TextChannel channel, User user, EventWaiter waiter) {
		GuildToggleList toggleList = getGuildToggleList(channel.getGuild());
		Map<ToggleCommand, Boolean> list = toggleList.getToggles();
		List<ToggleCommand> commands = new ArrayList<ToggleCommand>();
		
		SelectionDialog.Builder builder = new SelectionDialog.Builder();
		builder.addUsers(user);
		for (ToggleCommand toggleCommand : list.keySet()) {
			commands.add(toggleCommand);
			builder.addChoices(toggleCommand.getName());
		}
		builder.setColor(Color.GREEN);
		builder.setEventWaiter(waiter);
		builder.useLooping(true);
		builder.setSelectedEnds("->", commands.get(dialogIndex).toString()); // make right selected end the value of 'disabled'
		builder.setSelectionConsumer(new BiConsumer<Message, Integer>() {

			@Override
			public void accept(Message message, Integer integer) {
				toggleList.toggleCommandInList(commands.get(integer.intValue() - 1));
				setDialogIndex(integer.intValue() - 1);
			}
			
		});
		builder.setCanceled(new Consumer<Message>() {

			@Override
			public void accept(Message arg0) {
				
			}
			
		});
		builder.build().display(channel);
	}
	
	public static synchronized ToggleListManager getInstance() {
		if (instance == null) {
			instance = new ToggleListManager();
		}
		
		return instance;
	}
	
	private void setDialogIndex(int x) {
		dialogIndex = x;
	}
}
