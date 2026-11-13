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
