package backend;
import java.util.ArrayList;

import windowInterface.MyInterface;

public class Simulator extends Thread {

	private MyInterface mjf;
	
	private final int COL_NUM = 100;
	private final int LINE_NUM = 100;
	private final int LIFE_TYPE_NUM = 4;
	//Conway Radius : 1
	private final int LIFE_AREA_RADIUS = 1;
	//Animal Neighborhood Radius : 5
	private final int ANIMAL_AREA_RADIUS = 2;
	private ArrayList<Integer> fieldSurviveValues;
	private ArrayList<Integer> fieldBirthValues;
	
	private ArrayList<Agent> agents;
	
	private boolean stopFlag;
	private boolean pauseFlag;
	private boolean loopingBorder;
	private boolean clickActionFlag;
	private int loopDelay = 150;

	//TODO : add missing attribute(s)
	private int width;
	private int height;

	public Simulator(MyInterface mjfParam) {
		mjf = mjfParam;
		stopFlag=false;
		pauseFlag=false;
		loopingBorder=false;
		clickActionFlag=false;

		agents = new ArrayList<Agent>();
		fieldBirthValues = new ArrayList<Integer>();
		fieldSurviveValues = new ArrayList<Integer>();

		//TODO-COMPLETE: add missing attribute initialization
		//might want to changes those values later
		this.width=100;
		this.height=100;
		
		
		//Default rule : Survive always, birth never
		for(int i =0; i<9; i++) {
			fieldSurviveValues.add(i);
		}
		
	}

	public int getWidth() {
		//TODO-COMPLETE : replace with proper return
		return this.width;
	}

	public int getHeight() {
		//TODO-COMPLETE : replace with proper return
		return this.height;
	}

