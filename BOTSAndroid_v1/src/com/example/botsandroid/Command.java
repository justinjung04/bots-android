package com.example.botsandroid;

public enum Command {
	up, down, left, right;
	
	public static Command get(double angle) {
		if(inRange(angle, 45, 135)){
            return Command.up;
        }
        else if(inRange(angle, 0, 45) || inRange(angle, 315, 360)){
            return Command.right;
        }
        else if(inRange(angle, 225, 315)){
            return Command.down;
        }
        else{
            return Command.left;
        }
	}
	
	private static boolean inRange(double angle, float init, float end) {
        return (angle >= init) && (angle < end);
    }
}
