import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Backend (I think that's what this counts as) to WoLo worklogging program.
 * @author Will Mahurin
 * @version indev 26.11.12
 */
public class WorkLog
{
	// Fields.
	// File contruct of the log file.
	private CSVReaderWriter log;
	// Integer sum of the total time spent working TODO: Duration implementation and units?
	private long totalDuration; 
	private ArrayList<LogEntry> entries;
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
		log = new CSVReaderWriter(filepath);
		if(!log.getCsvFile().exists() ){
			log.write("Start time,End time,Duration");
		}
		readLogFile();
		calculateTotalDuration();
	}

	/**
	 * Add a work session with a start and end time, assuming the current day.
	 * Both timestamps follow format "h:m a", i.e. "6:30 pm".
	 * @param startTime The time that the session started.
	 * @param endTime The time that the session ended.
	 */
	public void addToday(String startTime, String endTime)
	{
		SimpleDateFormat dateParser = new SimpleDateFormat(DATEFORMAT);
		LocalDate today = LocalDate.now();
		startTime += ", " + today.getDayOfMonth() + " " + 
			today.getMonth().getDisplayName(TextStyle.SHORT, Locale.US) + " " + today.getYear();
		endTime += ", " + today.getDayOfMonth() + " " + 
			today.getMonth().getDisplayName(TextStyle.SHORT, Locale.US) + " " + today.getYear();
		try {
			ZonedDateTime startStamp = dateParser.parse(startTime).toInstant().atZone(ZoneId.systemDefault() );
			ZonedDateTime endStamp = dateParser.parse(endTime).toInstant().atZone(ZoneId.systemDefault() );

			if(endStamp.isBefore(startStamp) ) {
				startStamp = startStamp.minus(1, ChronoUnit.DAYS);

			}
			
			long duration = ChronoUnit.MINUTES.between(startStamp, endStamp);

			log.write(startStamp + "," + endStamp + "," + duration);

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
			ZonedDateTime startStamp = dateParser.parse(startDate).toInstant().atZone(ZoneId.systemDefault() );
			ZonedDateTime endStamp = dateParser.parse(endDate).toInstant().atZone(ZoneId.systemDefault() );
			long duration = ChronoUnit.MINUTES.between(startStamp, endStamp);
			log.write(startStamp + "," + endStamp + "," + duration);
		} catch(ParseException e) {
			System.out.printf("Couldn't parse either startTime \"%s\" or endTime \"%s\".%n",
					startDate, endDate);
			System.out.printf("Date format is \"%s\".%n", DATEFORMAT);
			e.printStackTrace();
			return;
		}

	}

	/**
	 * Read the log file and store all its data in the entries field as LogEntry objects.
	 */
	private void readLogFile()
	{
		String[] logLines = log.readAllLines();
		entries = new ArrayList<>();
		for(int i = 1; i < logLines.length; i++){
			entries.add(new LogEntry(logLines[i]) );
		}

	}

	/**
	 * Calculate the total duration based on the entries field.
	 */
	private void calculateTotalDuration()
	{
		totalDuration = 0;
		for(LogEntry entry : entries){
			totalDuration += entry.getDuration();
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

	public long getTotalDuration()
	{
		return totalDuration;
	}


}


