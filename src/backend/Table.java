package backend;

import java.util.ArrayList;

public class Table {
    private int height;
    private int width;
    private ArrayList<ArrayList<Cell>> table; 

    //TODO-INPROGRESS : create constructor
    public Table(int height, int width) {
        this.height = height;
        this.width = width;

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
    //TODO : set(Cell, x, y) set an object Cell to coordinate x, y

    //TODO : count around (xy) -> return how many  around this cell

    //TODO : set agent (x y agent) load an agent to coordinates x,y

    //TODO : set random (density) create a random table of determined density 


    //TODO : load(filepath) turn a loaded saveable file into a table
    //TODO : save(filename) turn the table into saveable file
}
