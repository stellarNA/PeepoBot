package oofBot.events.commands.music;

import java.awt.Color;
import java.util.ArrayList;
import java.util.function.BiConsumer;

import org.json.JSONObject;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.OrderedMenu;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.core.entities.Message;

import oofBot.main.PeepoBot;
import oofBot.resources.audio.MusicPlayerManager;

public class PlaySong extends Command {
	protected EventWaiter waiter;
	
	public PlaySong(EventWaiter waiter) {
		this.name = "play";
		this.aliases = new String[] {"Play"};
		this.help = "Plays the inputted link.";
		this.category = new Category("Music");
		this.arguments = "[url]";
		this.waiter = waiter;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (!event.getSelfMember().getVoiceState().inVoiceChannel()) {
			event.reply("Peepo is not currently in a voice channel.");
			return;
		}
		else if (event.getArgs().isEmpty()) {
			event.reply("You must provide arguments for this command.\n`" + PeepoBot.PREFIX + "play " + arguments + "`");
			return;
		}
		
		if (!event.getArgs().startsWith("http")) {
			String fixedSearch = new String(event.getArgs().replaceAll(" ", "+"));
			Unirest.get("https://www.googleapis.com/youtube/v3/search?part=snippet&key=" + PeepoBot.getYoutubeKey() + "&type=video&q=" + fixedSearch).asJsonAsync(new Callback<JsonNode>() {
				@Override
				public void completed(HttpResponse<JsonNode> response) {
					JSONObject obj = response.getBody().getObject();
					ArrayList<String> urls = new ArrayList<String>();
					for (int i = 0; i < 5; i++) {
						urls.add(new String("https://www.youtube.com/watch?v=" + obj.getJSONArray("items").getJSONObject(i).getJSONObject("id").getString("videoId")));
					}
					OrderedMenu.Builder builder = new OrderedMenu.Builder();
					builder.setEventWaiter(waiter);
					builder.allowTextInput(true);
					for (int i = 0; i < 5; i++) {
						builder.addChoice(obj.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").getString("title"));
					}
					builder.setDescription("Search Results");
					builder.useCancelButton(true);
					builder.setColor(Color.green);
					builder.setSelection(new BiConsumer<Message,Integer>() {

						@Override
						public void accept(Message arg0, Integer arg1) {
							MusicPlayerManager manager = MusicPlayerManager.getInstance();
							manager.loadAndPlay(event.getTextChannel(), urls.get(arg1.intValue() - 1));
							manager.getGuildMusicManager(event.getGuild()).player.setVolume(MusicPlayerManager.volume);
						}
						
					});
					builder.build().display(event.getChannel());
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
		else {
			String url = event.getArgs();
			MusicPlayerManager manager = MusicPlayerManager.getInstance();
			manager.loadAndPlay(event.getTextChannel(), url);
			manager.getGuildMusicManager(event.getGuild()).player.setVolume(MusicPlayerManager.volume);
		}
	}
}
