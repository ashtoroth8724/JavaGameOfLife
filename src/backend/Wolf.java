package backend;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Wolf extends Agent {

    int hunger;
    Random rand;
    Wolf(int x,int y){

        super(x,y,Color.red);
        hunger = 0;
        rand = new Random();
    }
	public boolean liveTurn(ArrayList<Agent> neighbors, Simulator world) {
		//implement that hunger increases until the wolf is on a cell with a sheep

        //we put a condition for the behavior of the wolf depending on the presence of a sheep in the detection range
        int detectionRange =3; 
        int eatingRange=1;
        int i=0;
        if (isInArea(x, y,detectionRange)){
            for(i=0;i<=world.getArrayAgent().size()-1;i++){
					
                //if we proceed to find the agent, we check if it is an instance of sheep
                if (world.getArrayAgent().get(i) instanceof Sheep ){
                    Agent agent = new Wolf(x,y);
                    //if the instance of sheep is detected to be inside the detection range it will 
                    if (world.getArrayAgent().get(i).getX()-Wolf.getX()<=detectionRange || world.getArrayAgent().get(i).getY()-Wolf.getY()<=detectionRange){

                    }
                
                }
            }
	
        }
    }
    //move randomly if no sheep in detection range 
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
    //implement a method that induce movement in the direction of the sheep in the detection range 
    
}