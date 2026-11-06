public class WoLo {
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("""
					Usage: WoLo <command> [<args>]""");
			return;
		}
		switch(args[0]) {
			default -> System.out.println(args[0] + " not recognized."); // This can go first!
			case "addDateStamps" -> addDateStamps(args);
			case "addToday" -> addToday(args);
			case "start" -> start(args);
			case "end" -> end(args);
		}
	}

	private static void addDateStamps(String[] args)
	{
		if(args.length != 3) {
			System.out.println("Usage: WoLo addDateStamps <Start Date> <End Date>");
			return;
		}
		WorkLog logger = new WorkLog();
		logger.addDateStamps(args[1], args[2]);
	}

	private static void addToday(String[] args)
	{
		if(args.length != 3) {
			System.out.println("Usage: WoLo addToday <Start Time> <End Time>");
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
}
