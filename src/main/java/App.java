import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class App {
    static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        Configurator.setRootLevel(Level.INFO); // fixme Remove it after sorting out log4j_2 configuration
        logger.info("Application started");

        FolderMonitor fm = new FolderMonitor();
        fm.createFolders();
        fm.doFolderMonitoring();
        logger.info("Application finished");
    }

}
