package com.luxl.botsandroid;

public class FlingCalculator {
	private double angle;
	
	public FlingCalculator(float x1, float y1, float x2, float y2) {
		angle = getAngle(x1, y1, x2, y2);
	}
	
	public Command getCommand() {
		if(inRange(angle, 45, 135)){
            return Command.UP;
        }
        else if(inRange(angle, 0, 45) || inRange(angle, 315, 360)){
            return Command.RIGHT;
        }
        else if(inRange(angle, 225, 315)){
            return Command.DOWN;
        }
        else{
            return Command.LEFT;
        }
	}
	
	private double getAngle(float x1, float y1, float x2, float y2) {
	    double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
	    return (rad*180/Math.PI + 180)%360;
	}
	
	private boolean inRange(double angle, float init, float end) {
        return (angle >= init) && (angle < end);
    }
}
