package language;

import config.ConfigUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LanguageBundle {

    private static final Logger logger = LogManager.getLogger(LanguageBundle.class);


    public static String getResource(String key) {
        String value;

        String language = ConfigUtils.readConfigValue("LANGUAGE");
        String propFileName = "bundle_" + language + ".properties";
        Properties prop = new Properties();

        try (InputStream inputStream = LanguageBundle.class.getClassLoader().getResourceAsStream(propFileName)) {
            if (inputStream == null) {
                /* An input stream for reading the resource;
                null if:
                 - the resource could not be found,
                 - the resource is in a package that is not opened unconditionally,
                 - or access to the resource is denied by the security manager.
                 */
                logger.error("Not possible to load language file: {}", propFileName); // Error message can't be stored in .properties file
                return "Not possible to load language file: " + propFileName;
            }

            prop.load(inputStream);
        } catch (IOException ex) {
            logger.error("Not possible to load language properties file"); // Error message can't be stored in .properties file
            ex.printStackTrace();
        }

        value = prop.getProperty(key);

        return value != null ? value : "";
    }
}
