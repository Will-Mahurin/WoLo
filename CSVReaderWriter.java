import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSVReaderWriter
{
	private File csvFile;

	public CSVReaderWriter(String filepath)
	{
		csvFile = new File(filepath);
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

	/**
	 * Read all the lines from the CSV file and return them an an array of strings.
	 * @return An array of Strings holding all lines in the file.
	 */
	public String[] readAllLines()
	{
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(csvFile.getName() ) );
			return lines.toArray(new String[0] );
		} catch(IOException e) {
			System.out.printf("Couldn't read %s: %s%n", csvFile.getName(), e);
			e.printStackTrace();
			System.out.printf("Returning null.%n");
			return null;
		}
	}

	public File getCsvFile() {
		return csvFile;
	}
}
