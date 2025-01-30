package Movement;

public class Speed extends Vector {

	public Speed() {
		super();
	}

	public Speed(double x, double y) {
		super(x, y);
	}


	@Override
	public void print() {
		System.out.print("Speed: ");
		super.print();
	}

	public void update(Acceleration acc) {
		this.updateX(acc.getX());
		this.updateY(acc.getY());
	}

	public void updateX(double accX) {
		this.setX(accX + this.getX());
	}


	public void updateY(double accY) {
		this.setY(accY + this.getY());
	}
}
