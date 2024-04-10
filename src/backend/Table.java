package backend;

import java.util.ArrayList;

public class Table {
    int vertexCount = 3;
    ArrayList<ArrayList<Cell>> table = new ArrayList<>(vertexCount);

    //TODO : create constructor

    public int getCell(int x,int y) {
        return table.get(x).add(y);
    }

    //TODO : get(xy)
    //TODO : set(xy)
    //TODO : count around (xy) -> return how many  around this cell
    //TODO : step : apply game rules for 1 tick ()
    //TODO : set agent (xy)
    //TODO : set random (density)
    //TODO : load(filepath)
    //TODO : save(filename)
}
