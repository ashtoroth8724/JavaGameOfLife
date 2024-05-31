
package backend;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//import for json
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	private int clickActionFlag;
	private int loopDelay = 150;
	
	//TODO-COMPLETE : add missing attribute(s)
	private double randomDansitySlider = 0.5;
	private int width;
	private int height;
	private boolean enableLogs;
	private Table table;
	private int lowestCellValue = 0;
	private int highestCellValue = 0;
	private int ruleArrayListLength = 0;

	//Rules Arraylists
	private ArrayList<Rule> ruleArrayList = new ArrayList<Rule>();
	private ArrayList<ArrayList<Integer>> colorArrayList = new ArrayList<ArrayList<Integer>>();
	

	public Simulator(MyInterface mjfParam) {
		mjf = mjfParam;
		stopFlag=false;
		pauseFlag=false;
		loopingBorder=false;
		clickActionFlag=1; //1 for cell, 2 for sheep, 3 for wolf

		agents = new ArrayList<Agent>();
		fieldBirthValues = new ArrayList<Integer>();
		fieldSurviveValues = new ArrayList<Integer>();

		//TODO-COMPLETE: add missing attribute initialization
		//might want to changes those values later
		this.width=COL_NUM;
		this.height=LINE_NUM;
		enableLogs = true; // for debugging purposes
		table = new Table(height, width, this);

		
		
		//Default rule : Survive always, birth never
		//loadRule("ressources/Rule/conwayRule.json");
		loadRule("ressources/Rule/conwayRule.json");
		
	}

	public int getWidth() {
		//TODO-COMPLETE : replace with proper return
		return this.width;
	}

	public int getHeight() {
		//TODO-COMPLETE : replace with proper return
		return this.height;
	}

	public ArrayList<ArrayList<Integer>> getColorArrayList(){
		return colorArrayList;
	}
	//method to get the list of agents
	public ArrayList<Agent> getArrayAgent(){
		return agents;
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
		/*for(Agent agent : agents) {
			ArrayList<Agent> neighbors = 
					this.getNeighboringAnimals(agent.getX(), agent.getY(), ANIMAL_AREA_RADIUS);
			if(!agent.liveTurn(neighbors,this)) {
				agents.remove(agent);
			}
		}*/

		for(int i=0; i<agents.size(); i++){
			ArrayList<Agent> neighbors = this.getNeighboringAnimals(agents.get(i).getX(), agents.get(i).getY(), ANIMAL_AREA_RADIUS);
			if(!agents.get(i).liveTurn(neighbors,this)) {
				agents.remove(i);
			}
		}
		//then evolution of the field
		//TODO-COMPLETE : apply game rule to all cells of the field
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
		//ruleArrayList
		//find the max and min values according to rule to cycle through it
		if (ruleArrayListLength == 0 && highestCellValue == 0){

			int ruleArrayListLength = ruleArrayList.size();

			for (int i = 0; i < ruleArrayListLength; i++) {
				if (ruleArrayList.get(i).getValue() > highestCellValue) {
					highestCellValue = ruleArrayList.get(i).getValue();
				}
			}
			int lowestCellValue = 0;

			for (int i = 0; i < ruleArrayListLength; i++) {
				if (ruleArrayList.get(i).getValue() < lowestCellValue) {
					lowestCellValue = ruleArrayList.get(i).getValue();
				}
			}

			System.out.println("ruleArrayListLength: " + ruleArrayListLength);
			System.out.println("highestCellValue: " + highestCellValue);
			System.out.println("lowestCellValue: " + lowestCellValue);
		}
		
		int currentCellValue = getCell(x, y);
		//TODO-COMPLETE : find highest value in ruleArrayList
		int newCellValue = 0;
		if(clickActionFlag == 1) { //cell, cycke through the states of cells according to the rules
			if (currentCellValue != highestCellValue) {
				newCellValue = currentCellValue +1;
			} 
			else {
				newCellValue = lowestCellValue;
			}
			this.setCell(x, y, newCellValue);
		}
		else if(clickActionFlag == 2) { //sheep
			int i=0;
			Agent agent = new Sheep(x,y);
			
			//if there are no agents, skip directly to adding one
			boolean removal =false;
			if (agents.size()>0 ){
				
				//if an agent is in this area we iterate in the arraylist agents in order to find which one it is 
				
				for(i=0;i<=agents.size()-1;i++){
					
					//if we proceed to find the agent on the coordinates of the click, we remove it
					
					if (agents.get(i).getX() == x && agents.get(i).getY() == y ){
						agents.remove(i);
						System.out.println("Corresponding agent found, proceeding with removal");
						removal = true;
						
					}
				}
				if(i==agents.size() && removal ==false){
					//if we find no corresponding agent after the for loop, we add one
					System.out.println("no agents to remove, proceeding with creation");
					setSheep(x, y);
					if (enableLogs) {
						System.out.println("clickAgent Called, Agent created at: " + x + "," + y + "");
					}
				}	
				
			}
			else{		
				setSheep(x,y);
				if (enableLogs) {
					System.out.println("clickAgent Called, Agent created at: " + x + "," + y + "");
				}
			}
		}
		else if(clickActionFlag == 3) { //Wolf
			int i=0;
			Agent wolf = new Wolf(x,y);
			
			//if there are no agents, skip directly to adding one
			boolean removal =false;
			if (agents.size()>0 ){
				
				//if an agent is in this area we iterate in the arraylist agents in order to find which one it is 
				
				for(i=0;i<=agents.size()-1;i++){
					
					//if we proceed to find the agent on the coordinates of the click, we remove it
					
					if (agents.get(i).getX() == x && agents.get(i).getY() == y ){
						agents.remove(i);
						System.out.println("Corresponding agent found, proceeding with removal");
						removal = true;
						
					}
				}
				if(i==agents.size() && removal ==false){
					//if we find no corresponding agent after the for loop, we add one
					System.out.println("no agents to remove, proceeding with creation");
					setWolf(x, y);
					if (enableLogs) {
						System.out.println("clickAgent Called, Agent created at: " + x + "," + y + "");
					}
				}	
				
			}
			else{		
				setWolf(x,y);
				if (enableLogs) {
					System.out.println("clickAgent Called, Agent created at: " + x + "," + y + "");
				}
			}
		}
	}

	//TODO-COMPLETE : set agent (x y agent) load an agent to coordinates x,y
	public void setSheep(int x,int y){

		Sheep sheep =  new Sheep(x,y);
		
		agents.add(sheep);
	}
	public void setWolf(int x,int y){

		Wolf wolf =  new Wolf(x,y);
		
		agents.add(wolf);
	}

	/**
	 * get cell value in simulated world
	 * @param x coordinate of cell
	 * @param y coordinate of cell
	 * @return value of cell
	 */
	public int getCell(int x, int y) {
		//TODO-COMPLETE :
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
		//TODO-COMPLETE : complete method
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
		ArrayList<String> saveState = new ArrayList<>();
	
		// Ensure height and width are properly initialized
		int height = getHeight(); // Replace getHeight() with your method to get the height
		int width = getWidth();   // Replace getWidth() with your method to get the width
	
		for (int y = 0; y < height; y++) {
			StringBuilder lineBuilder = new StringBuilder();
			for (int x = 0; x < width; x++) {
				lineBuilder.append(getCell(x, y));
				if (x < width - 1) {
					lineBuilder.append(";");
				}
			}
			saveState.add(lineBuilder.toString());
		}
		return saveState;
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
		if (clickActionFlag != 3) {
			clickActionFlag++;
		} else {
			clickActionFlag = 1;
		}
		if (enableLogs) {
			System.out.println("toggleClickAction called, set clickActionFlag to : " + clickActionFlag);
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


	@SuppressWarnings("unchecked")
	public void loadRule(String fileName) {
		System.out.println(fileName);
		//TODO-COMPLETE load json
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(fileName))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);

			//load all the list of cells states
			JSONArray cellList = (JSONArray) obj;
			ruleArrayList.clear();
			colorArrayList.clear();
			cellList.forEach( cell -> parseCellObject( (JSONObject) cell ) );
					
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//DEBUG
		//printRules(ruleArrayList);
		lowestCellValue = 0;
		highestCellValue = 0;
		ruleArrayListLength = 0;
	}

	@SuppressWarnings("unchecked")
	private void parseCellObject(JSONObject cell) {
		// Get cell object within list
		JSONObject cellObject = (JSONObject) cell.get("cell");
		
		// Get value
		int cellValue = ((Long) cellObject.get("value")).intValue();
		System.out.println("cell value rule loaded: " + cellValue);
	
		// Get color
		JSONArray colorValueJsonArray = (JSONArray) cellObject.get("color");
		ArrayList<Integer> rgbList = new ArrayList<>();
		colorValueJsonArray.forEach(value -> rgbList.add(((Long) value).intValue()));
	
		// Get Condition Count Near
		JSONArray countNearJsonArray = (JSONArray) cellObject.get("conditionCountNear");
		ArrayList<Integer> conditionCountNearList = new ArrayList<>();
		countNearJsonArray.forEach(value -> conditionCountNearList.add(((Long) value).intValue()));
	
		// Get Condition Highest Near
		JSONArray conditionHighestNearJsonArray = (JSONArray) cellObject.get("conditionHighestNear");
		ArrayList<Integer> conditionHighestNearList = new ArrayList<>();
		conditionHighestNearJsonArray.forEach(value -> conditionHighestNearList.add(((Long) value).intValue()));
	
		// Get ifValue
		int ifValue = ((Long) cellObject.get("ifValue")).intValue();
	
		// Get elseValue
		int elseValue = ((Long) cellObject.get("elseValue")).intValue();
	
		// Ensure the colorArrayList is large enough
		while (colorArrayList.size() <= cellValue) {
			colorArrayList.add(new ArrayList<>());
		}
		colorArrayList.set(cellValue, rgbList);
	
		// Ensure the ruleArrayList is large enough
		while (ruleArrayList.size() <= cellValue) {
			ruleArrayList.add(null);
		}
		ruleArrayList.set(cellValue, new Rule(cellValue, rgbList, conditionCountNearList, conditionHighestNearList, ifValue, elseValue));
	}

	public void applyRule(){
		Table tempTable = new Table(this.height, this.width, this);
    for(int x = 0; x < width; x++) {
        for(int y = 0; y < height; y++) {
            int valueCountNear = table.countNear(x, y);
            int valueHighestNear = table.highestNear(x, y);
            int currentValue = table.getCell(x, y).getValue();
            Rule currentRule = ruleArrayList.get(currentValue);
            
            if (currentRule.getConditionCountNear().isEmpty() && currentRule.getConditionHighestNear().isEmpty()) {
                // Both condition lists are empty, directly take if value
                tempTable.getCell(x, y).setValue(currentRule.getIfValue());
            } else if (!currentRule.getConditionCountNear().isEmpty() && currentRule.getConditionHighestNear().isEmpty()) {
                // Only countNear condition
                if (currentRule.getConditionCountNear().contains(valueCountNear)) {
                    tempTable.getCell(x, y).setValue(currentRule.getIfValue());
                } else {
                    tempTable.getCell(x, y).setValue(currentRule.getElseValue());
                }
            } else if (currentRule.getConditionCountNear().isEmpty() && !currentRule.getConditionHighestNear().isEmpty()) {
                // Only highestNear condition
                if (currentRule.getConditionHighestNear().contains(valueHighestNear)) {
                    tempTable.getCell(x, y).setValue(currentRule.getIfValue());
                } else {
                    tempTable.getCell(x, y).setValue(currentRule.getElseValue());
                }
            } else if (!currentRule.getConditionCountNear().isEmpty() && !currentRule.getConditionHighestNear().isEmpty()) {
                // Both conditions
                if (currentRule.getConditionHighestNear().contains(valueHighestNear)
                        && currentRule.getConditionCountNear().contains(valueCountNear)) {
                    tempTable.getCell(x, y).setValue(currentRule.getIfValue());
                } else {
                    tempTable.getCell(x, y).setValue(currentRule.getElseValue());
                }
            }

            // DEBUG Print:
            //System.out.println("Applying rule to cell: " + x + ", " + y +" | countNear = " + valueCountNear +" | highestNear = " + valueHighestNear +" | current cell value = " + currentValue +" | new cell value = " + tempTable.getCell(x, y).getValue());
        }
    }
    this.table = tempTable;
    //DEBUG Print the table
	//table.serialPrint();
	}
	
	
	public ArrayList<String> getAgentsSave() {
		ArrayList<String> agentSave = new ArrayList<String>();
		String listSheep = "";
		String listWolf = "";
		//iterate through the agents arraylist
		for(Agent agent : agents){
			if(agent instanceof Sheep) {
				if (listSheep != "") listSheep = listSheep + ";";
				listSheep = listSheep + agent.getX() + "," + agent.getY();
			}else if (agent instanceof Wolf) {
				if (listWolf != "") listWolf = listWolf + ";";
				listWolf = listWolf + agent.getX() + "," + agent.getY();
			}
		}
		agentSave.add(listSheep);
		System.out.println("listSheep: " + listSheep);
		agentSave.add(listWolf);
		System.out.println("listWolf: " + listWolf);
		return agentSave;
	}

	public void loadAgents(ArrayList<String> stringArray) {
		//TODO-COMPLETE : Same idea as other load methods, but for agent list
		// Load sheep agents
		String sheepLine = stringArray.get(0);
		//split each agent by ;
		String[] sheepCoordinates = sheepLine.split(";");
		for (String coordinate : sheepCoordinates) {
			//split each coordinate by ,
			String[] xy = coordinate.split(",");
			int x = Integer.parseInt(xy[0]);
			int y = Integer.parseInt(xy[1]);
			setSheep(x, y);
		}
		// Load wolf agents
		String wolfLine = stringArray.get(1);
		//split each agent by ;
		String[] wolfCoordinates = wolfLine.split(";");
		for (String coordinate : wolfCoordinates) {
			//split each coordinate by ,
			String[] xy = coordinate.split(",");
			int x = Integer.parseInt(xy[0]);
			int y = Integer.parseInt(xy[1]);
			setWolf(x, y);
		}
	}

	/**
	 * used by label in interface to show the active click action
	 * @return String representation of click action
	 */
	public String clickActionName() {
		if (clickActionFlag == 1){
			return "cell";
		}
		else if (clickActionFlag == 2){
			return "sheep";
		}
		else if (clickActionFlag == 3){
			return "wolf";
		}
		else {
			return "error";
		}
	}

	//debug print the list of rules
	public void printRules(ArrayList<Rule> ruleArrayList) {
		System.out.println("-----------------------------------");
		System.out.println("Rule list size: "+ruleArrayList.size());
		System.out.println("-----------------------------------");
        for (Rule rule : ruleArrayList) {
            System.out.println("Rule for value: " + rule.getValue());
            System.out.println("Color: " + rule.getColor());
            System.out.println("Condition Count Near: " + rule.getConditionCountNear());
            System.out.println("Condition Highest Near: " + rule.getConditionHighestNear());
            System.out.println("If Value: " + rule.getIfValue());
            System.out.println("Else Value: " + rule.getElseValue());
            System.out.println("-----------------------------------");
        }
	}
	//debug print the list of agents
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

}
