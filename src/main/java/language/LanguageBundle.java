package language;

import config.ConfigUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LanguageBundle {

    static Logger logger = LogManager.getRootLogger();


    public static String getResource(String message) {
        String value = "";

        String selectedLanguage = ConfigUtils.readConfigValue("LANGUAGE");
        StringBuilder properties = new StringBuilder("language_");
        properties.append(selectedLanguage);
        properties.append(".properties");

        try (InputStream input = LanguageBundle.class.getClassLoader().getResourceAsStream(properties.toString())) {
            if (input == null) {
                return LanguageBundle.getResource("Not possible to load language properties file");
            }

            Properties prop = new Properties();
            prop.load(input);

            value = prop.getProperty(message);

        } catch (IOException ex) {
            logger.error("Can't load config file");
            ex.printStackTrace();
        }

        return value != null ? value : "";
    }
}
