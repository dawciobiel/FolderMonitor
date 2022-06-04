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
        String value = null;
        try {
            value = ConfigUtils.getResource(param);
        } catch (FileOperationException e) {
            e.printStackTrace();
            logger.error("{}", e);
            try {
                logger.info(LanguageBundle.getResource("ERROR_UNABLE_TO_LOAD_CONFIG_FILE"));
            } catch (FileOperationException ex) {
                ex.printStackTrace();
                logger.error("{}", ex);
            }
        }
        return value;
    }

    public static String getResource(String message) throws FileOperationException {
        String value = "";

        try (InputStream input = LanguageBundle.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                return LanguageBundle.getResource("ERROR_UNABLE_TO_LOAD_CONFIG_FILE");
            }

            Properties prop = new Properties();
            prop.load(input);

            value = prop.getProperty(message);

        } catch (IOException ex) {
            logger.error("{}", ex);
            ex.printStackTrace();
        }

        return value != null ? value : "";
    }


}
