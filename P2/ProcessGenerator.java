
public class ProcessGenerator {
		double probability;
	public ProcessGenerator(double probability) {
		this.probability = probability;
	}

	public Process getNewProcess(int currentTime, int maxProcessTime, int maxLevel) {
		Process newProcess;
		newProcess = new Process(currentTime,maxProcessTime,maxLevel);
		return newProcess;
	}

	public boolean query() {
		return false;
	}

}
