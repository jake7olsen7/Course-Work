import java.util.Random;

public class ProcessGenerator
{
	private double probability;
	private Random rand;
	
	public ProcessGenerator(double value)
	{
		probability = value;
		rand = new Random();
	}

	public boolean query()
	{
		return (rand.nextDouble() <= probability);
	}

	public Process getNewProcess(int arrivalTime, int maxProcessTime, int maxLevel)
	{
		int timeRemaining = rand.nextInt(maxProcessTime)+1;
		int priority = rand.nextInt(maxLevel)+1;
		Process newProcess = new Process(arrivalTime, timeRemaining, priority);
		return newProcess;
	}
}
