package files;

import language.LanguageBundle;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

public class FileUtils {

    /**
     * Returns a file attribute view of a given type
     *
     * @param uri Path to the file
     * @return File attributes
     */
    public static BasicFileAttributes getFileAttr(Path uri) throws FileOperationException {
        Path path = Paths.get(String.valueOf(uri));

        try {
            return java.nio.file.Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
        } catch (IOException e) {
            throw new FileOperationException(LanguageBundle.getResource("ERROR_CANT_READ_FILE_ATTRIBUTE"));
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
     * @param filename File name
     * @return  Extension of the file
     */
    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename).filter(f -> f.contains(".")).map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    /**
     * Special Cases:
     * <p>
     * No extension – this method will return an empty string.<br />
     * Only extension – this method will return the String after the dot, e.g. “gitignore”
     *
     * @param fileName  File name
     * @return   Extension of the file
     */
    public String getExtensionByApacheCommonLib(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }


    /**
     * Special Cases:
     * <p>
     * No extension – this method will return an empty String<br />
     * Only extension – this method will return the String after the dot, e.g. “gitignore”
     *
     * @param filename
     * @return  Extension of the file
     */
    public static String getExtensionByGuava(String filename) {
        return com.google.common.io.Files.getFileExtension(filename);
    }


}
