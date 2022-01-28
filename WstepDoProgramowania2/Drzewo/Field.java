package drzewa;



public class Field implements Comparable<Field> {
	private double value;
	private int pointer;
	public Field(double x, int y) {
		this.value = x;
		this.pointer = y;
	}
	public Field(Field f) {
		//System.out.println(value + " p " + pointer + " v " + f.value + " p " + f.pointer);
		this(f.value, f.pointer);
		//System.out.println(value + " " + pointer + " " + f.value + " " + f.pointer);
	}
	public void setValue(double x) {
		this.value = x;
	}
	public void setPointer(int x) {
		this.pointer = x;
	}
	public double getValue() {
		double z = value;
		return z;
	}
	public int getPointer() {
		int z = pointer;
		return z;
	}
	public int compareTo(final Field f) {
		final int compareValue = Double.compare(this.value, f.getValue());
		return compareValue == 0 ? Double.compare(this.pointer, f.getPointer()) : compareValue;
	}
}