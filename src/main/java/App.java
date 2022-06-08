import language.LanguageBundle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Main and launching class for application <strong>FolderMonitor</strong>
 */
public class App {
    /**
     *
     */
    public static final int EXIT_STATUS_SUCCESS = 0;

    /**
     *
     */
    public static final int EXIT_STATUS_ERROR__BY_CREATING_FOLDER = 1;
    static Logger logger = LogManager.getLogger(App.class);

    /**
     *
     * @param args  Arguments
     */
    public static void main(String[] args) {
        Configurator.setRootLevel(Level.DEBUG);
        logger.info(LanguageBundle.getResource("APPLICATION_STARTED"));

        FolderMonitor fm = new FolderMonitor();
        fm.createFolders();
        fm.doFolderMonitoring(); // todo threaded it
        fm.proceedHolders(); // todo threaded it
        logger.info(LanguageBundle.getResource("APPLICATION_FINISHED"));
        System.exit(EXIT_STATUS_SUCCESS);
    }

}
