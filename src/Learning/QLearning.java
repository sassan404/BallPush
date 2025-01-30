package Learning;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class QLearning {

	HashMap<State, HashMap<Action, Double>> matrix = new HashMap<>();
	double explorationCoefficient = 0;
	Random rand = new Random();
	private String CSV_FILE_NAME = "matrix-iteration.csv";

	public QLearning(boolean incremental) {
		if (incremental) {
			File file = new File(CSV_FILE_NAME);
			if (file.exists()) {
				try {
					readMatrix();
				} catch (IOException e) {
					System.out.println("No old file, starting from a new file");
				}
			}
		}
	}

	public QLearning(String fileName) {
		CSV_FILE_NAME = fileName;
	}

	public QLearning() {
	}

	public void learn(State state, Action action, double reward) {
		if (!matrix.containsKey(state)) {
			// Add a new state with an empty action map
			matrix.put(state, new HashMap<>());
		}
		// Get the action map for the state
		HashMap<Action, Double> actionMap = matrix.get(state);
		// Update or set the value for the action
		actionMap.put(action, actionMap.getOrDefault(action, 0.0) + reward);
	}

	public void rewardAndLearn(Map<State, Action> pastActions, boolean victory) {
// todo
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


	public void readMatrix() throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_NAME))) {
			List<String> lines = br.lines().toList();
			lines.stream().skip(1).forEach(line -> {
				String[] lineArray = line.split(",");
				State state = new State(Arrays.copyOfRange(lineArray, 0, 9));
				HashMap<Action, Double> actionMap = new HashMap<>();
				// Update or set the value for the action
				actionMap.put(Action.UP, Double.valueOf(lineArray[9]));
				actionMap.put(Action.DOWN, Double.valueOf(lineArray[10]));
				actionMap.put(Action.RIGHT, Double.valueOf(lineArray[11]));
				actionMap.put(Action.LEFT, Double.valueOf(lineArray[12]));
				matrix.put(state, actionMap);
			});
		}
	}


}