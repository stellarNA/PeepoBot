package oofBot.events.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import oofBot.main.PeepoBot;

public class Random extends Command {
	
	public Random() {
		this.name = "random";
		this.aliases = new String[] {"Random"};
		this.arguments = "[range1] [range2]";
		this.help = "Returns a random number between Range 1 and Range 2";
		this.category = new Category("General");
	}

	@Override
	protected void execute(CommandEvent event) {
		String[] args = event.getArgs().split(" ");
		
		if (args.length < 2 || args.length > 2) {
			event.reply("Arguments invalid.");
			event.reply("`!random " + arguments + "`");
			return;
		}
		try {
			if (Integer.valueOf(args[0]) > Integer.valueOf(args[1])) {
				event.reply("Argument 1 cannot be larger than Argument 2.\n" + "`" + PeepoBot.PREFIX + "random " + arguments + "`");
				return;
			}
		}
		catch (Exception e) {
			event.reply("Arguments can only contain numbers.\n" + "`" + PeepoBot.PREFIX +"random " + arguments + "`");
			return;
		}
		
		int a = Integer.valueOf(args[0]);
		int b = Integer.valueOf(args[1]);
		event.reply("" + (int)(a + (b-a)*Math.random()));
	}
}
