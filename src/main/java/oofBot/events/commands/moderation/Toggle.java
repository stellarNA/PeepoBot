package oofBot.events.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.core.Permission;
import oofBot.resources.ToggleListManager;

public class Toggle extends Command {
	protected EventWaiter waiter;
	
	public Toggle(EventWaiter waiter) {
		this.name = "toggle";
		this.aliases = new String[] {"Toggle"};
		this.help = "Allows the owner to toggle on or off toggleable commands.";
		this.hidden = true;
		this.category = new Category("Moderation");
		this.userPermissions = new Permission[] {Permission.ADMINISTRATOR};
		this.waiter = waiter;
	}

	@Override
	protected void execute(CommandEvent event) {
		ToggleListManager manager = ToggleListManager.getInstance();
		manager.toggleComand(event.getTextChannel(), event.getAuthor(), waiter);
	}
}
