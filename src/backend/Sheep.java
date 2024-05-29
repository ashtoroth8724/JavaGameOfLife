package backend;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

// example of basic animal.
// do not hesitate to make it more complex
// and DO add at least another species that interact with it
// for example wolves that eat Sheep
public class Sheep extends Agent {
	
	int hunger;
	Random rand;
	
	Sheep(int x,int y){
		//first we call the constructor of the superClass(Animal)
		//with the values we want.
		// here we decide that a Sheep is initially white using this constructor
		super(x,y,Color.pink);
		// we give our sheep a hunger value of zero at birth
		hunger = 0;
		//we initialize the random number generator we will use to move randomly
		rand = new Random();
	}



	/**
	 * action of the animal
	 * it can interact with the cells or with other animals
	 * as you wish
	 */
	public boolean liveTurn(ArrayList<Agent> neighbors, Simulator world) {
		if(world.getCell(x, y)==1) {
			world.setCell(x, y, 0);
		} else {
			hunger++;
		}
		this.moveRandom();
		return hunger<10; //condition to be alive
	}

	private void moveRandom() {
		int direction = rand.nextInt(4);
		if(direction == 0) {
			x+=1;
		}
		if(direction == 1) {
			y+=1;
		}
		if(direction == 2) {
			x-=1;
		}
		if(direction == 3) {
			y-=1;
		}
	}


}
