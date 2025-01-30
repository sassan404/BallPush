package Game;

import Learning.Action;
import Learning.State;
import Learning.Type;
import Movement.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private static final Logger logger = LogManager.getLogger(Game.class);


	private final Ball defender;
	private final Ball attacker;
	private final Vector plane;
	private final List<StateActionTuple> defenderHistory = new ArrayList<>();
	private final List<StateActionTuple> attackerHistory = new ArrayList<>();

	public Game(Ball defender, Ball attacker) {
		this.plane = new Vector(100, 100);
		this.defender = defender;
		this.attacker = attacker;
	}

	public Game(Ball defender, Ball attacker, Vector plane) {
		this.plane = plane;
		this.defender = defender;
		this.attacker = attacker;
	}

	public Game(Vector plane) {
		this.plane = plane;
		this.defender = new Ball(0, plane.getY() / 4);
		this.attacker = new Ball(0, -plane.getY() / 4);
	}

	public Game() {
		this.plane = new Vector(100, 100);
		this.defender = new Ball(0, 25);
		this.attacker = new Ball(0, -25);
	}

	public Ball getDefender() {
		return defender;
	}

	public Ball getAttacker() {
		return attacker;
	}

	public List<StateActionTuple> getDefenderHistory() {
		return defenderHistory;
	}

	public List<StateActionTuple> getAttackerHistory() {
		return attackerHistory;
	}

	// Defender is out of bound in all directions
	public boolean isDefenderOutOfBounds() {
		return defender.getPosition().getX() > plane.getX() / 2 ||
				defender.getPosition().getY() > plane.getY() / 2 ||
				defender.getPosition().getX() < -plane.getX() / 2 ||
				defender.getPosition().getY() < -plane.getY() / 2;
	}

	// Attacker is out of bounds in 3 directions, 4th direction is the victory
	public boolean isAttackerOutOfBounds() {
		return attacker.getPosition().getX() > plane.getX() / 2 ||
				attacker.getPosition().getX() < -plane.getX() / 2 ||
				attacker.getPosition().getY() < -plane.getY() / 2;
	}

	public boolean isVictoryForDefender() {
		return defender.getPosition() == attacker.getPosition() || isAttackerOutOfBounds();
	}

	public boolean isVictoryForAttacker() {
		return attacker.getPosition().getY() > plane.getY() / 2 || isDefenderOutOfBounds();
	}

	public void moveBall(Ball ball, Action direction) {
		logger.info(direction);
		Vector force;
		switch (direction) {
			case UP -> {
				force = new Vector(0, 2);
				break;
			}
			case DOWN -> {
				force = new Vector(0, -2);
				break;
			}
			case RIGHT -> {
				force = new Vector(2, 0);
				break;
			}
			case LEFT -> {
				force = new Vector(-2, 0);
				break;
			}
			case null, default -> {
				force = new Vector();
				break;
			}
		}
		ball.move(force);
	}

	public State getAttackerState() {
		return new State(Type.ATTACKER, attacker.getPosition(), defender.getPosition(), attacker.getSpeed(), defender.getSpeed());
	}

	public State getDefenderState() {
		return new State(Type.DEFENDER, defender.getPosition(), attacker.getPosition(), defender.getSpeed(), attacker.getSpeed());

	}

	public void moveAttacker(Action direction) {
		logger.info("Attacker");
		State currentState = getAttackerState();
		attackerHistory.add(new StateActionTuple(currentState, direction));
		moveBall(attacker, direction);

	}

	public void moveDefender(Action direction) {
		logger.info("Defender");
		State currentState = getDefenderState();
		defenderHistory.add(new StateActionTuple(currentState, direction));
		moveBall(defender, direction);
	}

}
