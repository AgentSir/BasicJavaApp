package ua.sputilov.utils;

import java.io.*;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * The {@code PropertiesLoader} class represents utils for load the properties from properties file.
 * The class {@code PropertiesLoader} includes methods for set and get Properties object.
 * Loading is implemented through the {@code InputStream} class and  {@code InputStreamReader} class
 * using the {@code load} method defined by the {@code Properties} class.
 *
 * @author  Sergey Putilov
 * @see java.io.InputStreamReader
 * @see java.io.InputStream
 * @see java.util.Properties
 */
public class PropertiesLoader {

    private final Logger LOG = Logger.getLogger(PropertiesLoader.class.getName());

    private Properties properties = new Properties();

    public PropertiesLoader (String propertiesFileName){
        setProperties(propertiesFileName);
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * Loads the properties.
     */
    private void setProperties(String propertiesFileName) {

        InputStream inputStream;
        Reader readerIS;

        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
            readerIS = new  InputStreamReader(inputStream, "UTF-8");
            properties.load(readerIS);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
