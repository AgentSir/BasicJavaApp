package ua.sputilov.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The class {@code FormattedDate} represents a utils for getting the current date.
 * The {@code FormattedDate} includes method for define the current date in format "yyyy-MM-dd"
 * Defining of the date in need format is implemented through the {@code Date} class and
 * the {@code SimpleDateFormat} class and its {@code format} method.
 *
 * @author  Sergey Putilov
 * @see     java.util.Date
 * @see     java.text.SimpleDateFormat
 */
public class FormattedDate {

    /**
     * The method for get the current date.
     *
     * @return - the date in "yyyy-MM-dd" format as string.
     */
    public String getCurrentDate() {
        Date today = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(today);
    }
}
