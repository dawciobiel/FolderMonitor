package files;

import language.LanguageBundle;
import model.AttributesHolder;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

final public class FileUtils {

    private static final Logger logger = LogManager.getLogger(FileUtils.class);

    private FileUtils() {
    }

    /**
     * Returns a file attribute view of a given type
     *
     * @param uri {@link java.nio.file.Path} to the file
     * @return Attributes of the file as BasicFileAttributes {@link java.nio.file.attribute.BasicFileAttributes}
     */
    public static BasicFileAttributes getFileAttr(Path uri) throws FileOperationException {
        Path path = Paths.get(String.valueOf(uri));

        try {
            return java.nio.file.Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
        } catch (IOException e) {
            throw new FileOperationException(LanguageBundle.getResource("CANT_READ_FILE_ATTRIBUTES"));
        }
    }

    /**
     * If filename is empty or null, getExtension(String filename) will return the instance it was given. Otherwise, it returns extension of the filename.
     * <p>
     * Special Cases:
     * <p>
     * <p>
     * No extension – this method will return an empty String<br />
     * Only extension – this method will return the String after the dot, e.g. “gitignore”
     *
     * @param filename File name as {@link java.lang.String} object
     * @return Extension of the file
     */
    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename).filter(f -> f.contains(".")).map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    /**
     * Special Cases:
     * <p>
     * No extension – this method will return an empty string.<br />
     * Only extension – this method will return the String after the dot, e.g. “gitignore”
     *
     * @param fileName File name as {@link java.lang.String} object
     * @return Extension of the file
     */
    public static String getExtensionByApacheCommonLib(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }

    /**
     * Special Cases:
     * <p>
     * No extension – this method will return an empty String<br />
     * Only extension – this method will return the String after the dot, e.g. “gitignore”
     *
     * @param filename File name as {@link java.lang.String} object
     * @return Extension of the file
     */
    public static String getExtensionByGuava(String filename) {
        return com.google.common.io.Files.getFileExtension(filename);
    }

    /**
     * Return attributes of the file
     *
     * @param path {@link java.nio.file.Path} to the file
     * @return Attributes of the file as BasicFileAttributes {@link java.nio.file.attribute.BasicFileAttributes}
     * @throws IOException  if an I/O error occurs
     */
    public static BasicFileAttributes getFileDates(Path path) throws IOException {
        return Files.readAttributes(path, BasicFileAttributes.class);
    }

    /**
     * The method changes the location of the file from the source folder to the destination folder
     *
     * @param attributesHolder {@link AttributesHolder}
     * @return  true - if file was moved
     */
    public static boolean moveFile(AttributesHolder attributesHolder) {
        Path src = attributesHolder.getSource();
        Path dest = attributesHolder.getDestination();

        boolean fileMoved = true;
        try {
            Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            fileMoved = false;
            logger.error(LanguageBundle.getResource("CANT_MOVE_FILE"), src.getFileName());
            e.printStackTrace();
        }
        logger.info(LanguageBundle.getResource("FILE_MOVED"), src.getFileName());

        return fileMoved;
    }

}
