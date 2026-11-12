import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

// TODO: Swap from Dates to something else, their support is really weird. Maybe Calendar?

/**
 * Backend (I think that's what this counts as) to WoLo worklogging program.
 * @author Will Mahurin
 * @version indev 26.11.11
 */
public class WorkLog
{
	// Fields.
	// File contruct of the log file.
	private File logFile;
	// Integer sum of the total time spent working TODO: Duration implementation and units?
	private int totalDuration; 
	// Consistent reference formats for times and dates.
	/**
	 * Consistent format for input times. Follows "h:m a" e.g. "6:30 pm"
	 */
	public static final String TIMEFORMAT = "h:m a";
	/**
	 * Consistent format for input dates. Follows "h:m a, d MMM yyyy" e.g. "6:30 pm, 5 Nov 2026"
	 */
	public static final String DATEFORMAT = "h:m a, d MMM yyyy";

	/**
	 * Check the default file if not is given.
	 */
	public WorkLog() 
	{
		this("./log-file.csv");
	}

	/**
	 * Constructor for a WorkLog object with a specific file.
	 * @param filepath The path of the file to be used as a log. Can be relative or absolute.
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
		SimpleDateFormat dateParser = new SimpleDateFormat(TIMEFORMAT);
		LocalDate today = LocalDate.now();
		startTime += today.getDayOfMonth() + today.getMonth().getDisplayName(TextStyle.SHORT, Locale.US) + 
			today.getYear();
		try {
			// TODO: These dates are on the epoch, set them to the current one.
			Instant startStamp = dateParser.parse(startTime).toInstant();
			Instant endStamp = dateParser.parse(endTime).toInstant();
			if(endStamp.isBefore(startStamp) ) {
				System.out.println("I need to learn how to set the date back a day!");
				//TODO: Learn to set the date back a day.
			}
			long duration = ChronoUnit.MINUTES.between(startStamp, endStamp);
			writeToLogCSV(logFile, startStamp + "," + endStamp + "," + duration);
		} catch(ParseException e) {
			System.out.printf("Couldn't parse either startTime \"%s\" or endTime \"%s\".%n",
					startTime, endTime);
			System.out.printf("Time format is \"%s\".%n", TIMEFORMAT);
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Add a work session to the log with a start and end time.
	 * Both timestamps follow the format of the public DATEFORMAT constant.
	 * @param startDate The time that the work session started.
	 * @param endDate The time that the work session ended.
	 */
	public void addDateStamps(String startDate, String endDate)
	{
		SimpleDateFormat dateParser = new SimpleDateFormat(DATEFORMAT);
		try {
			Date startStamp = dateParser.parse(startDate);
			Date endStamp = dateParser.parse(endDate);
			long duration = endStamp.getTime() - startStamp.getTime();
			writeToLogCSV(logFile, startStamp + "," + endStamp + "," + duration);
			
		} catch(ParseException e) {
			System.out.printf("Couldn't parse either startTime \"%s\" or endTime \"%s\".%n",
					startDate, endDate);
			System.out.printf("Date format is \"%s\".%n", DATEFORMAT);
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
			System.out.printf("Failed to write to file: \"%s\".%n", file.getName() );
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
			System.out.printf("Failed to write to file: \"%s\".%n", file.getName() );
		}
	}
}


