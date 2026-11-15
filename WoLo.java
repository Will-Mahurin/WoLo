import java.util.Scanner;

public class WoLo {
	public static void main(String[] args) {
		if(args.length == 0) {
			commandLine();
			return;
		}
		runCommand(args);
	}

	private static void runCommand(String[] args)
	{
		switch(args[0]) {
			default -> System.out.println(args[0] + " not recognized."); // This can go first!
			case "addDateStamps" -> addDateStamps(args);
			case "addToday" -> addToday(args);
			case "start" -> start(args);
			case "end" -> end(args);
			case "worktime" -> printWorkTime();
		}
	}

	private static void commandLine()
	{
		//TODO: Read user's command and execute it, accounting for arguments in quotes.
		//TODO: Print welcome message explaining what to do.
		Scanner input = new Scanner(System.in);
		boolean exit = false;
		while(!exit) {
			String newCommand = input.nextLine();
			if(newCommand.toLowerCase().equals("exit") ){
				exit = true;
				continue;
			}
			System.out.println(newCommand);
		}
		input.close();
	}

	private static void addDateStamps(String[] args)
	{
		if(args.length != 3) {
			System.out.println("Usage: WoLo addDateStamps <Start Date> <End Date>");
			System.out.println("Dates formatted as \"" + WorkLog.DATEFORMAT + "\".");
			return;
		}
		WorkLog logger = new WorkLog();
		logger.addDateStamps(args[1], args[2]);
	}

	private static void addToday(String[] args)
	{
		if(args.length != 3) {
			System.out.println("Usage: WoLo addToday <Start Time> <End Time>");
			System.out.println("Time formatted as \"" + WorkLog.TIMEFORMAT + "\".");
			return;
		}
		WorkLog logger = new WorkLog();
		logger.addToday(args[1], args[2]);
	}

	private static void start(String[] args)
	{

	}

	private static void end(String[] args)
	{

	}

	private static void printWorkTime()
	{
		WorkLog logger = new WorkLog();
		long workTime = logger.getTotalDuration();
		long hours = workTime / 60;
		long days = hours / 24;
		if(days > 0) {
			System.out.printf("%d days, %d hours, and %d minutes (%d minutes).%n",
					days, hours % 24, workTime % 60, workTime);
		} else if(hours > 0) {
			System.out.printf("%d hours and %d minutes (%d minutes).%n",
					hours, workTime % 60, workTime);
		} else {
			System.out.printf("%d minutes.%n", workTime);
		}
	}
}
