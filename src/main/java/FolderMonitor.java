import calendar.CalendarUtils;
import config.ConfigUtils;
import files.FileOperationException;
import files.FileUtils;
import language.LanguageBundle;
import lombok.Data;
import model.AttributesHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

import static calendar.CalendarUtils.isDateIsEven;

/**
 *
 */
@Data
public class FolderMonitor {

    private static final Marker APP_MARKER = MarkerManager.getMarker("application");

    private static final String JAR = "jar";
    private static final String XML = "xml";

    /*
    ConcurrentLinkedQueue
    PriorityBlockingQueue
    LinkedBlockingQueue
     */
    /**
     * Thread safe queue implementation
     */
    public Queue<AttributesHolder> holders = new PriorityBlockingQueue<>();

    private static final Logger logger = LogManager.getLogger(FolderMonitor.class);

    public void doFolderMonitoring() {
        try {
            String home = ConfigUtils.readConfigValue("FOLDER_HOME");
            logger.info(LanguageBundle.getResource("WATCHING_DIRECTORY_FOR_CHANGES"), home);

            // STEP1: Create a watch service
            WatchService watchService = FileSystems.getDefault().newWatchService();

            // STEP2: Get the path of the directory which you want to monitor.
            Path directory = Path.of(home);

            // STEP3: Register the directory with the watch service
            WatchKey watchKey = directory.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);

            // STEP4: Poll for events
            while (true) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {

                    // STEP5: Get file name from even context
                    WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;

                    Path fileName = pathEvent.context();

                    // STEP6: Check type of event.
                    WatchEvent.Kind<?> kind = event.kind();

                    // STEP7: Perform necessary action with the event
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        logger.info(LanguageBundle.getResource("NEW_FILE_IS_CREATED"), fileName);
                        Path filename = ((WatchEvent<Path>) event).context();
                        Path fileNameWithSrcFolder = directory.resolve(filename);
                        Path absolutePathWithFileName = Path.of(filename.toAbsolutePath().getParent() + File.separator + fileNameWithSrcFolder);
                        AttributesHolder holder = createHolder(absolutePathWithFileName);

                        holders.add(holder);
                    }

                    if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        logger.info(LanguageBundle.getResource("FILE_HAS_BEEN_DELETED"), fileName);
                    }

                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        logger.info(LanguageBundle.getResource("FILE_HAS_BEEN_MODIFIED"), fileName);
                    }

                }

                // STEP8: Reset the watch key everytime for continuing to use it for further event polling
                boolean valid = watchKey.reset();
                if (!valid) {
                    break;
                }

            }
        } catch (IOException | FileOperationException e) {
            logger.error(LanguageBundle.getResource("EXCEPTION_MESSAGE"), e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create {@link model.AttributesHolder} based on full {@link java.nio.file.Path} of the file
     *
     * @param pathWithName {@link java.nio.file.Path} to the file
     * @return {@link model.AttributesHolder} object
     * @throws {@link files.FileOperationException} FileOperationException
     */
    private AttributesHolder createHolder(Path pathWithName) throws FileOperationException {
        String fileName = pathWithName.getFileName().toString();
        String fileExt = FileUtils.getExtensionByApacheCommonLib(fileName);
        FileTime creationDateZULU;
        FileTime lastAccessDateZULU;
        FileTime modifiedTimeDateZULU;
        Date creationDateCEST;
        Date lastAccessDateCEST;
        Date modifiedTimeDateCEST;

        try {
            creationDateZULU = FileUtils.getFileDates(pathWithName).creationTime();
            lastAccessDateZULU = FileUtils.getFileDates(pathWithName).lastAccessTime();
            modifiedTimeDateZULU = FileUtils.getFileDates(pathWithName).lastModifiedTime();
        } catch (IOException e) {
            logger.error(LanguageBundle.getResource("READING_FILE_ATTRIBUTES_NOT_POSSIBLE"), fileName);
            throw new FileOperationException(e.getMessage());
        }

        creationDateCEST = CalendarUtils.convertDateToUTC(creationDateZULU);
        lastAccessDateCEST = CalendarUtils.convertDateToUTC(lastAccessDateZULU);
        modifiedTimeDateCEST = CalendarUtils.convertDateToUTC(modifiedTimeDateZULU);
        Path destinationFolder = Path.of(
                selectTheCorrectDestinationFolder(
                        fileExt.toLowerCase(Locale.getDefault()), isDateIsEven(creationDateCEST))
        );

        AttributesHolder attributesHolder = new AttributesHolder(
                fileName,
                fileExt,
                creationDateCEST,
                lastAccessDateCEST,
                modifiedTimeDateCEST,
                pathWithName,
                destinationFolder);

        logger.debug(LanguageBundle.getResource("FILE_ATTRIBUTES"), attributesHolder);

        return attributesHolder;
    }

    /**
     * Based of config file this method will return proper destination folder based on file extension and creation date file parity
     *
     * @param fileExtension as {@link java.lang.String} object
     * @return Name of destination folder as {@link java.lang.String} object
     */
    private String selectTheCorrectDestinationFolder(String fileExtension, boolean parity) {
        String destination;

        switch (fileExtension) {
            case JAR -> {
                if (parity) {
                    destination = ConfigUtils.readConfigValue("FOLDER_DEV");
                } else {
                    destination = ConfigUtils.readConfigValue("FOLDER_TEST");
                }

            }
            case XML -> destination = ConfigUtils.readConfigValue("FOLDER_TEST");
            default -> destination = ConfigUtils.readConfigValue("FOLDER_HOME");
        }

        return destination;
    }

    /**
     * Proceed collection of holders.
     * Add result to json (by Gson)
     */
    public void proceedHolders() {

    }


    /**
     * The method creates folders where the application works
     */
    public void createFolders() {
        List<String> paths = Arrays.asList(
                ConfigUtils.readConfigValue("FOLDER_DEV"),
                ConfigUtils.readConfigValue("FOLDER_TEST"),
                ConfigUtils.readConfigValue("FOLDER_HOME")
        );

        Consumer<String> consumer = p -> {
            File file = new File(p);
            boolean isFolderCreated = file.mkdirs();
            if (!isFolderCreated) {

                boolean isFoldersAlreadyExist = file.exists();
                if (!isFoldersAlreadyExist) {
                    logger.error(LanguageBundle.getResource("ERROR_CANT_CREATE_FOLDER"), file.getName());
                    logger.error(APP_MARKER, LanguageBundle.getResource("APPLICATION_TERMINATED"));
                    System.exit(App.EXIT_STATUS_ERROR__BY_CREATING_FOLDER);
                } else {
                    logger.debug(LanguageBundle.getResource("FOLDER_ALREADY_EXIST"), file.getName());
                }
            } else {
                logger.debug(LanguageBundle.getResource("FOLDERS_CREATED"));
            }
        };
        paths.forEach(consumer);
    }


}
