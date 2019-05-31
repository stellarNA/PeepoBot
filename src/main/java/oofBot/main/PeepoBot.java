package oofBot.main;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import oofBot.events.commands.*;
import oofBot.events.commands.hidden.*;
import oofBot.events.commands.memes.*;
import oofBot.events.commands.moderation.*;
import oofBot.events.commands.music.*;

/*
 * Created by stellar#5949 on 2/26/18
 * Includes moderation functionality, memes, and other general purpose music commands.
 * */

public class PeepoBot {
	public static final char PREFIX = Prefix.TILDE.getPREFIX();
	public static CommandClient commandClient;
	public static JDA jda;
	
	public static void main(String[] args) throws LoginException {
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		String token = new String("NTUwNDE2Nzk1MjMyODk1MDAx.XOokCQ.LiEimsOYrHRdJwpynDXoIFELW5U");
		builder.setToken(token);
		
		EventWaiter waiter = new EventWaiter();
		
		CommandClientBuilder commandBuilder = new CommandClientBuilder();
		commandBuilder.setOwnerId("217856240624074752");
		commandBuilder.setPrefix("" + PREFIX);
		commandBuilder.setHelpWord("help");
		commandBuilder.setGame(Game.playing("Autism 24/7 | " + PREFIX + "help"));
		commandBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);
		
		
		//General Commands
		commandBuilder.addCommands(
			new Ping(),
			new Random(),
			new BotInvite(),
			new RandomCat(),
			new ApexStats(),
			new Say()
		);
		
		//Music Commands
		commandBuilder.addCommands(
			new JoinVoiceChannel(),
			new LeaveVoiceChannel(),
			new PlaySong(waiter),
			new ChangeVolume(),
			new SkipSong(),
			new ShowQueue(waiter),
			new LoopSong(),
			new ShuffleQueue(),
			new StopQueue(),
			new PauseSong(),
			new ResetPlayer()
		);
		
		//Memes Commands
		commandBuilder.addCommands(
			new HoneyMustard(),
			new Bean(),
			new Autism()
		);
		
		//Moderation Commands
		commandBuilder.addCommands(
			new Clear(),
			new Toggle(waiter)
		);
		
		//Hidden Commands
		commandBuilder.addCommands(
			new Shutdown(),
			new ChangePresence(),
			new TestCommand(waiter)
		);
		
		commandClient = commandBuilder.build();
		builder.setAudioEnabled(true);
		builder.setAudioSendFactory(new NativeAudioSendFactory());
		builder.addEventListener(waiter);
		builder.addEventListener(commandClient);
		jda = builder.build();
	}
}