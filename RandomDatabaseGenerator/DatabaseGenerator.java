// Imports:

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Random;

/**
* This class contains code to generate an amino acid database.  The database
* will be generated randomly, using sample data from the complete human
* proteome
*
* Created By: Alex Cadigan
* Date Last Modified: 7/21/2017
*/
public class DatabaseGenerator
{
  // Instance variables:

  private Random generator = new Random();
  private ArrayList<String> sequenceInfo = new ArrayList<String>();
  private char [] aminoAcids = {'A', 'R', 'N', 'D', 'C', 'E', 'Q', 'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V'};

  // Constructors:

  /**
  * Constructs an instance of this class
  */
  public DatabaseGenerator() {}

  // Methods:

  /**
  * Converts the size of the database to generate into bytes
  *
  * @param      rawSize     The database size entered by the user
  * @param      units       The units of the database size
  *
  * @return     double      The database size in bytes
  */
  public double convertSize(double rawSize, String units)
  {
    double size = 0;

    if (units.equals("Kilobytes"))
    {
      size = rawSize * 1000;
    }
    else if (units.equals("Megabytes"))
    {
      size = rawSize * 1000000;
    }
    else if (units.equals("Gigabytes"))
    {
      size = rawSize * 1000000000;
    }
    else
    {
      size = rawSize;
    }

    return size;
  }

  /**
  * This method will generate a randomized database of a specific size using
  * sample data from the human proteome
  *
  * @param      size            The size of the database to generate
  * @param      headers         The human proteome headers
  * @param      Sequences       The human proteome sequences
  * @param      percentages     The percentages of each method to use in database generation
  * @param      outputPath      The path to the output file
  *
  * @return     void
  */
  public void generateDatabase(double size, ArrayList<String> headers, ArrayList<String> sequences, double [] percentages, String outputPath)
  {
    try (PrintWriter dataWriter = new PrintWriter(outputPath))
    {
      // Creates sequences until the database size requirement has been filled
      while (size > 0)
      {
        // Generates an amino acid sequence using one of the five methods
        int randomNumber = this.generator.nextInt(100);
        if (randomNumber < percentages[0])
        {
          this.method0(headers, sequences);
        }
        else if (randomNumber < (percentages[0] + percentages[1]))
        {
          this.method1(headers, sequences);
        }
        else if (randomNumber < (percentages[0] + percentages[1] + percentages[2]))
        {
          this.method2(headers, sequences);
        }
        else if (randomNumber < (percentages[0] + percentages[1] + percentages[2] + percentages[3]))
        {
          this.method3(headers, sequences);
        }
        else
        {
          this.method4(headers, sequences);
        }

        // Checks if the database is exceeding the size limit
        if (this.sequenceInfo.get(1).length() > size)
        {
          // Writes the sequence and decoy to the database file
          dataWriter.println(this.sequenceInfo.get(0));
          String sequence = this.sequenceInfo.get(1).substring(0, (int) Math.round(size));
          dataWriter.println(sequence);
          dataWriter.println(this.sequenceInfo.get(0).substring(0, 1) + "rev_" + this.sequenceInfo.get(0).substring(1, this.sequenceInfo.get(0).length()));
          dataWriter.println(this.generateDecoySequence(sequence));

          size -= sequence.length();
        }
        else
        {
          // Writes the sequence and decoy to the database file
          dataWriter.println(this.sequenceInfo.get(0));
          dataWriter.println(this.sequenceInfo.get(1));
          dataWriter.println(this.sequenceInfo.get(0).substring(0, 1) + "rev_" + this.sequenceInfo.get(0).substring(1, this.sequenceInfo.get(0).length()));
          dataWriter.println(this.generateDecoySequence(this.sequenceInfo.get(1)));

          size -= this.sequenceInfo.get(1).length();
        }

        this.sequenceInfo = new ArrayList<String>();
      }
    }
    // Catches any errors that are thrown
    catch (IOException exception)
    {
      System.out.println("\nError when writing to " + outputPath);
      exception.printStackTrace();
    }
  }

  /**
  * This method generates an amino acid sequence to add to the database.  This
  * method chooses a random sequence from the human proteome and selects a
  * substring of random length from that sequence
  *
  * @param      headers       The headers of the sequences from the human proteome
  * @param      sequences     The amino acid sequences from the human proteome
  *
  * @return     void
  */
  private void method0(ArrayList<String> headers, ArrayList<String> sequences)
  {
    // Randomly selects a sequence
    int randomNumber = this.generator.nextInt(sequences.size());
    this.sequenceInfo.add(headers.get(randomNumber));

    // If the selected sequence is too small to manipulate it will be returned
    if (sequences.get(randomNumber).length() <= 50)
    {
      this.sequenceInfo.add(sequences.get(randomNumber));
    }
    // Otherwise a substring of the sequence will be returned
    else
    {
      this.sequenceInfo.add(sequences.get(randomNumber).substring(0, this.generator.nextInt(sequences.get(randomNumber).length() - 49) + 50));
    }
  }

