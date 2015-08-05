package ua.sputilov;

import ua.sputilov.entities.*;
import ua.sputilov.utils.PropertiesLoader;
import ua.sputilov.utils.FormattedResultSet;

import java.sql.ResultSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Main {

    // The variables to compose the full path to file.
    private static final String DIR_SEPARATOR = System.getProperty("file.separator");
    private static final String PATH_TO_FILE = System.getProperty("user.dir");
    private static final String FILE_NAME = "export.txt";
    // Names of properties files.
    private static final String DB_CONNECTION_PROPERTIES_FILE_NAME = "db_connection.properties";
    private static final String SMTP_SERVER_PROPERTIES_FILE_NAME = "smtp_server.properties";
    // Email's variables.
    private static final String MAIL_SUBJECT = "Result information according to the SQL query";
    private static final String MAIL_BODY = "The file with DB's information attached.";
    // SQL query.
    private static final String SQL_QUERY = "SELECT a.person_id, p.firstname, p.lastname, a.street, a.city, a.zip "
            + "FROM address a INNER JOIN person p ON a.person_id = p.id";
    // List of email addresses.
    private static List<String> emailAddresses;

    public static void main(String[] args) {
        // Create the userInterface for communicating with the user.
        UserInterface userInterface = new UserInterface();

        // Set up default DB connection properties from db_connection.properties file,
        // or set up entered by user properties from keyboard depending on the choice of the user.
        Properties dbProperties = userInterface.setupDbConnection(new PropertiesLoader(DB_CONNECTION_PROPERTIES_FILE_NAME));
        // Create the DBReader object, that reads from DB's tables records according the SQL query
        // and write the records to the ResultSet object.
        DBReader reader = new DBReader(dbProperties);
        ResultSet resultSet = reader.getDBInfo(SQL_QUERY);

        // Create the FormattedResultSet object and get the formatted list of DB records from ResultSet object.
        FormattedResultSet resultSetFormatter = new FormattedResultSet(resultSet);
        List<String> dbRecords = resultSetFormatter.getFormattedData();

        // Set up the default full path to the export txt file,
        // or set up entered by user the full path to the file from keyboard depending on the choice of the user.
        String fullPathToFile = userInterface.getFullPathToFile(PATH_TO_FILE + DIR_SEPARATOR + FILE_NAME);
        // Create the writer object and write to file received the DB records.
        FileWriter writer = new FileWriter(fullPathToFile);
        writer.writeToFile(dbRecords);

        // Set up default SMTP server properties from smtp_server.properties file,
        // or set up entered by user properties from keyboard depending on the choice of the user.
        Properties smtpProperties = userInterface.setupSmtpServer(new PropertiesLoader(SMTP_SERVER_PROPERTIES_FILE_NAME));
        MailSender sender = new MailSender(smtpProperties);

        emailAddresses = new LinkedList<>();
        emailAddresses.add("sputilov@ya.ru");

        // Use default list of emails it's emailAddresses list that you may see above,
        // or use entered by user emails from keyboard depending on the choice of the user.
        emailAddresses = userInterface.getEmails(emailAddresses);

        //Sending emails with file attached.
        sender.sendMessages(emailAddresses, MAIL_SUBJECT, MAIL_BODY, fullPathToFile);
    }
}
