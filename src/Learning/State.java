package Learning;

import Movement.Position;
import Movement.Speed;

import java.util.Objects;

public class State {
	private final Type type;
	private final Position selfPosition;
	private final Position enemyPosition;
	private final Speed selfSpeed;
	private final Speed enemySpeed;

	public State(String[] data) {
		this.type = Type.valueOf(data[0]);
		this.selfPosition = new Position(Double.parseDouble(data[1]), Double.parseDouble(data[2]));
		this.enemyPosition = new Position(Double.parseDouble(data[3]), Double.parseDouble(data[4]));
		this.selfSpeed = new Speed(Double.parseDouble(data[5]), Double.parseDouble(data[6]));
		this.enemySpeed = new Speed(Double.parseDouble(data[7]), Double.parseDouble(data[8]));
	}

	// Copy constructor
	public State(State other) {
		// Copy all fields from the other state
		this.type = other.type;
		this.selfPosition = new Position(other.selfPosition);
		this.enemyPosition = new Position(other.enemyPosition);
		this.selfSpeed = new Speed(other.selfSpeed);
		this.enemySpeed = new Speed(other.enemySpeed);
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

	// Overriding the hashcode() function
	@Override
	public int hashCode() {
		return Objects.hash(type, selfPosition, enemyPosition, selfSpeed, enemySpeed);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		State state = (State) obj;
		return this.type == state.type &&
				this.selfPosition.equals(state.selfPosition) &&
				this.enemyPosition.equals(state.enemyPosition) &&
				this.selfSpeed.equals(state.selfSpeed) &&
				this.enemySpeed.equals(state.enemySpeed);
	}
}
