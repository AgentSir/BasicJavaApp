package ua.sputilov.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.LinkedList;
import java.util.List;

/**
 * The {@code FormattedResultSet} class represents utils for represents the {@code ResultSet} object like
 * list of formatted string lines.
 * Formatting are implemented through the method {@code format}, defined by {@code String}.
 *
 * @author Sergey Putilov
 * @see java.lang.String
 *
 */
public class FormattedResultSet {

    private ResultSet resultSet;

    /**
     * The constructor initialize the resultSet variable form received argument.
     *
     * @param resultSet - the ResultSet object.
     */
    public FormattedResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * Returns the formatted data from ResultSet object as list of lines.
     * The line of txt file has this following format:
     * person_id; firstname; lastname; street; city; zip; current date in format YYYY-MM-DD.
     * Getting the current date is implemented through the {@code FormattedDate} class
     * and its method {@code getCurrentDate()}
     *
     * @return - the list of DB's records.
     */
    public List<String> getFormattedData() {

        FormattedDate dateInformer = new FormattedDate();
        List<String> dbRecords = new LinkedList<>();

        try {
            while (resultSet.next()) {
                // create a formatted line from received records SQL query
                String dbRecord = String.format("%-4d%-14s%-14s%-14s%-14s%-8d%-13s",
                        resultSet.getInt("person_id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("city"),
                        resultSet.getString("street"),
                        resultSet.getInt("zip"),
                        // getting a current date
                        dateInformer.getCurrentDate());
                dbRecords.add(dbRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbRecords;
    }
}
