package oofBot.resources.audio;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class TrackScheduler extends AudioEventAdapter {
	private final AudioPlayer player;
	private final Queue<AudioTrack> queue;
	private boolean looping = false;
	private AudioTrack lastTrack;
	
	public TrackScheduler(AudioPlayer player) {
		this.player = player;
		this.queue = new LinkedList<>();
	}
	
	public void queue(AudioTrack track) {
		if (!player.startTrack(track, true)) {
			queue.offer(track);
		}
	}
	
	public void nextTrack() {
		player.startTrack(queue.poll(), false);
	}
	
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		this.lastTrack = track;
		
		if (endReason.mayStartNext) {
			if (looping) {
				player.startTrack(lastTrack.makeClone(), false);
			}
			else {
				nextTrack();
			}
		}
	}
	
	public boolean isLooping() {
		return looping;
	}
	
	public void setLooping(boolean looping) {
		this.looping = looping;
	}
	
	public void shuffleQueue() {
		Collections.shuffle((List<?>) queue);
	}
	
	public Queue<AudioTrack> getQueue() {
		return queue;
	}
	
	public AudioTrack getLastTrack() {
		return lastTrack;
	}
}
