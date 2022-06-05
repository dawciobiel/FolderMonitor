import files.FileOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Application started.");
        logger.debug("Application started.");
        logger.error("Application started.");

        FolderMonitor fm = new FolderMonitor();
        try {
            fm.createFolders();
        } catch (FileOperationException e) {
            System.exit(1); // Can't create application folders
        }
        fm.doFolderMonitoring();
    }

}
