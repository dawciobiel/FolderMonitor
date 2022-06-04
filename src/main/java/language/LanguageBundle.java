package language;

import config.ConfigUtils;
import files.FileOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LanguageBundle {

    static Logger logger = LogManager.getRootLogger();


    public static String getResource(String message) throws FileOperationException {
        String value = "";

        String selectedLanguage = ConfigUtils.readConfigValue("LANGUAGE");
        StringBuilder properties = new StringBuilder("language_");
        properties.append(selectedLanguage);
        properties.append(".properties");

        try (InputStream input = LanguageBundle.class.getClassLoader().getResourceAsStream(properties.toString())) {
            if (input == null) {
                return LanguageBundle.getResource("ERROR_UNABLE_TO_LOAD_LANGUAGE_FILE");
            }

            Properties prop = new Properties();
            prop.load(input);

            value = prop.getProperty(message);

        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("{}", ex);
//            throw new FileOperationException(LanguageBundle.getResource("ERROR_UNABLE_TO_READ_LANGUAGE_FILE"));
        }

        return value != null ? value : "";
    }
}
