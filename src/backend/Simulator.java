package backend;
import java.awt.Color;
import java.util.ArrayList;

import windowInterface.MyInterface;

public class Simulator extends Thread {

	private MyInterface mjf;
	
	private final int COL_NUM = 10;
	private final int LINE_NUM = 10;
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
	private double randomDansitySlider = 0.5;
	private int width;
	private int height;
	private boolean enableLogs;
	private Table table;
	private boolean cellDensityToggle;

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
		this.width=COL_NUM;
		this.height=LINE_NUM;
		enableLogs = true; // for debugging purposes
		table = new Table(height, width, this);
		cellDensityToggle=false;

		
		
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
	//we define the initial size of the arraylist as 1 in order to ease the code in the clickAgent part
	public void StartingCap(int initialCapacity){
		agents = new ArrayList<>(initialCapacity);
	}
	//Should probably stay as is
	public void run() {
		int stepCount=0;
		System.out.println("Step Count: "+ stepCount);
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
		// TODO-INPROGRESS : apply game rule to all cells of the field
		this.applyRule();


	}

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
		if (enableLogs) {
			if (pauseFlag) {
				System.out.println("togglePause called, Simulation paused");
			} else {
				System.out.println("togglePause called, Simulation unpaused");
			}
		}
	}
	
	/**
	 * method called when clicking on a cell in the interface
	 */
	public void clickCell(int x, int y) {
		if (clickActionFlag) {
			int currentCellValue = getCell(x, y);
			int newCellValue = 0;
			if(cellDensityToggle) {
				if (currentCellValue == -1) {
					newCellValue = 0;
				}
				if (currentCellValue == 0) {
					newCellValue = 1;
				} 
				if (currentCellValue == 1) {
					newCellValue = 2;
				}
				if (currentCellValue == 2) {
					newCellValue = 3;
				} 
				if (currentCellValue == 3) {
					newCellValue = -1;
				}
			} else {
				if (currentCellValue == 0) {
					newCellValue = 1;
				} else {
					newCellValue = 0;
				}
			}
		
			if (enableLogs) {
				System.out.println("clickCell Called, cell :" + x + "," + y + " is now" + newCellValue + "");
			}
			
			this.setCell(x, y, newCellValue);
			
			
		} 
		else {
			int radius = 1;
			Agent agent = new Sheep(x,y);
			
			if(agent.isInArea(x,y,radius)){
				System.out.println("true");
				for(int i=0;i<=agents.size();i++){
					if (agents.get(i) instanceof Sheep){
						agents.remove(i);
					}
					else{
						setSheep(x,y);
					}
				}
			}
			
			
			if (enableLogs) {
				System.out.println("clickAgent Called, Agent created at: " + x + "," + y + "");
			}
		}
	}
	
	//TODO-INPROGRESS : set agent (x y agent) load an agent to coordinates x,y
	public void setSheep(int x,int y){

		Sheep sheep =  new Sheep(x,y);
		
		agents.add(sheep);
	}



	public void printAgents() {
        for (Agent Agent : agents) {
            String agentType = Agent.getClass().getSimpleName();
            int x = Agent.getX();
            int y = Agent.getY();
            Color color = Agent.getDisplayColor();
            
            System.out.println("Agent Type: " + agentType);
            System.out.println("Position: (" + x + ", " + y + ")");
            System.out.println("Color: " + color);
            System.out.println();
        }
		
	}
	/**
	 * get cell value in simulated world
	 * @param x coordinate of cell
	 * @param y coordinate of cell
	 * @return value of cell
	 */
	public int getCell(int x, int y) {
		//TODO-INPROGRESS :
		//complete method with proper return
		return this.table.getCell(x,y).getValue();
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
		this.table.getCell(x, y).setValue(val);
	}
	
	public void countAround(int x, int y) {
		//enableLogs
		//getCell
		//if loopingBorder TRUE, border count as living.
		this.table.countNear(x, y);
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
		System.out.println("loadSaveState called");
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
			System.out.println("line : " + line);
			String[] lineElements = line.split(";");
			for(int x=0; x<lineElements.length;x++) {
				System.out.println("x : " + x);
				System.out.println("lineElements : " + lineElements[x]);
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
		this.table.setRandom(chanceOfLife);
	}
	
	public boolean isLoopingBorder() {
		//ODO-COMPLETE : complete method with proper return
		return loopingBorder;
	}
	
	public void toggleLoopingBorder() {
		//ODO-COMPLETE : complete method
		loopingBorder = !loopingBorder;
		if (enableLogs) {
			if (loopingBorder) {
				System.out.println("toggleLoopingBorder called, set loopingBorder to true");
			} else {
				System.out.println("toggleLoopingBorder called, set loopingBorder to false");
			}
		}
		
	}
	
	public void setLoopDelay(int delay) {
		//TODO-COMPLETE : complete method
		loopDelay = delay;
		if (enableLogs) {
			System.out.println("Loop delay set to " + delay);
		}
	}

	public void setDansity(double density) {
		randomDansitySlider = density;
		if (enableLogs) {
			System.out.println("Density set to " + density);
		}
	}
	
	public void toggleClickAction() {
		//TODO-COMPLETE : complete method
		clickActionFlag = !clickActionFlag;
		if (enableLogs) {
			if (clickActionFlag) {
				System.out.println("toggleClickAction called, set clickActionFlag to true");
			} else {
				System.out.println("toggleClickAction called, set clickActionFlag to false");
			}
		}
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
		//TODO-INPROGRESS : remove previous rule (=emptying lists)
		fieldSurviveValues = new ArrayList<Integer>();
		fieldBirthValues = new ArrayList<Integer>();
		
		String surviveLine = lines.get(0);
		String birthLine = lines.get(1);
		
		String[] surviveElements = surviveLine.split(";");
		for(int x=0; x<surviveElements.length;x++) {
			String elem = surviveElements[x];
			int value = Integer.parseInt(elem);
			//TODO-INPROGRESS : add value to possible survive values
			fieldSurviveValues.add(value);
			
		}

		String[] birthElements = birthLine.split(";");
		for(int x=0; x<birthElements.length;x++) {
			String elem = birthElements[x];
			int value = Integer.parseInt(elem);
			//TODO-INPROGRESS : add value to possible birth values
			fieldBirthValues.add(value);
		}
	}

	public void applyRule(){
		Table tempTable = new Table(this.height, this.width, this);
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				int resultCountNear = this.table.countNear(x, y);
				if (this.getCell(x,y)==1) {
					if (this.fieldSurviveValues.contains(resultCountNear)) {
						tempTable.getCell(x, y).setValue(1);
					} else {
						tempTable.getCell(x, y).setValue(0);
					}
				} 
				else if(this.getCell(x,y)==0) {
					if (this.fieldBirthValues.contains(resultCountNear)) {
						tempTable.getCell(x, y).setValue(1);
					} else {
						tempTable.getCell(x, y).setValue(0);
					}
				}
				//DEBUG:
				//System.out.println("applying rule to cell: "+x+", "+y + " | countnear = " + resultCountNear + " | new cell value = " + this.getCell(x, y));
				

			}
		}
		this.table = tempTable;
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
		// TODO-COMPLETE : initially return "sheep" or "cell"
		// depending on clickActionFlag
		if (clickActionFlag){
			return "cell";
		}
		else {
			return "sheep";
		}
	}

    
	
}