	//Should probably stay as is
	public void run() {
		int stepCount=0;
		while(!stopFlag) {
			stepCount++;
			makeStep();
			mjf.update(stepCount);
			try {
				Thread.sleep(loopDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(pauseFlag && !stopFlag) {
				try {
					Thread.sleep(loopDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * method called at each step of the simulation
	 * makes all the actions to go from one step to the other
	 */
	public void makeStep() {
		// agent behaviors first
		// only modify if sure of what you do
		// to modify agent behavior, see liveTurn method
		// in agent classes
		for(Agent agent : agents) {
			ArrayList<Agent> neighbors = 
					this.getNeighboringAnimals(
					agent.getX(), 
					agent.getY(), 
					ANIMAL_AREA_RADIUS);
			if(!agent.liveTurn(
					neighbors,
					this)) {
				agents.remove(agent);
			}
		}
		//then evolution of the field
		// TODO : apply game rule to all cells of the field
		
		/* you should distribute this action in methods/classes
		 * don't write everything here !
		 * 
		 * the idea is first to get the surrounding values
		 * then count how many are alive
		 * then check if that number is in the lists of rules
		 * if the cell is alive 
		 * 		and the count is in the survive list,
		 * 		then the cell stays alive
		 * if the cell is not alive
		 * 		and the count is in the birth list,
		 * 		then the cell becomes alive 	
		 */
		
		
		
		
	}
	
	/*
	 * leave this as is
	 */
	public void stopSimu() {
		stopFlag=true;
	}
	
	/*
	 * method called when clicking pause button
	 */
	public void togglePause() {
		// TODO-COMPLETE : actually toggle the corresponding flag
		pauseFlag = !pauseFlag;
	}
	
	/**
	 * method called when clicking on a cell in the interface
	 */
	public void clickCell(int x, int y) {
		int currentCellValue = getCell(x, y);
		int newCellValue;
		if (currentCellValue == 0) {
			System.out.println("Cell :" + x + "," + y + " is now alive");
			newCellValue = 1; // If the cell is dead, make it alive
		} else {
			System.out.println("Cell :" + x + "," + y + " is now dead");
			newCellValue = 0; // If the cell is alive, make it dead
		}
		
		setCell(x, y, newCellValue);
	}
	
	/**
	 * get cell value in simulated world
	 * @param x coordinate of cell
	 * @param y coordinate of cell
	 * @return value of cell
	 */
	public int getCell(int x, int y) {
		//TODO : complete method with proper return
		return 0;
	}
	/**
	 * 
	 * @return list of Animals in simulated world
	 */
	public ArrayList<Agent> getAnimals(){
		return agents;
	}
	/**
	 * selects Animals in a circular area of simulated world
	 * @param x center
	 * @param y center
	 * @param radius
	 * @return list of agents in area
	 */
	public ArrayList<Agent> getNeighboringAnimals(int x, int y, int radius){
		ArrayList<Agent> inArea = new ArrayList<Agent>();
		for(int i=0;i<agents.size();i++) {
			Agent agent = agents.get(i);
			if(agent.isInArea(x,y,radius)) {
				inArea.add(agent);
			}
		}
		return inArea;
	}

	/**
	 * set value of cell
	 * @param x coord of cell
	 * @param y coord of cell
	 * @param val to set in cell
	 */
	public void setCell(int x, int y, int val) {
		//TODO : complete method
	}
	
	/**
	 * 
	 * @return lines of file representing 
	 * the simulated world in its present state
	 */
	public ArrayList<String> getSaveState() {
		//TODO : complete method with proper return
		return null;
	}
	/**
	 * 
	 * @param lines of file representing saved world state
	 */
	public void loadSaveState(ArrayList<String> lines) {
		/*
		 * First some checks that the file is usable
		 * We call early returns in conditions like this
		 * "Guard clauses", as they guard the method
		 * against unwanted inputs
		 */
		if(lines.size()<=0) {
			return;
		}
		String firstLine = lines.get(0);
		String[] firstLineElements = firstLine.split(";");
		if(firstLineElements.length<=0) {
			return;
		}
		/*
		 * now we fill in the world 
		 * with the content of the file
		 */
		for(int y =0; y<lines.size();y++) {
			String line = lines.get(y);
			String[] lineElements = line.split(";");
			for(int x=0; x<lineElements.length;x++) {
				String elem = lineElements[x];
				int value = Integer.parseInt(elem);
				setCell(x, y, value);
			}
		}
	}

	/**
	 * called by button, with slider providing the argument
	 * makes a new world state with random cell states
	 * @param chanceOfLife the chance for each cell 
	 * to be alive in new state
	 */
	public void generateRandom(float chanceOfLife) {
		//TODO : complete method
		/*
		 * Advice :
		 * as you should probably have a separate class
		 * representing the field of cells...
		 * maybe just make a constructor in there 
		 * and use it here
		 */
	}
	
	public boolean isLoopingBorder() {
		//TODO : complete method with proper return
		return false;
	}
	
	public void toggleLoopingBorder() {
		//TODO : complete method
		
	}
	
	public void setLoopDelay(int delay) {
		//TODO : complete method
	}
	
	public void toggleClickAction() {
		//TODO : complete method
	}

	/**
	 * prepare the content of a file saving present ruleSet
	 *  as you might want to save a state,
	 *  initialy written in this class constructor 
	 *  as a file for future use
	 * @return File content as an ArrayList of Lines (String)
	 * @see loadRule for inverse process
	 */
	public ArrayList<String> getRule() {
		//TODO : complete method with proper return
		
		return null;
	}

	public void loadRule(ArrayList<String> lines) {
		if(lines.size()<=0) {
			System.out.println("empty rule file");
			return;
		}
		//TODO : remove previous rule (=emptying lists)
		
		
		String surviveLine = lines.get(0);
		String birthLine = lines.get(1);
		String[] surviveElements = surviveLine.split(";");
		for(int x=0; x<surviveElements.length;x++) {
			String elem = surviveElements[x];
			int value = Integer.parseInt(elem);
			//TODO : add value to possible survive values
			
		}
		String[] birthElements = birthLine.split(";");
		for(int x=0; x<birthElements.length;x++) {
			String elem = birthElements[x];
			int value = Integer.parseInt(elem);
			//TODO : add value to possible birth values
			
		}
	}
	
	public ArrayList<String> getAgentsSave() {
		//TODO : Same idea as the other save method, but for agents
		return null;
	}

	public void loadAgents(ArrayList<String> stringArray) {
		//TODO : Same idea as other load methods, but for agent list
		
	}

	/**
	 * used by label in interface to show the active click action
	 * @return String representation of click action
	 */
	public String clickActionName() {
		// TODO : initially return "sheep" or "cell"
		// depending on clickActionFlag
		return "";
	}

}
