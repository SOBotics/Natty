package in.bhargavrao.stackoverflow.natty.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Created by bhargav.h on 19-Aug-17.
 */
public class FMSUtils {


    public static void createNewFile(String filename, String data) throws IOException {
        Files.write(Paths.get(filename), Arrays.asList(data), StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);
    }
}
