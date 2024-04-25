package backend;

import java.util.ArrayList;

public class Table {
    private int height;
    private int width;
    private ArrayList<ArrayList<Cell>> table; 
    private Simulator simulator;

    //TODO-INPROGRESS : create constructor
    public Table(int height, int width, Simulator tempSimulator) {
        this.height = height;
        this.width = width;
        this.simulator = tempSimulator;

        //initialize the table
        int vertexCount = 3;
        table = new ArrayList<>(vertexCount);


        //fill the table will empty cells
        Cell emptyCell = new Cell(0,0);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.setCell(emptyCell,i,j);
            }
        }
    }

    public int getheight() {
        return this.height;
    }

    public int getwidth() {
        return this.width;
    }
    public boolean isLoopingBorder() {
        return simulator.isLoopingBorder();
    }

    //TODO-COMPLETE : create getCell
    public Cell getCell(int x,int y) {
        //return the Cell object of coordinates x, y
        return table.get(x).get(y);
    }
    //TODO-complete : set(Cell, x, y) set an object Cell to coordinate x, y
    public void setCell(Cell cell, int x, int y){
        this.table.get(x).set(y,cell);
    }
    //TODO-complete : count near (xy) -> return how many cells around this cell
    public int countNear(int x, int y){
        int cellCount =0;
        // if border is true 
        for (int i = x-1;i<=x+1;i++){
            for (int j = y-1;j<=y+1;y++){
                if (!(i == j)){
                    cellCount += this.getCell(i,j).getValue();
                }
            }
            
        }
        return cellCount;
        //if border is false

    }
    //TODO : set agent (x y agent) load an agent to coordinates x,y

    //TODO : set random (density) create a random table of determined density 


    //TODO : load(filepath) turn a loaded saveable file into a table
    //TODO : save(filename) turn the table into saveable file
}
