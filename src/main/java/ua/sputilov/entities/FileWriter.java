package ua.sputilov.entities;

import java.io.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * The {@code FileWriter} class's object writing the information from DB's ResultSet object to txt file.
 * Writing to file is implemented through the {@code FileWriter} class,
 * and the {@code BufferedWriter} class and its {@code write()} method.
 *
 * @author  Sergey Putilov
 * @see     java.io.BufferedWriter
 * @see     java.io.FileWriter
 */
public class FileWriter {

    private final Logger LOG = Logger.getLogger(FileWriter.class.getName());
    public final String LINE_SEPARATOR = System.getProperty("line.separator");

    private BufferedWriter bufferedWriter;
    private File file;

    /**
     * The constructor of {@code FileWriter} class performs the following actions:
     * check the file from constructor's argument
     * if file is exists then clear its, else create a new file.
     * The file clearing is implemented through the {@code clearFile} method.
     *
     * @param fullPathToFile - the full path to file.
     */
    public FileWriter(String fullPathToFile) {
        file = new File(fullPathToFile);
        if (file.exists()) {
            LOG.info("The file is exists, clearing..." + LINE_SEPARATOR);
            clearFile();
            LOG.info("Clearing done." + LINE_SEPARATOR);
        } else {
            try {
                file.createNewFile();
                LOG.info("The file created successful." + LINE_SEPARATOR);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Recordings the information received from DB's tables to "txt" file.
     *
     * @param dbRecords - the records with information from DB.
     */
    public void writeToFile (List<String> dbRecords) {

        try {
            bufferedWriter = new BufferedWriter(new java.io.FileWriter(file, true));
            LOG.info("Writing to file records received from DB's tables..." + LINE_SEPARATOR);
            for (String dbRecord : dbRecords) {
                bufferedWriter.write(dbRecord);
                bufferedWriter.newLine();
            }
            LOG.info("Writing done." + LINE_SEPARATOR);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Clears the file.
     */
    private void clearFile () {

        try {
            bufferedWriter = new BufferedWriter(new java.io.FileWriter(file));
            bufferedWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
