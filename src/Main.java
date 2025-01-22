import Game.Game;
import Game.StateActionTuple;
import Learning.Action;
import Learning.QLearning;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {

		//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
		// to see how IntelliJ IDEA suggests fixing it.

		logger.info("This is an info message.");
		logger.error("This is an error message.");

		QLearning attackerAI = new QLearning(true, "attacker.csv");
		QLearning defenderAI = new QLearning(true, "defender.csv");

		for (int i = 0; i < 1; i++) {
			logger.info("Start game: {}", i);
			Game game = new Game();
			game.getAttacker().print();
			game.getDefender().print();


			int iteration = 0;
			while (!game.isVictoryForAttacker() && !game.isVictoryForDefender()) {
				iteration++;
				logger.info("Iteration: {}", iteration);
				Action attackerAction = attackerAI.pickAction(game.getAttackerState());
				Action defenderAction = defenderAI.pickAction(game.getDefenderState());
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
				attackerAI.learn(stateActionTuple.getState(), stateActionTuple.getAction(), rewardForAttacker);
				rewardForAttacker = 0.9 * rewardForAttacker;
			}

			while (!game.getDefenderHistory().isEmpty()) {
				StateActionTuple stateActionTuple = game.getDefenderHistory().getLast();
				game.getDefenderHistory().removeLast();
				defenderAI.learn(stateActionTuple.getState(), stateActionTuple.getAction(), rewardForDefender);
				rewardForDefender = 0.9 * rewardForDefender;
			}

		}

		try {
			attackerAI.writeMatrix();
			defenderAI.writeMatrix();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}