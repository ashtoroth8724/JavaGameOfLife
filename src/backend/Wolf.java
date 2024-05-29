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
           
            //iterations for the length of the arraylist
            for(i=0;i<=world.getArrayAgent().size()-1;i++){
                
                //if we proceed to find the agent, we check if it is an instance of sheep
                if (world.getArrayAgent().get(i) instanceof Sheep ){
                    System.out.println("sheep detected");
                    Agent wolf = new Wolf(x,y);
                    //if the instance of sheep is detected to be inside the detection range it will 
                    if (world.getArrayAgent().get(i).getX()-wolf.getX()<=detectionRange || world.getArrayAgent().get(i).getY()-wolf.getY()<=detectionRange){
                        System.out.println("sheep in range");
                        int xSpotted = world.getArrayAgent().get(i).getX()-wolf.getX();
                        int ySpotted = world.getArrayAgent().get(i).getY()-wolf.getY(); 
                        // here the values calculated can be over or under 0
                        //thus we need to further determine its meaning, a negative xSpotted means that the value of x for the wolf is 
                        //greater than the sheep thus it means that the wolf will have to go in the negative x direction (to the left) in order to reach 
                        //the sheep
                        //We also need to determine the different cases possible by splitting the movements in 8 directions 
                        //such as the four mains directions and the 4 resulting diagonals 
                        //ySpotted<0 means go to -y direction
                        //xSpotted<0 means to go in the -x direction
                        //if the distance in x is smaller than the distance in y then the wolf will start by reducing the distance in y 
                        //and according to the sign of ySpotted

                        //taking care of the displacement on y if there is a dy
                        if (xSpotted < ySpotted && ySpotted<0){
                            y-=1;
                        } 
                        if (xSpotted < ySpotted && ySpotted>0){
                            y+=1;
                        }
                        //displacement on x if there is no dy
                        if (xSpotted < ySpotted && ySpotted==0){
                            x-=1;
                        } 
                        if (xSpotted > ySpotted && ySpotted==0){
                            x+=1;
                        }
                        //displacement in x when there is a dx
                        if (xSpotted>ySpotted && xSpotted<0){
                            x-=1;
                        }
                        if (xSpotted>ySpotted && xSpotted>0){
                            x+=1;
                        }
                        if (xSpotted < ySpotted && xSpotted==0){
                            y-=1;
                        } 
                        if (xSpotted > ySpotted && xSpotted==0){
                            y+=1;
                        }

                        if (xSpotted==ySpotted && xSpotted>0){
                            x+=1;
                            y+=1;
                        }
                        if (xSpotted==ySpotted && xSpotted<0){
                            x-=1;
                            y-=1;
                        }
                        if (xSpotted == -ySpotted && xSpotted>0){
                            x+=1;
                            y-=1;
                        }
                        if (xSpotted == -ySpotted && xSpotted<0){
                            x-=1;
                            y+=1;
                        }
                        if(world.getArrayAgent().get(i).getX() == wolf.getX() && world.getArrayAgent().get(i).getY() == wolf.getY()){
                            world.getArrayAgent().remove(i);
                            hunger = 0;
                            System.out.println("sheep killed");
                        }
                        
                    }
                
                }
                else{
                    hunger++;
                }
            }
	
        }
        else{
            this.moveRandom();
            hunger++;
            System.out.println("nothing detected");
            
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
   
    
}