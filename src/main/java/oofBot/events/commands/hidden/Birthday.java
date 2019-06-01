package oofBot.events.commands.hidden;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Birthday extends Command {
	
	public Birthday() {
		this.name = "birthday";
		this.aliases = new String[] {"Birthday"};
		this.category = new Category("Hidden");
		this.hidden = true;
		this.ownerCommand = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("D");
		String formattedDate = formatter.format(date);
		if (formattedDate.equals("176")) {
			event.reply("Happy birthday kitten!");
		}
	}
}