  /**
  * This method generates an amino acid sequence to add to the database.  This
  * method chooses a random sequence from the human proteome and mutates a
  * random amino acid
  *
  * @param      headers       The headers of the sequences from the human proteome
  * @param      sequences     The amino acid sequences from the human proteome
  *
  * @return     void
  */
  private void method1(ArrayList<String> headers, ArrayList<String> sequences)
  {
    // Randomly selects a sequence
    int randomNumber = this.generator.nextInt(sequences.size());
    String tempSequence = sequences.get(randomNumber);
    this.sequenceInfo.add(headers.get(randomNumber));

    // Determines the amino acids to mutate
    int oldAminoAcid = this.generator.nextInt(20);
    int newAminoAcid = this.generator.nextInt(20);

    // Runs through the sequence and implements the mutation
    for (int index = 0; index < tempSequence.length(); index ++)
    {
      if (tempSequence.charAt(index) == this.aminoAcids[oldAminoAcid])
      {
        tempSequence = tempSequence.substring(0, index) + this.aminoAcids[newAminoAcid] + tempSequence.substring(index + 1, tempSequence.length());
      }
    }

    this.sequenceInfo.add(tempSequence);
  }

  /**
  * This method generates an amino acid sequence to add to the database.  This
  * method chooses a random sequence from the human proteome and randomly
  * inserts amino acids into that sequence
  *
  * @param      headers       The headers of the sequences from the human proteome
  * @param      sequences     The amino acid sequences from the human proteome
  *
  * @return     void
  */
  private void method2(ArrayList<String> headers, ArrayList<String> sequences)
  {
    // Randomly selects a sequence
    int randomNumber = this.generator.nextInt(sequences.size());
    String tempSequence = sequences.get(randomNumber);
    this.sequenceInfo.add(headers.get(randomNumber));

    // Runs through the sequence and inserts random amino acids
    for (int index = 0; index < tempSequence.length(); index ++)
    {
      int insertionIndex = this.generator.nextInt(20);
      index += insertionIndex;
      if (index < tempSequence.length())
      {
        tempSequence = tempSequence.substring(0, index) + this.aminoAcids[this.generator.nextInt(20)] + tempSequence.substring(index, tempSequence.length());
      }
    }

    this.sequenceInfo.add(tempSequence);
  }

  /**
  * This method generates an amino acid sequence to add to the database.  This
  * method chooses a random sequence from the human proteome and randomly
  * deletes amino acids in that sequence
  *
  * @param      headers       The headers of the sequences from the human proteome
  * @param      sequences     The amino acid sequences from the human proteome
  *
  * @return     void
  */
  private void method3(ArrayList<String> headers, ArrayList<String> sequences)
  {
    // Randomly selects a sequence
    int randomNumber = this.generator.nextInt(sequences.size());
    String tempSequence = sequences.get(randomNumber);
    this.sequenceInfo.add(headers.get(randomNumber));

    // Runs through the sequence and deletes random amino acids
    for (int index = 0; index < tempSequence.length(); index ++)
    {
      int deletionIndex = this.generator.nextInt(20);
      index += deletionIndex;
      if (index < tempSequence.length())
      {
        tempSequence = tempSequence.substring(0, index) + tempSequence.substring(index + 1, tempSequence.length());
      }
    }

    this.sequenceInfo.add(tempSequence);
  }

  /**
  * This method generates an amino acid sequence to add to the database.  This
  * method concatenates sequences from two of the pervious four methods.  The
  * two methods to concatenate are chosen randomly
  *
  * @param      headers       The headers of the sequences from the human proteome
  * @param      sequences     The amino acids sequences from the human proteome
  *
  * @return     void
  */
  private void method4(ArrayList<String> headers, ArrayList<String> sequences)
  {
    // Generates the first subsequence
    for (int index = 0; index < 2; index ++)
    {
      int methodNumber = this.generator.nextInt(4);
      if (methodNumber == 0)
      {
        this.method0(headers, sequences);
      }
      else if (methodNumber == 1)
      {
        this.method1(headers, sequences);
      }
      else if (methodNumber == 2)
      {
        this.method2(headers, sequences);
      }
      else
      {
        this.method3(headers, sequences);
      }
    }

    ArrayList<String> tempInfo = new ArrayList<String>();
    tempInfo.add(this.sequenceInfo.get(0));
    tempInfo.add(this.sequenceInfo.get(1) + this.sequenceInfo.get(3));

    this.sequenceInfo = tempInfo;
  }

  /**
  * This method generates a decoy reverse sequence given an amino acid sequence
  *
  * @param      sequence    The sequence to use when generating the decoy
  *
  * @return     String      The decoy sequence
  */
  private String generateDecoySequence(String sequence)
  {
    String decoySequence = "";
    for (int index = sequence.length() - 1; index >= 0; index --)
    {
      decoySequence += sequence.charAt(index);
    }

    return decoySequence;
  }
}
