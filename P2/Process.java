import java.util.Random;

public class Process {
	int priorityLevel,timeRemain,arrivalTime,timeNotProcessed;
	public Process(int currentTime, int maxProcessTime, int maxLevel){
		this.arrivalTime = currentTime;
		Random rand = new Random();
		this.priorityLevel = rand.nextInt(maxLevel) + 1;
		this.timeRemain = rand.nextInt(maxProcessTime) + 1;

	}
	
	public int getTimeRemaining() {
		return timeRemain;
	}

	public int getPriority() {
		return priorityLevel;
	}

	public void reduceTimeRemaining() {
		timeRemain--;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public boolean finish() {
		if (timeRemain == 0){
			return true;
		} else {
			return false;
		}
	}
	public int compareTo(Process comp) {
		if (this.getPriority() > comp.getPriority()) {
			return 1;
		} else if (comp.getPriority() > this.getPriority()) {
			return -1;
		} else if (comp.getArrivalTime() < this.getArrivalTime()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public void resetTimeNotProcessed() {
		timeNotProcessed = 0;
	}
}
