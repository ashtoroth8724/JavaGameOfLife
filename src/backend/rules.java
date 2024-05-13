package backend;

public class rules {

    private int value;
    private int[] color;
    private int[] conditionCountNear;
    private int[] conditionHighestNear;
    private int ifValue;
    private int elseValue;

    public rules(int value , int[] color, int[] conditionCountNear, int[] conditionHighestNear, int ifValue, int elseValue) {
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

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public int[] getConditionCountNear() {
        return conditionCountNear;
    }

    public void setConditionCountNear(int[] conditionCountNear) {
        this.conditionCountNear = conditionCountNear;
    }

    public int[] getConditionHighestNear() {
        return conditionHighestNear;
    }

    public void setConditionHighestNear(int[] conditionHighestNear) {
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
