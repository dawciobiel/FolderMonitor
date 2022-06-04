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
public class AttributesHolder {

    private String fileExt;
    private String fileName;
    private FileTime dateOfCreation;
    private String sourceFolder;
    private String destinationFolder;
    private boolean parityOfDate;


    public AttributesHolder(String fileExt, String fileName, FileTime dateOfCreation, String sourceFolder, String destinationFolder) {
        this.fileExt = fileExt;
        this.fileName = fileName;
        this.dateOfCreation = dateOfCreation;
        this.sourceFolder = sourceFolder;
        this.destinationFolder = destinationFolder;
        setParityOfDate(isDateIsEven(dateOfCreation));
    }

    private String getExtensionByGuava(File file) {
        return FileUtils.getExtensionByGuava(file.getName());
    }

}
