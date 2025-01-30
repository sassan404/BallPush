import Game.Game;
import Game.StateActionTuple;
import Learning.Action;
import Learning.QLearning;
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
			game.getAttacker().print();
			game.getDefender().print();


			int iteration = 0;
			while (!game.isGameOver()) {
				iteration++;
				logger.info("Iteration: {}", iteration);
				Action attackerAction = gameAI.pickAction(game.getAttackerState());
				Action defenderAction = gameAI.pickAction(game.getDefenderState());
				game.moveAttacker(attackerAction);
				game.getAttacker().print();
				game.moveDefender(defenderAction);
				game.getDefender().print();
			}

			double rewardForAttacker, rewardForDefender;

			if (game.isVictoryForAttacker()) {
				rewardForAttacker = 10;
				rewardForDefender = -10;
				logger.info("Victory for attacker.");
			} else {
				rewardForAttacker = -10;
				rewardForDefender = 10;
				logger.info("Victory for defender");
			}

			if (game.isAttackerOutOfBounds() || game.isDefenderOutOfBounds()) {
				logger.info("Victory by falling out of bounds");
			}

			logger.info(rewardForAttacker);
			logger.info(rewardForDefender);

			while (!game.getAttackerHistory().isEmpty()) {
				StateActionTuple stateActionTuple = game.getAttackerHistory().getLast();
				game.getAttackerHistory().removeLast();
				gameAI.learn(stateActionTuple.getState(), stateActionTuple.getAction(), rewardForAttacker);
				rewardForAttacker = 0.9 * rewardForAttacker;
			}

			while (!game.getDefenderHistory().isEmpty()) {
				StateActionTuple stateActionTuple = game.getDefenderHistory().getLast();
				game.getDefenderHistory().removeLast();
				gameAI.learn(stateActionTuple.getState(), stateActionTuple.getAction(), rewardForDefender);
				rewardForDefender = 0.9 * rewardForDefender;
			}

		}

		try {
			gameAI.writeMatrix();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}