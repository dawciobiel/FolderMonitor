import config.ConfigUtils;
import files.FileOperationException;
import language.LanguageBundle;
import lombok.Data;
import model.AttributesHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

@Data
public class FolderMonitor {

    private static final String JAR = "jar";
    private static final String XML = "xml";
    private static final int EXIT_STATUS_SUCCESS = 0;
    private static final int EXIT_STATUS_ERROR = 1;
    public Deque<AttributesHolder> holders;
    Logger logger = LogManager.getRootLogger();

    public void doFolderMonitoring() { // todo thread
        try {
            String home = ConfigUtils.readConfigValue("FOLDER_HOME");
            logger.info("Watching directory: {} for changes", home);

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
                        logger.info("A new file is created : {}", fileName);
                        AttributesHolder holder = createHolder(pathEvent.context().getFileSystem());
                        holders.add(holder);
                    }

                    if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        logger.info("A file has been deleted: {}", fileName);
                    }

                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        logger.info("A file has been modified: {}", fileName);
                    }

                }

                // STEP8: Reset the watch key everytime for continuing to use it for further event polling
                boolean valid = watchKey.reset();
                if (!valid) {
                    break;
                }

            }
        } catch (IOException | FileOperationException e) {
            logger.error("{}", e);
        }

        // todo wait for proceedHolders till finish

        System.exit(EXIT_STATUS_SUCCESS);
    }

    private AttributesHolder createHolder(FileSystem fileSystem) throws FileOperationException {
        for (FileStore fileStore : fileSystem.getFileStores()) {
            String attributeFileName = null;
            String attributeExtension = null;
            FileTime attributeCreationDate = null;

            try {
                // Reading file name, extension and creation date
                //Object attributeFileName = fileStore.getAttribute("name");
                attributeFileName = fileStore.name();
                attributeExtension = fileStore.getAttribute("extension").toString();
                attributeCreationDate = null; // fileStore.getAttribute("creation-date"); // todo Get creation date from the file. It is possible to get absolute path from this file and check it in another way (system)
                attributeCreationDate = FileTime.fromMillis(0); //todo
            } catch (IOException e) {
                logger.error(LanguageBundle.getResource("ERROR_READING_FILE_ATTRIBUTES_NOT_POSSIBLE") + ": {}", fileStore);
            }

            String attributeSourceFolder = ConfigUtils.readConfigValue("FOLDER_HOME");
            String attributeDestinationFolder = selectTheCorrectDestinationFolder(attributeExtension.toLowerCase(Locale.getDefault()));

            return new AttributesHolder(attributeFileName, attributeExtension, attributeCreationDate, attributeSourceFolder, attributeDestinationFolder);
        }
        return null;
    }

    private String selectTheCorrectDestinationFolder(String attributeExtension) {
        String destination;

        switch (attributeExtension) {
            case JAR -> destination = ConfigUtils.readConfigValue("FOLDER_DEV");
            case XML -> destination = ConfigUtils.readConfigValue("FOLDER_TEST");
            default -> destination = ConfigUtils.readConfigValue("FOLDER_HOME");
        }

        return destination;
    }

    public void proceedHolders() {
        /* todo
            thread
                proceed collection of holders
                    file operation on first holder from Holders
                    add to gson

         */
    }


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
                    String errorMessage = LanguageBundle.getResource("ERROR_CANT_CREATE_FOLDER" + file.getName());
                    logger.error(errorMessage);
                    logger.error("Application terminated");
                    System.exit(1);
                } else {
                    logger.debug("Folder {} exist", file.getName());
                }
            } else {
                logger.debug("Folders created");
            }
        };
        paths.forEach(consumer);
    }


}
