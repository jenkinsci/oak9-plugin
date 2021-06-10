package io.jenkins.plugins.oak9.utils;

import hudson.FilePath;

import java.io.*;
import java.nio.file.Path;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileArchiver {

    private final Collection<File> filesList;
    private final String outputFileName;
    private final FilePath basePath;

    public FileArchiver(FilePath basePath, Collection<File> filesList, String outputFileName) {
        this.filesList = filesList;
        this.basePath = basePath;
        this.outputFileName = outputFileName;
    }

    public void zipFiles() throws IOException {
        FileOutputStream fos = new FileOutputStream(this.basePath + File.separator + this.outputFileName);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (File file : this.filesList) {
            //File fileToZip = new File(this.basePath + File.separator +  file.getName());
            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(file.toString());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
    }

}
