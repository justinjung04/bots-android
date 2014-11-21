package com.example.botsandroid;

public enum Command {
	UP("up"), DOWN("down"), LEFT("left"), RIGHT("right");
	
	private String Command;
	
	Command(String Command) {
		this.Command = Command;
	}
	
	public String toString() {
		return Command;
	}
}
