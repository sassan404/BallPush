import Game.Game;
import Learning.Action;
import Learning.QLearning;
import Learning.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {

		QLearning gameAI = new QLearning(true);

		for (int i = 0; i < 1; i++) {
			logger.info("Start game: {}", i);
			Game game = new Game();

			State oldAttackerState = new State(game.getAttackerState());
			State oldDefenderState = new State(game.getDefenderState());

			int iteration = 0;
			while (!game.isGameOver()) {
				iteration++;
				logger.trace("Round: {}", iteration);

				Action attackerAction = gameAI.pickAction(oldAttackerState);
				Action defenderAction = gameAI.pickAction(oldDefenderState);

				game.moveAttacker(attackerAction);
				game.moveDefender(defenderAction);

				State newAttackerState = game.getAttackerState();
				State newDefenderState = game.getDefenderState();

				double rewardForAttacker = game.getRewardForAttacker();
				double rewardForDefender = game.getRewardForDefender();

				gameAI.learn(oldAttackerState, newAttackerState, attackerAction, rewardForAttacker);
				gameAI.learn(oldDefenderState, newDefenderState, defenderAction, rewardForDefender);

				oldAttackerState = new State(game.getAttackerState());
				oldDefenderState = new State(game.getDefenderState());
			}
		}

		try {
			gameAI.writeMatrix();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}