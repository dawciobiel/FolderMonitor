package model;

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
     *
     */
    private String fileName;

    /**
     *
     */
    private String fileExt;

    /**
     *
     */
    private Date fileCreationDate;

    /**
     *
     */
    private Date fileLastAccessDate;

    /**
     *
     */
    private Date fileModifiedTimeDate;

    /**
     *
     */
    private Path sourceFolder;

    /**
     *
     */
    private Path destinationFolder;

    /**
     *
     */
    private boolean parityOfDate;


    public AttributesHolder(String fileName,
                            String fileExt,
                            Date fileCreationDate,
                            Date fileLastAccessDate,
                            Date fileModifiedTimeDate,

                            Path sourceFolder,
                            Path destinationFolder) {
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.fileCreationDate = fileCreationDate;
        this.fileLastAccessDate = fileLastAccessDate;
        this.fileModifiedTimeDate = fileModifiedTimeDate;

        this.sourceFolder = sourceFolder;
        this.destinationFolder = destinationFolder;
        this.parityOfDate = isDateIsEven(fileCreationDate);
    }

    private String getExtensionByGuava(File file) {
        return FileUtils.getExtensionByGuava(file.getName());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("file name: ").append(fileName).append("\n");
        sb.append("extension: ").append(fileExt).append("\n");
        sb.append("parity: ").append(parityOfDate).append("\n");
        sb.append("creation date: ").append(fileCreationDate).append("\n");
        sb.append("modified time date: ").append(fileModifiedTimeDate).append("\n");
        sb.append("last access date: ").append(fileLastAccessDate).append("\n");
        sb.append("source folder: ").append(sourceFolder).append("\n");
        sb.append("destination folder: ").append(destinationFolder);
//        sb.append("\n");

        return sb.toString();

    }

    @Override
    public int compareTo(Object o) {
        AttributesHolder c = (AttributesHolder) o;

        if (
                (this.fileName.equals(c.getFileName())) &&
                (this.fileExt.equals(c.getFileExt())) &&
                (this.fileCreationDate.equals(c.getFileCreationDate())) &&
                (this.fileLastAccessDate.equals(c.getFileLastAccessDate())) &&
                (this.fileModifiedTimeDate.equals(c.getFileModifiedTimeDate())) &&

                (this.sourceFolder.equals(c.getSourceFolder())) &&
                (this.destinationFolder.equals(c.getDestinationFolder()))
                // && (this.parityOfDate == c.get)

        ) {
            return 0;
                        /*
                            private String fileName;
                            private String fileExt;
                            private Date fileCreationDate;
                            private Date fileLastAccessDate;
                            private Date fileModifiedTimeDate;
                            private Path sourceFolder;
                            private Path destinationFolder;
                            private boolean parityOfDate;
                         */
        } else {
            return -1;
        }
    }
}
