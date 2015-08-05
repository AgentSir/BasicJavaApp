package ua.sputilov.entities;

import ua.sputilov.utils.PropertiesLoader;

import java.util.*;
import java.util.logging.Logger;

/**
 * The {@code UserInterface} class represents user interface for communication with the user.
 * Communication is implemented through the {@code Logger} class.
 * According the user answers the  {@code UserInterface} object and its methods
 * changing the db_connections.properties, the smtp_server.properties, the full path to file with
 * exported records from DB and the list of email addresses.
 *
 * @author  Sergey Putilov
 * @see java.util.logging.Logger
 */
public class UserInterface {

    public final Logger LOG = Logger.getLogger(UserInterface.class.getName());
    public final String LINE_SEPARATOR = System.getProperty("line.separator");

    private String userAnswer;
    private Scanner scanner = new Scanner(System.in);

    /**
     *  Return the Properties object.
     *
     * @param propertiesLoader
     * @return
     */
    public Properties setupSmtpServer(PropertiesLoader propertiesLoader) {

        Properties smtpProperties = propertiesLoader.getProperties();

        LOG.info("The default smtp properties is: " + LINE_SEPARATOR + "  "
                + "smtp.server = " + smtpProperties.getProperty("smtp.server") + LINE_SEPARATOR + "  "
                + "smtp.login = " + smtpProperties.getProperty("smtp.login") + LINE_SEPARATOR + "  "
                + "smtp.password = ******" + LINE_SEPARATOR
                + "If you want to change them, please enter \"yes\"." + LINE_SEPARATOR
                + "Press \"Enter\" to continue, using the default properties.");

        userAnswer = scanner.nextLine();

        if (userAnswer.equals("yes".toLowerCase())) {

            LOG.info("Please enter the smtp properties \"smtp.serve\", \"smtp.login\", \"smtp.password\" separated by \";\"");

            List <String> userDbProperties = new LinkedList<>(Arrays.asList(scanner.nextLine().split(";")));

            smtpProperties.setProperty("smtp.server", userDbProperties.get(0));
            smtpProperties.setProperty("smtp.login", userDbProperties.get(1));
            smtpProperties.setProperty("smtp.password", userDbProperties.get(2));

            return smtpProperties;
        }

        if (userAnswer.equals("")) {
            return smtpProperties;
        }
        return smtpProperties;
    }

    /**
     *  Return the Properties object.
     *
     * @param propertiesLoader
     * @return
     */
    public Properties setupDbConnection(PropertiesLoader propertiesLoader) {

        Properties dbProperties = propertiesLoader.getProperties();

        LOG.info("The default DB's connection properties is: " + LINE_SEPARATOR + "  "
                + "user = " + dbProperties.getProperty("user") + LINE_SEPARATOR + "  "
                + "password = ******" + LINE_SEPARATOR + "  "
                + "db.url = " + dbProperties.getProperty("db.url") + LINE_SEPARATOR + "  "
                + "db.driver = " + dbProperties.getProperty("db.driver") + LINE_SEPARATOR
                + "If you want to change them, please enter \"yes\"" + LINE_SEPARATOR
                + "Press \"Enter\" to continue, using the default properties.");

        userAnswer = scanner.nextLine();

        if (userAnswer.equals("yes".toLowerCase())) {

            LOG.info("Please enter the DB properties \"user\", \"password\", \"db.url\","
                    + " \"db.driver\" separated by \";\"");

            List <String> userDbProperties = new LinkedList<>(Arrays.asList(scanner.nextLine().split(";")));

            dbProperties.setProperty("user", userDbProperties.get(0));
            dbProperties.setProperty("password", userDbProperties.get(1));
            dbProperties.setProperty("db.url", userDbProperties.get(2));
            dbProperties.setProperty("db.driver", userDbProperties.get(3));
        }

        if (userAnswer.equals("")) {
            return dbProperties;
        }
        return dbProperties;
    }

    /**
     * Returns the full path to export file.
     *
      * @param fullPathToFile
     * @return
     */
    public String getFullPathToFile(String fullPathToFile) {

        LOG.info("The default full path to the export file is: " + fullPathToFile + LINE_SEPARATOR + "  "
                + "If you want to change its, please enter the new full path to file." + LINE_SEPARATOR + "  "
                + "Press \"Enter\" to continue, using the default path.");

        userAnswer = scanner.nextLine();

        // If entered line has character
        // that separates components of a file pat ("/" on UNIX and "\" on Windows).
        if (userAnswer.indexOf(System.getProperty("file.separator")) !=-1) {
            fullPathToFile = userAnswer;
        }
        return fullPathToFile;
    }

    /**
     * Returns the list of emails.
     *
     * @param emailAddresses
     * @return - List<String> emails
     */
    public List<String> getEmails(List<String> emailAddresses) {

        List<String> emails = emailAddresses;

        LOG.info("The default list of email addresses has following emails: " + LINE_SEPARATOR + "  "
                + emails.toString() + LINE_SEPARATOR
                + "If you want to change its, please enter \"yes\"" + LINE_SEPARATOR
                + "Press \"Enter\" to continue, using default emails.");

        userAnswer = scanner.nextLine();

        if (userAnswer.equals("yes".toLowerCase())) {

            LOG.info("Please enter emails separated by \";\"");

            emails = new LinkedList<>(Arrays.asList(scanner.nextLine().split(";")));
            return emails;
        }

        if (userAnswer.equals("")) {

            return emails;
        }
        return emails;
    }
}
