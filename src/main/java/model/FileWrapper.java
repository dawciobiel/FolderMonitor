package model;

import files.FileOperationException;
import files.FileUtils;
import language.LanguageBundle;
import lombok.Data;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import static calendar.CalendarUtils.isDateIsEven;

@Data
public class FileWrapper {

    private String fileExt;
    private String fileName;

    private FileTime dateOfCreation;

    private boolean parityOfDate;

    public FileWrapper(File file) throws FileOperationException {
        Path path = Paths.get(file.getPath());
        BasicFileAttributes fileAttr = FileUtils.getFileAttr(path);

        FileTime creationTime ;
        if (fileAttr != null) {
            creationTime = fileAttr.creationTime();
        } else {
            throw new FileOperationException(LanguageBundle.getResource("READING_FILE_ATTRIBUTES_NOT_POSSIBLE") + file.getName());
        }
        String fileName = file.getName();
        String fileExt = getExtensionByGuava(file);
        new FileWrapper(fileExt, fileName, creationTime);
    }

    public FileWrapper(String fileExt, String fileName, FileTime dateOfCreation) {
        this.fileExt = fileExt;
        this.fileName = fileName;
        this.dateOfCreation = dateOfCreation;

        setParityOfDate(isDateIsEven(dateOfCreation));
    }

    private String getExtensionByGuava(File file) {
        return FileUtils.getExtensionByGuava(file.getName());
    }

}
