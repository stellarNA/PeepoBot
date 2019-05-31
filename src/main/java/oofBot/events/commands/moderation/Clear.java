package oofBot.events.commands.moderation;

import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import oofBot.main.PeepoBot;

public class Clear extends Command {
	
	public Clear() {
		this.name = "clear";
		this.aliases = new String[] {"Clear"};
		this.arguments = "[number]";
		this.help = "Clears the last inputed number of messages.";
		this.cooldown = 30;
		this.userPermissions = new Permission[] {Permission.MESSAGE_MANAGE};
		this.cooldownScope = CooldownScope.USER_CHANNEL;
		this.category = new Category("Moderation");
	}

	@Override
	protected void execute(CommandEvent event) {
		String[] args = event.getArgs().split(" ");
		int past = 0;
		if (args[0].isEmpty()) {
			event.reply("You must provide arguments for the command.\n`" + PeepoBot.PREFIX + "clear [number]`");
			return;
		}
		else {
			try {
				past = Integer.valueOf(args[0]);
			}
			catch (Exception e) {
				event.reply("Arguments may only contain numbers,\n`" + PeepoBot.PREFIX + "clear [number]`");
				return;
			}
			if (past > 100) {
				event.reply("You may only clear 100 messages at a time.");
				return;
			}
		}
		
		MessageHistory history = event.getChannel().getHistory();
		List<Message> messages = history.retrievePast(past + 1).complete();
		event.getChannel().purgeMessages(messages);
	}
}
