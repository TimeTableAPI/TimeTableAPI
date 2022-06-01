package pt.iscte.asd.projectn3.group11.services.loaders;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * Loader Service.
 * @param <T>
 */
public abstract class LoaderService<T> {

    private static final String UPLOADED_FILES_LOCATION = "./";

    /**
     * Loads a T List from a file in the given path.
     *
     * @param path path to the classroom csv.
     * @return List of T
     */
    @NotNull
    public List<T> load(@NotNull final String path)
    {
        return load(new File(path));
    }
    /**
     * Loads a T List from a file in the given path.
     *
     * @param file File with T.
     * @return List of T
     */
    @NotNull
    public List<T> load(@NotNull final File file)
    {
        LinkedList<T> linkedList = new LinkedList<>();
        try (
                final Reader reader = new FileReader(file);
                final CSVReader csvReader = new CSVReader(reader)
        ) {
            extract(csvReader, linkedList);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return linkedList;
    }

    /**
     * Loads a T List from a file in the given path.
     *
     * @param multipartFile File with T.
     * @return List of T
     */
    @NotNull
    public List<T> load(@NotNull final MultipartFile multipartFile, final boolean toDisk) throws IOException
    {
        File file;
        if (toDisk) {
            file = new File(UPLOADED_FILES_LOCATION + multipartFile.getOriginalFilename());
        } else {
            Path temp = Files.createTempFile(multipartFile.getOriginalFilename(), ".csv");
            file = new File(temp.toUri());
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        }
        return load(file);
    }

    protected abstract void extract(@NotNull CSVReader csvReader, @NotNull List<T> list) throws IOException, CsvValidationException;
}