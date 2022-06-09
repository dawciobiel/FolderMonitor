package model;

import config.ConfigUtils;
import files.FileUtils;
import lombok.Data;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;

import static calendar.CalendarUtils.isDateIsEven;

/**
 *
 */
@Data
public class AttributesHolder implements Comparable {

    /**
     * File name
     */
    private String name;

    /**
     * File extension
     */
    private String extension;

    /**
     * File creation date and time
     */
    private Date creationDate;

    /**
     * File last access date and time
     */
    private Date lastAccessDate;

    /**
     * File modified date and time
     */
    private Date modifiedTimeDate;

    /**
     * Folder where this file exist
     */
    private Path source;

    /**
     * Folder where this file should be moved
     */
    private Path destination;

    /**
     * Parity of date and time. Date and time of creation file
     */
    private boolean parityOfDate;


    /**
     * Create {@link AttributesHolder} object
     *
     * @param name
     * @param extension
     * @param creationDate
     * @param lastAccessDate
     * @param modifiedTimeDate
     * @param sourceFolderWithFileName
     * @param destinationFolderWithFileName
     */
    public AttributesHolder(String name, String extension,
                            Date creationDate, Date lastAccessDate, Date modifiedTimeDate,
                            Path sourceFolderWithFileName,
                            Path destinationFolderWithFileName) {
        this.name = name;
        this.extension = extension;

        this.creationDate = creationDate;
        this.lastAccessDate = lastAccessDate;
        this.modifiedTimeDate = modifiedTimeDate;

        this.parityOfDate = isDateIsEven(creationDate);

        this.source = sourceFolderWithFileName;
        this.destination = destinationFolderWithFileName;
    }

    /**
     * Create {@link AttributesHolder} object. Field {@link AttributesHolder#source} will
     * be set from config value <I>"FOLDER_HOME"</I>.
     *
     * @param name
     * @param extension
     * @param creationDate
     * @param lastAccessDate
     * @param modifiedTimeDate
     * @param destinationFolderWithFileName
     */
    public AttributesHolder(String name, String extension,
                            Date creationDate, Date lastAccessDate, Date modifiedTimeDate,
                            Path destinationFolderWithFileName) {

        this(name, extension,
                creationDate, lastAccessDate, modifiedTimeDate,
                Path.of(ConfigUtils.getResource("FOLDER_HOME")), destinationFolderWithFileName);
    }


    private String getExtensionByGuava(File file) {
        return FileUtils.getExtensionByGuava(file.getName());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("file name: ").append(name).append("\n");
        sb.append("extension: ").append(extension).append("\n");
        sb.append("parity: ").append(parityOfDate).append("\n");
        sb.append("creation date: ").append(creationDate).append("\n");
        sb.append("modified time date: ").append(modifiedTimeDate).append("\n");
        sb.append("last access date: ").append(lastAccessDate).append("\n");
        sb.append("source folder: ").append(source).append("\n");
        sb.append("destination: ").append(destination);

        return sb.toString();

    }

    @Override
    public int compareTo(Object o) {
        AttributesHolder c = (AttributesHolder) o;

        if (
                (this.name.equals(c.getName())) &&
                        (this.extension.equals(c.getExtension())) &&
                        (this.creationDate.equals(c.getCreationDate())) &&
                        (this.lastAccessDate.equals(c.getLastAccessDate())) &&
                        (this.modifiedTimeDate.equals(c.getModifiedTimeDate())) &&

                        (this.source.equals(c.getSource())) &&
                        (this.destination.equals(c.getDestination()) &&
                                (this.parityOfDate == c.isParityOfDate()))
        ) {
            return 0;
        } else {
            return -1;
        }
    }
}
