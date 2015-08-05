package ua.sputilov.entities;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * The {@code MailSender} class's object sending email messages with attention to list of emails.
 * Sending is implements through the javax.mail library.
 * The {@code MailSender} class sends emails using its {@code sendMessages} method.
 *
 * @author  Sergey Putilov
 * @see javax.mail library
 */
public class MailSender {

    private final String SEND_FROM_EMAIL = "dbsupport@yandex.ru";
    private final Logger LOG = Logger.getLogger(MailSender.class.getName());
    public final String LINE_SEPARATOR = System.getProperty("line.separator");

    private Properties sessionProperties = new Properties();
    private Properties smtpProperties = new Properties();

    public MailSender(Properties properties) {
        smtpProperties = properties;
    }

    /**
     * Sends the email.
     *
     * @param emailAddresses - list of emails
     * @param subject - the subject of email
     * @param text - the text of email's body.
     * @param fullPathToFileForAttention - the full path to the file for attachment
     */
    public void sendMessages(List<String> emailAddresses, String subject, String text, String fullPathToFileForAttention) {

        LOG.info("Sending emails..." + LINE_SEPARATOR);

        // turn on the authentication, and getting session
        sessionProperties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(sessionProperties);

        try {
            // Getting the Transport object for sending an email
            Transport transport = session.getTransport("smtp");
            // SMTP server usually needs to get login and password
            transport.connect(smtpProperties.getProperty("smtp.server"), smtpProperties.getProperty("smtp.login"),
                    smtpProperties.getProperty("smtp.password"));

            // Create the Message object
            Message msg = new MimeMessage(session);
            // Setting the message attributes
            msg.setFrom(new InternetAddress(SEND_FROM_EMAIL));
            InternetAddress[] addresses = getAddresses(emailAddresses);
            msg.setRecipients(Message.RecipientType.TO, addresses);
            msg.setSubject(subject);
            //Setting the content and sending messages
            setTextAndFileAsAttachment(msg, text, fullPathToFileForAttention);
            msg.saveChanges();
            transport.sendMessage(msg, addresses);
            transport.close();
            LOG.info("All emails send successfully." + LINE_SEPARATOR);

        } catch (MessagingException mex) {
            // print the info about any possible exceptions
            mex.printStackTrace();
        }
    }

    /**
     *  Sets a text and attach a file as an attachment
     *
     * @param msg - The Message object
     * @param text - The text of email message
     * @param fullPathToFileForAttention - The full path to the file for attach to email message
     * @throws MessagingException - exception
     */
    private void setTextAndFileAsAttachment(Message msg, String text, String fullPathToFileForAttention) throws MessagingException {

        // Create and set the text to the first part of email message
        MimeBodyPart part1 = new MimeBodyPart();
        part1.setText(text);

        // Create the second email's part for attach to email message
        MimeBodyPart part2 = new MimeBodyPart();

        // add the file to the second part
        FileDataSource fileDataSource = new FileDataSource(fullPathToFileForAttention);
        part2.setDataHandler(new DataHandler(fileDataSource));
        part2.setFileName(fileDataSource.getName());

        // Create an object of Multipart and add all parts
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(part1);
        multipart.addBodyPart(part2);

        // Set up the Multipart object as a content of email
        msg.setContent(multipart);
    }

    /**
     * Returns a email addresses from List of email addresses as an array of InternetAddress objects.
     *
     * @param emailAddresses - List of emails
     * @return - the array of InternetAddress objects
     */
    private InternetAddress [] getAddresses (List<String> emailAddresses) {

        InternetAddress[] internetAddresses = new InternetAddress [emailAddresses.size()];

        for (int i = 0; i < emailAddresses.size(); i++) {
            internetAddresses [i] = new InternetAddress();
            internetAddresses [i].setAddress(emailAddresses.get(i));
        }
        return internetAddresses;
    }
}