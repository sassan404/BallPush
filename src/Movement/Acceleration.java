package Movement;

public class Acceleration extends Vector {


	public Acceleration() {
		super();
	}

	public Acceleration(double x, double y) {
		super(x, y);
	}

	@Override
	public void print() {
		System.out.print("Acceleration: ");
		super.print();
	}

	public void update(Vector friction, Vector force) {
		this.updateX(friction.getX(), force.getX());
		this.updateY(friction.getY(), force.getY());
	}

	public void updateX(double frictionX, double forceX) {
		this.setX(frictionX + forceX);
	}


	public void updateY(double frictionY, double forceY) {
		this.setY(frictionY + forceY);
	}
}
