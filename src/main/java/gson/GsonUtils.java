package gson;

import com.google.gson.Gson;
import config.ConfigUtils;
import files.FileOperationException;
import language.LanguageBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The class enables data write operations to a JSON file using the Gson library
 */
final public class GsonUtils {

    private static final Logger logger = LogManager.getLogger(GsonUtils.class);

    private GsonUtils() {
    }

    /**
     * Create and/or write data to json file
     *
     * @param data Data object to be written
     * @throws FileOperationException if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
     */
    public void createJsonFile(Object data) throws FileOperationException {
        Gson gson = new Gson();

        try {
            gson.toJson(data, new FileWriter(ConfigUtils.getResource("OUTPUT_FILE")));
        } catch (IOException e) {
            logger.error(LanguageBundle.getResource("CANT_CREATE_OR_WRITE_JSON_FILE"), e.getMessage());
            throw new FileOperationException(e.getMessage());
        }

    }
}
