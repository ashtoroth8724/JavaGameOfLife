package backend;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Agent {
	protected int x;
	protected int y;
	protected Color color;
	
	protected Agent(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public Color getDisplayColor() {
		return color;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
//presence of an agent at those coordinates
	public boolean agentPresence(int x, int y){

		if (this.getX() == x && this.getY() == y) {
			return true;
		}
		else {
			return false;
		}
	}
//presenec of an agent in an area
	public boolean isInArea(int x, int y, int radius) {
		int diffX = this.x-x;
		int diffY = this.y-y;
		int dist = (int) Math.floor(Math.sqrt(diffX*diffX+diffY*diffY));
		return dist<radius;
	}
	
	// Does whatever the agent does during a step
	// then returns a boolean
	// if false, agent dies at end of turn
	// see step function in Simulator
	public abstract boolean liveTurn(ArrayList<Agent> neighbors, Simulator world);
	
	
}
