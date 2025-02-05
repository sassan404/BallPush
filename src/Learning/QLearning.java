package Learning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class QLearning {

	private static final Logger logger = LogManager.getLogger(QLearning.class);
	private final String CSV_FILE_NAME;
	private final double learningRate;
	private final double discountFactor;
	private final HashMap<State, HashMap<Action, Double>> matrix;
	private final double explorationCoefficient;
	Random rand = new Random();

	public QLearning(boolean incremental) {
		CSV_FILE_NAME = "matrix-iteration.csv";
		learningRate = 0.1;
		discountFactor = 0.9;
		explorationCoefficient = 0.4;
		matrix = new HashMap<>();
		initialiseMatrix(incremental);
	}

	public void initialiseMatrix(boolean incremental) {
		if (incremental) {
			File file = new File(CSV_FILE_NAME);
			if (file.exists()) {
				try {
					readMatrix();
				} catch (IOException e) {
					logger.warn("No old file, starting from a new file");
				}
			}
		}
	}

	public void writeMatrix() throws IOException {
		File csvOutputFile = new File(CSV_FILE_NAME);
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
			Stream<String> data = matrix.entrySet().stream()
					.map(this::convertToCSVString);
			String header = "Type,self x, self y,enemy x, enemy y, self speed x, self speed y, enemy speed x, enemy speed y, up, down, right, left";
			pw.println(header);
			data.forEach(pw::println);
		}
	}

	public String convertToCSVString(Map.Entry<State, HashMap<Action, Double>> data) {
		String state = data.getKey().toString();
		double upQ = data.getValue().getOrDefault(Action.UP, 0.0);
		double downQ = data.getValue().getOrDefault(Action.DOWN, 0.0);
		double rightQ = data.getValue().getOrDefault(Action.RIGHT, 0.0);
		double leftQ = data.getValue().getOrDefault(Action.LEFT, 0.0);
		return state + "," + upQ + "," + downQ + "," + rightQ + "," + leftQ;
	}

	public HashMap<State, HashMap<Action, Double>> getMatrix() {
		return matrix;
	}

	public void learn(State oldState, State newState, Action action, double reward) {
		if (!matrix.containsKey(oldState)) {
			// Add a new state with an empty action map
			matrix.put(oldState, new HashMap<>());
		}
		// Get the action map for the state
		HashMap<Action, Double> actionMap = matrix.get(oldState);
		double oldQValue = actionMap.getOrDefault(action, 0.0);

		double maxNextQValue = getMaxNextQValue(newState);

		double newQValue = (1 - learningRate) * oldQValue + learningRate * (reward + discountFactor * maxNextQValue);
		// Update or set the value for the action
		actionMap.put(action, newQValue);

	}

	public double getMaxNextQValue(State state) {
		HashMap<Action, Double> possibleActions = matrix.getOrDefault(state, new HashMap<>());
		Collection<Double> values = possibleActions.values();
		Optional<Double> maxNextQValueOpt = values.stream().max(Double::compare);
		return maxNextQValueOpt.orElse(0.0);
	}

	public Optional<Action> getBestKnownAction(State state) {
		HashMap<Action, Double> possibleActions = matrix.getOrDefault(state, new HashMap<>());
		Optional<Map.Entry<Action, Double>> entryWithMaxValue = possibleActions.entrySet().stream().max(Map.Entry.comparingByValue());
		return entryWithMaxValue.map(Map.Entry::getKey);
	}

	public Action pickRandomAction() {
		int rand_int = rand.nextInt(4);
		return Action.values()[rand_int];
	}

	public Action pickAction(State state) {
		double rand_double = rand.nextDouble();
		if (rand_double > explorationCoefficient) return this.getBestKnownAction(state).orElse(this.pickRandomAction());
		else return this.pickRandomAction();
	}

	public void readMatrix() throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_NAME))) {
			br.readLine(); // Skip the header
			String line = br.readLine();
			while (line != null) {
				String[] lineArray = line.split(",");
				State state = new State(Arrays.copyOfRange(lineArray, 0, 9));
				HashMap<Action, Double> actionMap = new HashMap<>();
				// Update or set the value for the action
				actionMap.put(Action.UP, Double.valueOf(lineArray[9]));
				actionMap.put(Action.DOWN, Double.valueOf(lineArray[10]));
				actionMap.put(Action.RIGHT, Double.valueOf(lineArray[11]));
				actionMap.put(Action.LEFT, Double.valueOf(lineArray[12]));
				matrix.put(state, actionMap);
				line = br.readLine();
			}
		}
	}

}