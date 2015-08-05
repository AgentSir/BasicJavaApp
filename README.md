# Basic Java test


Description:

A command line application Java with following functionality: to read records from a database, to save the records

to a text file, to create an email object with the file as an attachment, to send the email to a defined addresses.


Individual steps:

1. Install MariaDB (MySQL) database engine on your computer.

2. Create new database named TestDb.

3. Create two tables in that database:

  a. person – columns id (int) NOT NULL PK, firstname (nvarchar(50)), lastname (nvarchar(50)).
    
  b. address – columns id (int) NOT NULL PK, person_id (int) NOT NULL FK, street (nvarchar(50)), 
     city (nvarchar(50)), zip (varchar(10)).

4. Insert one record for each table with data of your own choice.

5. Create command line application Java in arbitrary IDE or text editor.

6. Create method for reading inserted records from database and writing data to the text file. 

    The SQL query must use inner join clause. The line in the text file must have this following format: 
   
    person_id; firstname; lastname; street; city; zip; current date in format YYYY-MM-DD.
   
7. Create and configure an email object using javax.mail library. Use the text file as an attachment.

8. Send the email to defined addresses. You’ll get target email addresses and an address of SMPT server later.

9. Compile an run the application.


You have to present the running application, the source code and all sql script 

you’ll create during building the database and data processing.
