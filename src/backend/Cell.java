package backend;

public class Cell {
    private int value;
	private int density;

    public Cell(int value, int density) {
		this.value = value;
		this.density = density;
	}

	public int getValue() {
		return value;
	}
	public int getDensity() {
		return density;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public void setDensity(int density){
		this.density = density;
	}
}
