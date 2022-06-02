package language;

import files.FileOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LanguageBundle {

    public static String getResource(String message) throws FileOperationException {
        try (InputStream input = LanguageBundle.class.getClassLoader().getResourceAsStream("language_eu_US.properties")) { // todo Move it to config as value settings
            if (input == null) {
                return LanguageBundle.getResource("NOT_POSSIBLE_TO_LOAD_PROPERTIES_FILE");
            }

            Properties prop = new Properties();
            prop.load(input);

            String value = prop.getProperty(message);
            return value != null ? value : "";
        } catch (IOException ex) {
            throw new FileOperationException(LanguageBundle.getResource("ERROR_UNABLE_TO_FIND_CONFIG"));
        }
    }
}
