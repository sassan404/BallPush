package Movement;

import java.util.Objects;

public class Vector {
	private double x;
	private double y;

	public Vector() {
		this.setX(0);
		this.setY(0);
	}

	public Vector(Vector vector) {
		this.setX(vector.getX());
		this.setY(vector.getY());
	}

	public Vector(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void print() {
		System.out.print("X: " + this.x + "; Y: " + this.y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getX(), this.getY());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Vector other = (Vector) obj;
		if (Double.doubleToLongBits(this.getX()) != Double.doubleToLongBits(other.getX())) {
			return false;
		}
		return Double.doubleToLongBits(this.getY()) == Double.doubleToLongBits(other.getY());
	}
}
