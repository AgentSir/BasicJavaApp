package ua.sputilov.entities;

import java.sql.*;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * The {@code DBReader} class's object reads information from DB's tables according to the SQL query.
 * Reading is implemented through the {@code Connection}, the {@code Statement} and the {@code ResultSet} classes.
 * At the beginning through the {@code Connection} class, the {@code DriverManager} class
 * and its the {@code getConnection} method that received the DB URL and DB connection properties
 * from the db_connection.properties file as parameters, getting connection to DB.
 * Next is creating {@code Statement} object and {@code ResultSet} object according the executed SQL query.
 * It's implemented through the {@code Connection} object
 * and its the {@code createStatement()} method, and through the {@code Statement} object
 * and its the {@code executeQuery(String sqlQuery)} method.
 *
 * At last the {@code DBReader} can returned the {@code ResultSet} object.
 * Returning is implemented through the {@code getDBInfo} method.
 *
 *  @author  Sergey Putilov
 *  @see     java.sql.Connection
 *  @see     java.sql.DriverManager
 *  @see     java.sql.Statement
 *  @see     java.sql.ResultSet
 */
public class DBReader {

    private final Logger LOG = Logger.getLogger(DBReader.class.getName());
    public final String LINE_SEPARATOR = System.getProperty("line.separator");

    private Connection dbConnection;
    private Statement statement;
    private ResultSet resultSet;
    private Properties dbConnectionProperties;

    public DBReader(Properties properties) {
        dbConnectionProperties = properties;
    }

    /**
     * Returns the ResultSet object with the received information from DB's tables according to the SQL query.
     *
     * @param sqlQuery - the SQL query.
     * @return - the ResultSet with information from DB.
     */
    public ResultSet getDBInfo (String sqlQuery) {

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            LOG.info("Receiving information from DB's tables according the following SQL query: "  + LINE_SEPARATOR
                    + "  " + sqlQuery + LINE_SEPARATOR);
            resultSet = statement.executeQuery(sqlQuery);
            return  resultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    /**
     * Returns the connection to DB.
     *
     * @return - the connection to DB.
     */
    private Connection getDBConnection() {

        try {
            Class.forName(dbConnectionProperties.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            dbConnection = DriverManager.getConnection(dbConnectionProperties.getProperty("db.url"), dbConnectionProperties);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}
