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
        
        int i=0;
        //Agent in detectionrange
        if (isInArea(x, y,detectionRange)){
           boolean sheepFound = false;
            //iterations for the length of the arraylist
            for(i=0;i<=world.getArrayAgent().size()-1;i++){
                Agent agentIter = world.getArrayAgent().get(i);
                //if we proceed to find the agent, we check if it is an instance of sheep
                if(agentIter instanceof Sheep){
                    //if the agent is a sheep, we check if it is in the detection range
                    if(isInArea(agentIter.getX(),agentIter.getY(),detectionRange)){
                        sheepFound = true;
                        //if the sheep is in the detection range, the wolf moves towards the sheep
                        moveTowardsSheep((Sheep)agentIter);
                        //if the wolf is on the same cell as the sheep, the sheep is eaten
                        if(x==agentIter.getX() && y==agentIter.getY()){
                            //eat the sheep
                            world.getArrayAgent().remove(agentIter);
                            hunger=0;
                            break;
                        }
                        else{
                            hunger++;
                            break;
                        }
                    }
                }
	
            } if (sheepFound==false){
                this.moveRandom();
                hunger++;
            }
        }
        else{
            this.moveRandom();
            hunger++;            
        }
        return hunger<8;
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


    public void moveTowardsSheep(Sheep sheep) {
        int deltaX = sheep.getX() - this.x;
        int deltaY = sheep.getY() - this.y;
       
        // Normalize deltas to get direction
        if (deltaX > 0) this.x += 1;
        else if (deltaX < 0) this.x -= 1;
    
        if (deltaY > 0) this.y += 1;
        else if (deltaY < 0) this.y -= 1;
    }
    
}