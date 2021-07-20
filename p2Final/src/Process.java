import java.util.Random;

public class Process {
	int priorityLevel,timeRemain,arrivalTime,timeNotProcessed;

	public Process(int arrivalTime, int processTime, int priority){
		this.arrivalTime = arrivalTime;
		this.timeRemain = processTime;
		this.priorityLevel = priority;
		this.timeNotProcessed = 0;
	}
	
	public int getTimeRemaining() {
		return timeRemain;
	}

	public int getPriority() {
		return priorityLevel;
	}

	public int getTimeNotProcessed() {
		return timeNotProcessed;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void reduceTimeRemaining() {
		timeRemain--;
	}

	public void increasePriority() {
    	priorityLevel++;
    }

	public void increasTimeNotProcessed() {
    	timeNotProcessed++;
    }

	public void resetTimeNotProcessed() {
		timeNotProcessed = 0;
	}

	public boolean finish() {
		if (timeRemain == 0){
			return true;
		} else {
			return false;
		}
	}
	public int compareTo(Process comp) {
		if (this.getPriority() > comp.getPriority())
			return 1;
		if (comp.getPriority() > this.getPriority())
			return -1;
		if (this.getArrivalTime() < comp.getArrivalTime())
			return 1;
		if (comp.getArrivalTime() < this.getArrivalTime())
			return -1;
		return 0;
	}

}
