package Movement;

public class Position extends Vector {

	public Position() {
		super();
	}

	public Position(double x, double y) {
		super(x, y);
	}

	@Override
	public void print() {
		System.out.print("Position: ");
		super.print();
	}


	public void update(Acceleration acc, Speed speed) {
		this.updateX(acc.getX(), speed.getX());
		this.updateY(acc.getY(), speed.getY());
	}

	public void updateX(double accX, double speedX) {
		this.setX(0.5 * accX + speedX + this.getX());
	}


	public void updateY(double accY, double speedY) {
		this.setX(0.5 * accY + speedY + this.getX());
	}
}
