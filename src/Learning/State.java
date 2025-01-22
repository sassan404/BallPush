package Learning;

import Movement.Position;
import Movement.Speed;

public class State {
	private Type type;
	private Position selfPosition;
	private Position enemyPosition;
	private Speed selfSpeed;
	private Speed enemySpeed;

	public State(String[] data) {
		this.type = Type.valueOf(data[0]);
		this.selfPosition = new Position(Double.parseDouble(data[1]), Double.parseDouble(data[2]));
		this.enemyPosition = new Position(Double.parseDouble(data[3]), Double.parseDouble(data[4]));
		this.selfSpeed = new Speed(Double.parseDouble(data[5]), Double.parseDouble(data[6]));
		this.enemySpeed = new Speed(Double.parseDouble(data[7]), Double.parseDouble(data[8]));
	}

	public State(Type type, Position selfPosition, Position enemyPosition, Speed selfSpeed, Speed enemySpeed) {
		this.type = type;
		this.selfPosition = selfPosition;
		this.enemyPosition = enemyPosition;
		this.selfSpeed = selfSpeed;
		this.enemySpeed = enemySpeed;
	}

	public String toString() {
		return type.name() + "," + selfPosition.toString() + "," + enemyPosition.toString() + "," + selfSpeed + "," + enemySpeed;
	}

}
