package config;

import files.FileOperationException;
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

    public static String getResource(String message) {
        String value;
        Properties prop = new Properties();

        try (InputStream input = LanguageBundle.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                return LanguageBundle.getResource("ERROR_UNABLE_TO_LOAD_CONFIG_FILE");
            }

            prop.load(input);
        } catch (IOException ex) {
            logger.error("Can't load config file");
            ex.printStackTrace();
        }

        value = prop.getProperty(message);

        return value != null ? value : "";
    }

}
