package in.bhargavrao.stackoverflow.natty.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by bhargav.h on 20-Aug-17.
 */
public class PropertyService {
    private Properties prop;

    public PropertyService() {

        prop = new Properties();
        try
        {
            prop.load(new FileInputStream("./properties/login.properties"));
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }

    }

    public String getEmail(){
        return prop.getProperty("email");
    }

    public String getPassword(){
        return prop.getProperty("password");
    }

    public String getUsername(){
        return prop.getProperty("username");
    }

    public String getUserId(){
        return prop.getProperty("userid");
    }

    public String getApiKey(){
        return prop.getProperty("apikey");
    }

    public String getAutoFlagKey(){
        return prop.getProperty("autoflagkey");
    }

    public String getAutoFlagToken(){
        return prop.getProperty("autoflagtoken");
    }

    public String getLocation(){
        return prop.getProperty("location");
    }

    public String getSentinelKey(){
        return prop.getProperty("sentinelKey");
    }

    public String getSentinelApiKey(){
        return prop.getProperty("sentinelApiKey");
    }

    public String getFMSPath(){
        return prop.getProperty("fmsPath");
    }
    public String getFMSUrl(){
        return prop.getProperty("fmsUrl");
    }
}
