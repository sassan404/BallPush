package Game;

import Movement.Acceleration;
import Movement.Position;
import Movement.Speed;
import Movement.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ball {

	private static final Logger logger = LogManager.getLogger(Ball.class);

	private Position position;
	private Speed speed;
	private Acceleration acceleration;


	public Ball() {
		this.position = new Position();
		this.speed = new Speed();
		this.acceleration = new Acceleration();
	}

	public Ball(double x, double y) {
		this.position = new Position(x, y);
		this.speed = new Speed();
		this.acceleration = new Acceleration();
	}

	public Ball(double x, double y, double speedX, double speedY, double accX, double accY) {
		this.position = new Position(x, y);
		this.speed = new Speed(speedX, speedY);
		this.acceleration = new Acceleration(accX, accY);
	}

	public void move(Vector force) {
		move(force, 1);
	}

	public void move(Vector force, double frictionValue) {
		double fX = frictionValue * (this.speed.getX() > 0 ? -1 : this.speed.getX() < 0 ? 1 : Double.compare(0, force.getX()));
		double fY = frictionValue * (this.speed.getY() > 0 ? -1 : this.speed.getY() < 0 ? 1 : Double.compare(0, force.getY()));
		Vector friction = new Vector(fX, fY);
		this.acceleration.update(friction, force);
		this.position.update(this.acceleration, this.speed);
		this.speed.update(this.acceleration);
	}

	public void print() {
		System.out.print("Position: ");
		this.position.print();
		System.out.print(", Speed: ");
		this.speed.print();
		System.out.print(", Acceleration: ");
		this.acceleration.print();
		System.out.println();
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Speed getSpeed() {
		return speed;
	}

	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	public Acceleration getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Acceleration acceleration) {
		this.acceleration = acceleration;
	}
}