package oofBot.events.commands;

import java.awt.Color;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.core.EmbedBuilder;

public class RandomCat extends Command {
	
	public RandomCat() {
		this.name = "cat";
		this.aliases = new String[] {"Cat"};
		this.category = new Category("General");
		this.help = "Posts a random cat picture. :cat:";
	}

	@Override
	protected void execute(CommandEvent event) {
		Unirest.get("http://aws.random.cat/meow").asJsonAsync(new Callback<JsonNode>() {

			@Override
			public void completed(HttpResponse<JsonNode> response) {
				EmbedBuilder builder = new EmbedBuilder();
				builder.setTitle(":cat:");
				builder.setColor(Color.GREEN);
				builder.setImage(response.getBody().getObject().getString("file"));
				event.reply(builder.build());
			}

			@Override
			public void failed(UnirestException e) {
				e.printStackTrace();
				event.reactError();
			}

			@Override
			public void cancelled() {
				event.reactError();
			}
			
		});
	}
}
