import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OpenCSVTest {

    private static final String SAMPLE_CSV_FILE_PATH = "./ADS - Caracterizacao das salas.csv";

    public static void main(String[] args) {
//https://www.callicoder.com/java-read-write-csv-file-opencsv/
        try(
            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
            CSVReader csvReader = new CSVReader(reader);
        ){
            String [] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null){
                for (String parameter:nextRecord) {
                    System.out.print(parameter+";");

                }
                    System.out.println();

            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}