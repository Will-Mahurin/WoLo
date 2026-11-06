import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
// TODO: Swap from Dates to something else, their support is really weird. Maybe Calendar?

/**
 * Backend (I think that's what this counts as) to WoLo worklogging program.
 * @author Will Mahurin
 * @version indev 6.11.26
 */
public class WorkLog
{
	// Fields.
	// File contruct of the log file.
	private File logFile;
	// Integer sum of the total time spent working TODO: Duration implementation and units?
	private int totalDuration; 
	// Consistent reference formats for times and dates.
	public static final String timeStampFormat = "h:m a";
	public static final String dateStampFormat = "h:m a, d MMM yy";

	/**
	 * Check the default file if non is given.
	 */
	public WorkLog() 
	{
		this("./WorkLog.csv");
	}

	/**
	 * Constructor for a WorkLog object with a specific file.
	 */
	public WorkLog(String filepath)
	{
		// TODO: Learn to read CSV files.
		logFile = new File(filepath);
		if(!logFile.exists() ) {
			createLogCSV(logFile);
		}
	}

	/**
	 * Add a work session with a start and end time, assuming the current day.
	 * Both timestamps follow format "h:m a", i.e. "6:30 pm".
	 * @param startTime The time that the session started.
	 * @param endTime The time that the session ended.
	 */
	public void addToday(String startTime, String endTime)
	{
		SimpleDateFormat dateParser = new SimpleDateFormat(timeStampFormat);
		try {
			// TODO: These dates are on the epoch, set them to the current one.
			Date startStamp = dateParser.parse(startTime);
			Date endStamp = dateParser.parse(endTime);
			if(endStamp.before(startStamp) ) {
				System.out.println("I need to learn how to set the date back a day!");
				//TODO: Learn to set the date back a day.
			}
			long duration = endStamp.getTime() - startStamp.getTime();
			writeToLogCSV(logFile, startStamp + "," + endStamp + "," + duration);
		} catch(ParseException e) {
			System.out.printf("Couldn't parse either startTime \"%s\" or endTime \"%s\".%n", startTime, endTime);
			System.out.printf("Date format is \"%s\".", dateStampFormat);
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Add a work session to the log with a start and end time.
	 * Both timestamps follow format "h:m a, d MMM yy". i.e. "6:30 pm, 5 Nov 26".
	 * @param startTime The time that the work session started.
	 * @param endTime The time that the work session ended.
	 */
	public void addDateStamps(String startTime, String endTime)
	{
		SimpleDateFormat dateParser = new SimpleDateFormat(dateStampFormat);
		try {
			Date startStamp = dateParser.parse(startTime);
			Date endStamp = dateParser.parse(endTime);
			long duration = endStamp.getTime() - startStamp.getTime();
			writeToLogCSV(logFile, startStamp + "," + endStamp + "," + duration);
			
		} catch(ParseException e) {
			System.out.printf("Couldn't parse either startTime \"%s\" or endTime \"%s\".%n", startTime, endTime);
			System.out.printf("Time format is \"%s\".", timeStampFormat);
			e.printStackTrace();
			return;
		}

	}

	public void startTimer()
	{
		// TODO: Figure out how to log indefinite real time.
		// Probably timestamps.
	}

	public void endTimer()
	{
		// TODO: Figure out how to stop logging indefinite real time.
	}

	/**
	 * Internal method to create a log file.
	 * @param file File to be create created.
	 */
	private void createLogCSV(File file)
	{
		System.out.printf("File %s does not exist and will be created.%n", file.getName() );
		try(PrintWriter writer = new PrintWriter(new FileWriter(file) ) ){
			writer.println("Start time,End time,Duration");
		} catch(IOException e) {
			System.out.printf("Failed to write to file: \"%s\".", file.getName() );
			e.printStackTrace();
		}
	}

	/**
	 * Internal method to write to a CSV file.
	 * @param file File object representing the CSV file that stores the logs.
	 * @param words String of the value to be written to the file.
	 */
	private void writeToLogCSV(File file, String words)
	{
		if(!file.exists() ) {
			System.out.printf("File %s does not exist, create it first.%n", file.getName() );
			return;
		}
		if(!file.canWrite() ) {
			System.out.printf("File %s is not writable.%n", file.getName() );
			return;
		}
		try(PrintWriter writer = new PrintWriter(new FileWriter(file, true) ) ) { // Need this `true` to append.
			System.out.printf("This should be appending %s to %s.%n", words, file.getName() );
			writer.append(words + "\n");
		} catch(IOException e) {
			System.out.printf("Failed to write to file: \"%s\".", file.getName() );
		}
	}
}


