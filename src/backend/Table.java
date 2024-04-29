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

        //initialize the row arraylist
        table = new ArrayList<>(height);

        //fill the table will empty cells
        for (int i = 0; i < height; i++) {
            //initialize each element of row ArrayList with another column ArrayList:
            this.table.add(i, new ArrayList<Cell>());
            for (int j = 0; j < width; j++) {
                //initialize each cell for each column
                this.table.get(i).add(new Cell(0,1));
            }
        }

        //TESTS:
        //System.out.println(this.getCell(0, 0).getValue());
        //System.out.println(this.countNear(0, 0));
        //Cell aliveCell = new Cell(1, 0);
        //this.setCell(0, 0, aliveCell);
        //System.out.println(this.getCell(0, 0).getValue());
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
    public Cell getCell(int row,int column) {
        //return the Cell object of coordinates x, y
        return table.get(row).get(column);
    }
    //TODO-complete : set(Cell, x, y) set an object Cell to coordinate x, y
    public void setCell(int row, int column, Cell cell){
        this.table.get(row).set(column, cell);
    }

    //TODO-complete : count near (xy) -> return how many cells around this cell
    public int countNear(int row, int column){
        int cellCount =0;
        // if border is true 
        for (int i = row-1;i<=row+1;i++){
            for (int j = column-1;j<=column+1;column++){
                if (!(i == j)){
                    if (i<0 || i>=width || j<0 || j>=height){
                        //TODO what to do when out of bound (wall alive or dead/ looping border) (wall dead by default)
                    } else {
                        cellCount += this.getCell(i,j).getValue();
                    }
                }
            }
            
        }
        return cellCount;
        //if border is false

    }
    //TODO : set agent (x y agent) load an agent to coordinates x,y

    //TODO : set random (density) create a random table of determined density 
    public void setRandom(double density) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double random = Math.random();
                if (random < density) {
                    table.get(i).get(j).setValue(1);
                } else {
                    table.get(i).get(j).setValue(0);
                }
            }
        }
        System.out.println("Created a random field");
    }

    public void serialPrint(){
        for (int i = 0; i < height; i++) {
            System.out.print("\n");
            for (int j = 0; j < width; j++) {
                System.out.print(this.getCell(i, j).getValue() +" ");
            }
        }
    }

}
