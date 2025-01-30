package Movement;

public class Vector {
	private double x;
	private double y;

	public Vector() {
		this.x = 0;
		this.y = 0;
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void print() {
		System.out.print("X: " + this.x + "; Y: " + this.y);
	}

	public String toString() {
		return x + "," + y;
	}
}
