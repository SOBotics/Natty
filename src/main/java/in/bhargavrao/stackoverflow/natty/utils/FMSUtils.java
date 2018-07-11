package in.bhargavrao.stackoverflow.natty.utils;

import in.bhargavrao.stackoverflow.natty.services.PropertyService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Created by bhargav.h on 19-Aug-17.
 */
public class FMSUtils {


    public static String FMSFilePath(){
        PropertyService propertyService = new PropertyService();
        return propertyService.getFMSPath();
    }
    public static String FMSUrl(){
        PropertyService propertyService = new PropertyService();
        return propertyService.getFMSUrl();
    }
    public static String createNewFile(String filename, String data) throws IOException {
        Files.write(Paths.get(filename), Arrays.asList(data), StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);
        PropertyService propertyService = new PropertyService();
        return propertyService.getFMSPath();
    }
}
