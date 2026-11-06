import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CSVWriterTest {
	public static void main(String[] args) {
		try(PrintWriter writer = new PrintWriter(new FileWriter("data.csv") ) ) {
			writer.println("Test, Test again, testy test");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
