// Imports:

import java.util.ArrayList;

/**
* This class contains code to run a random database generator program.  This
* class controlls all processes needed to create the database
*
* Created By: Alex Cadigan
* Date Last Modified: 7/21/2017
*/
public class BeginProgram
{
  // Constructors:

  /**
  * Constructs an instance of this class
  */
  public BeginProgram() {}

  // Methods:

  /**
  * This is the main method.  It will initiate the process of generating
  * the database
  *
  * @param      args    Not used in this program
  *
  * @return     void
  */
  public static void main(String [] args)
  {
    System.out.println("\n----------Starting Program----------");

    // Creates the GUI to collect user input
    new GUI();
  }

  /**
  * This method will controll all processes of generating the 
  * database after the user input from the GUI has been collected
  *
  * @param      rawDatabaseSize     The unconverted database size entered by the user
  * @param      units               The units of the database size entered by the user
  * @param      percentages         The percents of each method to use in database generation
  * @param      databasePath        The path to the proteome database
  * @param      outputPath          The path to the output file
  *
  * @return     void
  */
  public void run(double rawDatabaseSize, String units, double [] percentages, String databasePath, String outputPath)
  {
    // Reads in the human proteome data
    System.out.println("\nReading data from the proteome...");
    fastaFileReader fileReader = new fastaFileReader();
    fileReader.readSequences(databasePath);
    ArrayList<String> headers = fileReader.getHeaders();
    ArrayList<String> sequences = fileReader.getSequences();

    // Generates the database
    System.out.println("\nGenerating the proteome database...");
    DatabaseGenerator generator = new DatabaseGenerator();
    double databaseSize = generator.convertSize(rawDatabaseSize / 2, units);
    generator.generateDatabase(databaseSize, headers, sequences, percentages, outputPath);

    System.out.println("\n----------Ending Program----------");
  }
}
