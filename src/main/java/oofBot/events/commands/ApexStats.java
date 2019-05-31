package oofBot.events.commands;

import java.awt.Color;
import org.json.JSONObject;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.core.EmbedBuilder;

public class ApexStats extends Command {
	
	public ApexStats() {
		this.name = "apex";
		this.aliases = new String[] {"Apex"};
		this.help = "Prints the inputted users stats in an embed. (Platform defaults to pc unless specified)";
		this.category = new Category("General");
		this.arguments = "[username] <platform>";
		this.cooldown = 10;
		this.cooldownScope = CooldownScope.GUILD;
	}

	@Override
	protected void execute(CommandEvent event) {
		String[] args = event.getArgs().split(" ");
		if (event.getArgs().isEmpty()) {
			event.reply("You must provide arguments.");
			return;
		}
		else if (args.length > 2) {
			event.reply("More arguments than command accepts.");
			return;
		}
		String platform;
		String username = args[0];
		
		if (args.length == 1) {
			platform = new String("pc");
		}
		else if (args[1].equalsIgnoreCase("pc")) {
			platform = new String("pc");
		}
		else if (args[1].equalsIgnoreCase("ps4") || args[1].equalsIgnoreCase("psn")) {
			platform = new String("psn");
		}
		else if (args[1].equalsIgnoreCase("xb1") || args[1].equalsIgnoreCase("xbl")) {
			platform = new String("xbl");
		}
		else {
			event.reply("Unrecognized platform.");
			return;
		}
		
		Unirest.get("https://apextab.com/api/search.php?platform=" + platform + "&search=" + username).asJsonAsync(new Callback<JsonNode>() {
			EmbedBuilder builder = new EmbedBuilder();
			
			@Override
			public void completed(HttpResponse<JsonNode> response) {
				if (response.getBody().getObject().getString("totalresults").equals("0")) {
					event.reply("Player not found on platform.");
					return;
				}
				
				JSONObject obj = response.getBody().getObject().getJSONArray("results").getJSONObject(0);
				
				builder.setThumbnail(obj.getString("avatar"));
				builder.setColor(Color.RED);
				builder.setTitle(obj.getString("name") + "'s stats");
				builder.setFooter("- Powered by ApexTab API", "https://avatars1.githubusercontent.com/u/47739369?s=460&v=4");
				builder.addField("Level:", obj.getString("level"), false);
				builder.addField("Last played: ", obj.getString("legend"), false);
				builder.addField("Kills: ", obj.getString("kills"), false);
				
				event.reply(builder.build());
			}

			@Override
			public void failed(UnirestException e) {
				System.out.println("Failed.");
				e.printStackTrace();
				event.reactError();
			}

			@Override
			public void cancelled() {
				System.out.println("Cancelled.");
				event.reactError();
			}
			
		});
	}
}
