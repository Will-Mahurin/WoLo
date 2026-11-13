import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CSVReaderWriter
{
	private File csvFile;

	public CSVReaderWriter(String filepath)
	{
		csvFile = new File(filepath);
		if(!csvFile.exists() ) {
			System.out.printf("File %s does not exists and will be created.%n", csvFile.getName() );
			createCSVFile();
		}
	}


	/**
	 * Internal method to create a log file.
	 */
	private void createCSVFile()
	{
		System.out.printf("File %s does not exist and will be created.%n", csvFile.getName() );
		try(PrintWriter writer = new PrintWriter(new FileWriter(csvFile) ) ){
			writer.println("Start time,End time,Duration");
		} catch(IOException e) {
			System.out.printf("Failed to write to file: \"%s\".%n", csvFile.getName() );
			e.printStackTrace();
		}
	}

	/**
	 * Write a string to the CSV file associated with this writer.
	 * @param words String of the value to be written to the csvFile.
	 */
	public void write(String words)
	{
		try(PrintWriter writer = new PrintWriter(new FileWriter(csvFile, true) ) ) { // Need this `true` to append.
			writer.append(words + "\n");
		} catch(IOException e) {
			System.out.printf("Failed to write to file: \"%s\".%n", csvFile.getName() );
		}
	}


	public File getCsvFile() {
		return csvFile;
	}
}
