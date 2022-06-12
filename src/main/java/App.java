import language.LanguageBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * Main and launching class for application <strong>FolderMonitor</strong>
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    private static final Marker APP_MARKER = MarkerManager.getMarker("application");

    /**
     *
     */
    public static final int EXIT_STATUS_SUCCESS = 0;

    /**
     *
     */
    public static final int EXIT_STATUS_ERROR__BY_CREATING_FOLDER = 1;


    /**
     * @param args Arguments
     */
    public static void main(String[] args) {
        logger.info(APP_MARKER, LanguageBundle.getResource("APPLICATION_STARTED"));

        FolderMonitor fm = new FolderMonitor();
        fm.createFolders();

        var ProceedingHoldersThread = new Thread(fm::proceedHolders);
        ProceedingHoldersThread.start();

        fm.doFolderMonitoring();

        logger.info(APP_MARKER, LanguageBundle.getResource("APPLICATION_FINISHED"));
        System.exit(EXIT_STATUS_SUCCESS);
    }

}
