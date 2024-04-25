package backend;

import java.util.ArrayList;

public class Table {
    private int height;
    private int width;
    private ArrayList<ArrayList<Cell>> table; 
    private Simulator simulator;


    //TODO-INPROGRESS : create constructor
    public Table(int height, int width) {
        this.height = height;
        this.width = width;
        this.simulator = simulator;


        //initialize the table
        int vertexCount = 3;
        table = new ArrayList<>(vertexCount);
    }

    public int getheight() {
        return this.height;
    }

    public int getwidth() {
        return this.width;
    }

    //TODO-COMPLETE : create getCell
    public Cell getCell(int x,int y) {
        //return the Cell object of coordinates x, y
        return table.get(x).get(y);
    }
    //TODO-INPROGRESS : set(Cell, x, y) set an object Cell to coordinate x, y
    public void setCell(Cell cell, int x, int y) {
        table.get(x).set(y, cell);
    }

    public boolean isLoopingBorder() {
        return simulator.isLoopingBorder();
    }


    //TODO-INPROGRESS : count around (xy) -> return how many  around this cell
    //2 modes needed : 1 with borders and 1 without
    public int countNear(int x, int y) {
        // Count the number of living cells around the specified cell
        int count = 0;
        return count;
    }







    
    //TODO : set agent (x y agent) load an agent to coordinates x,y

    //TODO : set random (density) create a random table of determined density 


    //TODO : load(filepath) turn a loaded saveable file into a table
    //TODO : save(filename) turn the table into saveable file
}
