package Game;

import Learning.Action;
import Learning.State;

public class StateActionTuple {

	private final State state;
	private final Action action;

	public StateActionTuple(State state, Action action) {
		this.state = state;
		this.action = action;
	}

	public State getState() {
		return state;
	}

	public Action getAction() {
		return action;
	}
}

