// Imports:

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/**
* This class contains code to read a file in fasta format.  This program will
* read in the sequences from the human proteome to then use in database
* generation
*
* Created By: Alex Cadigan
* Date Last Modified: 7/12/2017
*/
public class fastaFileReader
{
  // Instance Variables:

  ArrayList<String> headers = new ArrayList<String>();
  ArrayList<String> sequences = new ArrayList<String>();

  // Constructors:

  /**
  * Constructs an instance of this class
  */
  public fastaFileReader() {}

  // Methods:

  /**
  * Reads in amino acid sequences from a fasta file
  *
  * @param      filename              The name of the fasta file to readSequences
  *
  * @return     void
  */
  public void readSequences(String filename)
  {
    // Attempts to read data from the given file
    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(filename));

      String line = reader.readLine();

      // Runs through all the data in the file, extracting the amino acid sequences
      while (line != null)
      {
        if (!line.equals(""))
        {
          if (line.substring(0, 1).equals(">"))
          {
            this.headers.add(line);
          }
          else
          {
            this.sequences.add(line);
          }
        }

        line = reader.readLine();
      }
    }
    // Catches any errors that are thrown
    catch (IOException exception)
    {
      System.out.println("\nError when reading " + filename);
      exception.printStackTrace();
    }
  }

  /**
  * Returns the headers from the proteome database
  *
  * @return     ArrayList<String>     The headers from the proteome database
  */
  public ArrayList<String> getHeaders()
  {
    return this.headers;
  }

  /**
  * Returns the sequences from the proteome database
  *
  * @return     ArrayList<String>     The sequences from the proteome database
  */
  public ArrayList<String> getSequences()
  {
    return this.sequences;
  }
}
