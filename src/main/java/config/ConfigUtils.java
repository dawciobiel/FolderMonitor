package config;

import language.LanguageBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

    static Logger logger = LogManager.getRootLogger();

    public static String readConfigValue(String param) {
        return ConfigUtils.getResource(param);
    }

    public static String getResource(String key) {
        String value;
        Properties prop = new Properties();

        try (InputStream inputStream = LanguageBundle.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                /* An input stream for reading the resource;
                null if:
                 - the resource could not be found,
                 - the resource is in a package that is not opened unconditionally,
                 - or access to the resource is denied by the security manager.
                 */
                return LanguageBundle.getResource("CANT_LOAD_CONFIG_FILE");
            }

            prop.load(inputStream);
        } catch (IOException ex) { //  if an error occurred when reading from the input stream.
            logger.error("Can't load from config file"); // Error message can't be stored in .properties file
            ex.printStackTrace();
        }

        value = prop.getProperty(key);

        return value != null ? value : "";
    }

}
