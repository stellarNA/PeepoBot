package oofBot.main;

public enum Prefix {
	EXCLAMATION ('!'), TILDE ('~'), DOLLARSIGN ('$'), PERCENT ('%'), AMPERSAND ('&'), PLUS ('+'), QUESTION ('?'), PERIOD ('.');
	
	private char PREFIX;
	
	Prefix(char c) {
		PREFIX = c;
	}

	public char getPREFIX() {
		return PREFIX;
	}
}
