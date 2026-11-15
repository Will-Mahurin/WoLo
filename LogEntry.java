import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Class for managing details of a log entry conveniently.
 * @author Will Mahurin
 * @version indev 26.11.12
 */
public class LogEntry 
{
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private long duration;

	public LogEntry(String logLine)
	{
		String[] logItems = logLine.split(",");
		if(logItems.length != 3){
			System.out.printf("Log line %s is invalid.%n", logLine);
		}
		startDate = ZonedDateTime.parse(logItems[0]);
		endDate = ZonedDateTime.parse(logItems[1]);
		duration = Long.valueOf(logItems[2]);
	}

	public LogEntry(ZonedDateTime startingTime, ZonedDateTime endingTime)
	{
		startDate = startingTime;
		endDate = endingTime;
		duration = ChronoUnit.MINUTES.between(startDate, endDate);
	}

	public long getDuration()
	{
		return duration;
	}
}
