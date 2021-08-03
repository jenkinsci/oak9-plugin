package io.jenkins.plugins.oak9.utils;

import hudson.FilePath;
import java.io.*;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileArchiver {

    /**
     * list of files to ship to oak9
     */
    private final Collection<File> filesList;

    /**
     * output zip file name
     */
    private final String outputFileName;

    /**
     * base path to Jenkins workspace, used to clean up file pathing inside zip
     */
    private final FilePath basePath;

    /**
     * Constructor
     *
     * @param basePath
     * @param filesList
     * @param outputFileName
     */
    public FileArchiver(FilePath basePath, Collection<File> filesList, String outputFileName) {
        this.filesList = filesList;
        this.basePath = basePath;
        this.outputFileName = outputFileName;
    }

    /**
     * Zip files without specifying a base path - may create overly large directory tree in zip
     *
     * @throws IOException thrown in the event that the zip file cannot be created
     */
    public void zipFiles() throws IOException {
        zipFiles("");
    }

    /**
     * Zip files to ship to oak9
     *
     * @param base_path the path from which to start scanning for IaC files
     * @throws IOException thrown in the event that zip file cannot be created
     */
    public void zipFiles(String base_path) throws IOException {
        IOException runError = null;
        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;

        fos = new FileOutputStream(this.basePath + File.separator + this.outputFileName);
        zipOut = new ZipOutputStream(fos);

        try {
            for (File file : this.filesList) {
                fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getAbsoluteFile().toString().replace(base_path, ""));
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
            }
        } catch (IOException e) {
            runError = e;
        } finally {
            if (fis != null) {
                fis.close();
            }
            zipOut.close();
            fos.close();
        }
        if (runError != null) {
            throw runError;
        }
    }

}
