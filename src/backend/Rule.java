package backend;

import java.util.ArrayList;

public class Rule {

    private int value;
    private ArrayList<Integer> color;
    private ArrayList<Integer> conditionCountNear;
    private ArrayList<Integer> conditionHighestNear;
    private int ifValue;
    private int elseValue;

    public Rule(int value , ArrayList<Integer> color, ArrayList<Integer> conditionCountNear, ArrayList<Integer> conditionHighestNear, int ifValue, int elseValue) {
        this.value = value;
        this.color = color;
        this.conditionCountNear = conditionCountNear;
        this.conditionHighestNear = conditionHighestNear;
        this.ifValue = ifValue;
        this.elseValue = elseValue;
    }

	public int getValue() {
		return value;
	}

    public ArrayList<Integer> getColor() {
        return color;
    }

    public void setColor(ArrayList<Integer> color) {
        this.color = color;
    }

    public ArrayList<Integer> getConditionCountNear() {
        return conditionCountNear;
    }

    public void setConditionCountNear(ArrayList<Integer> conditionCountNear) {
        this.conditionCountNear = conditionCountNear;
    }

    public ArrayList<Integer> getConditionHighestNear() {
        return conditionHighestNear;
    }

    public void setConditionHighestNear(ArrayList<Integer> conditionHighestNear) {
        this.conditionHighestNear = conditionHighestNear;
    }

    public int getIfValue() {
        return ifValue;
    }

    public void setIfValue(int ifValue) {
        this.ifValue = ifValue;
    }

    public int getElseValue() {
        return elseValue;
    }

    public void setElseValue(int elseValue) {
        this.elseValue = elseValue;
    }

}
