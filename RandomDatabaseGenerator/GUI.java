// Imports:

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
* This class contains code to create a GUI for the user to enter
* input.  The input will be passed along to subsequent methods
* to be used in generating the random database
*
* Created By: Alex Cadigan
* Date Last Modified: 7/21/2017
*/
public class GUI
{
	// Instance Variables:

	private double databaseSize;
	private String units, databasePath, outputPath;
	private double [] percentages;

	// Constructors:

	/**
	* This constructor will create the GUI for the user
	*/
	public GUI()
	{
		// Initializes the GUI window
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Random Database Generator");
		frame.setSize(600, 500);
		frame.setLocationRelativeTo(null);

		// Creates the database size fields
		JPanel panel1 = new JPanel();

		JLabel lblDatabaseSize = new JLabel("Database Size:");
		panel1.add(lblDatabaseSize);

		JTextField txtDatabaseSize = new JTextField(5);
		panel1.add(txtDatabaseSize);

		String [] listInput = {"Bytes", "Kilobytes", "Megabytes", "Gigabytes"};
		JList lsUnits = new JList(listInput);
		lsUnits.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lsUnits.setSelectedIndex(2);
		panel1.add(lsUnits);

		// Creates the percentages fields
		JLabel lblpercentages = new JLabel("Percent of the database that will be built with each method:");

		JPanel percentPanel1 = new JPanel();
		JLabel lblmethod0 = new JLabel("Method 0:");
		percentPanel1.add(lblmethod0);
		JTextField txtmethod0 = new JTextField("20", 5);
		percentPanel1.add(txtmethod0);

		JPanel percentPanel2 = new JPanel();
		JLabel lblmethod1 = new JLabel("Method 1:");
		percentPanel2.add(lblmethod1);
		JTextField txtmethod1 = new JTextField("20", 5);
		percentPanel2.add(txtmethod1);

		JPanel percentPanel3 = new JPanel();
		JLabel lblmethod2 = new JLabel("Method 2:");
		percentPanel3.add(lblmethod2);
		JTextField txtmethod2 = new JTextField("20", 5);
		percentPanel3.add(txtmethod2);

		JPanel percentPanel4 = new JPanel();
		JLabel lblmethod3 = new JLabel("Method 3:");
		percentPanel4.add(lblmethod3);
		JTextField txtmethod3 = new JTextField("20", 5);
		percentPanel4.add(txtmethod3);

		JPanel percentPanel5 = new JPanel();
		JLabel lblmethod4 = new JLabel("Method 4:");
		percentPanel5.add(lblmethod4);
		JTextField txtmethod4 = new JTextField("20", 5);
		percentPanel5.add(txtmethod4);

		// Creates objects for a user to select what proteome database file to read in
		JPanel databaseInfo = new JPanel();
		JLabel lblDatabase = new JLabel("Proteome database to use:");
		databaseInfo.add(lblDatabase);
		JTextField txtDatabase = new JTextField(20);
		databaseInfo.add(txtDatabase);

		// Creates a button to use when selecting a file
		JButton btnSelectDatabase = new JButton("Select File");
		btnSelectDatabase.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				selectFile(txtDatabase);
			}
		});

		// Adds the file selection info together
		JPanel fileSelectInfo1 = new JPanel();
		fileSelectInfo1.add(databaseInfo);
		fileSelectInfo1.add(btnSelectDatabase);

		// Creates objects for a user to select what output file to use
		JPanel outputInfo = new JPanel();
		JLabel lblOutput = new JLabel("Output file:");
		outputInfo.add(lblOutput);
		JTextField txtOutput = new JTextField(20);
		outputInfo.add(txtOutput);

		// Creates a button to use when selecting a file
		JButton btnSelectOutput = new JButton("Select File");
		btnSelectOutput.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				selectFile(txtOutput);
			}
		});

		// Adds the file selection info together
		JPanel fileSelectInfo2 = new JPanel();
		fileSelectInfo2.add(outputInfo);
		fileSelectInfo2.add(btnSelectOutput);

		// Creates an object that can hold the previous GUI objects
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		masterPanel.add(panel1);
		masterPanel.add(lblpercentages);
		masterPanel.add(percentPanel1);
		masterPanel.add(percentPanel2);
		masterPanel.add(percentPanel3);
		masterPanel.add(percentPanel4);
		masterPanel.add(percentPanel5);
		masterPanel.add(fileSelectInfo1);
		masterPanel.add(fileSelectInfo2);

		// Creates an initializes a button
		JButton btnBegin = new JButton("Start");
		btnBegin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				collectUserInput(frame, txtDatabaseSize, lsUnits, txtmethod0, txtmethod1, txtmethod2, txtmethod3, txtmethod4, txtDatabase, txtOutput);
			}
		});

		// Finishes setting up the frame
		frame.add(masterPanel);
		frame.add(btnBegin, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

	// Methods:

	/**
	* This method allows the user to select a file from their directories
	*
	* @param 		textField 		The text file that will display the path of the file
	*
	* @return 		void
	*/
	public void selectFile(JTextField textField)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

		JFrame frame = new JFrame();

		int result = fileChooser.showOpenDialog(frame);

		if (result == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			textField.setText(selectedFile.getAbsolutePath());
		}

		frame.dispose();
	}

	/**
	* This method is called every time the begin button is pressed.
	* This method serves to collect user input from the GUI and then
	* begin the process of generating the database
	*
	* @param 		frame 						The frame of the GUI window
	* @param 		txtDatabaseSize 			The database size textbox
	* @param 		lsUnits 					The units of the database size
	* @param 		txtmethod0 					The percentage information
	* @param 		txtmethod1					The percentage information
	* @param 		txtmethod2					The percentage information
	* @param 		txtmethod3 					The percentage information
	* @param 		txtmethod4 					The percentage information
	* @param 		txtDatabase 				The path of the proteome database file to read
	* @param 		txtOutput					The path of the file to use as output
	*
	* @return 		void
	*/
	private void collectUserInput(JFrame frame, JTextField txtDatabaseSize, JList lsUnits, JTextField txtmethod0, JTextField txtmethod1, JTextField txtmethod2, JTextField txtmethod3, JTextField txtmethod4, JTextField txtDatabase, JTextField txtOutput)
	{
		// Attempts to get the database size from the GUI
		try
		{
			this.databaseSize = Double.parseDouble(txtDatabaseSize.getText());

			// Checks if the database size is below 0
			if (this.databaseSize < 0)
			{
				this.databaseSize = 0;
			}
		}
		// If the input is not a double this error will be shown
		catch (NumberFormatException exception)
		{
			JOptionPane.showMessageDialog(null, "Error!  Please enter only double values for the database size.", "Error Message", 0);
			System.exit(0);
		}

		// Gets the units of the database size
		this.units = (String) lsUnits.getSelectedValue();

		// Attempts to get the percentages entered from the GUI
		try
		{
			this.percentages = new double [5];
			this.percentages[0] = Double.parseDouble(txtmethod0.getText());
			this.percentages[1] = Double.parseDouble(txtmethod1.getText());
			this.percentages[2] = Double.parseDouble(txtmethod2.getText());
			this.percentages[3] = Double.parseDouble(txtmethod3.getText());
			this.percentages[4] = Double.parseDouble(txtmethod4.getText());
		}
		// If the input is not an integer this error will be shown
		catch (NumberFormatException exception)
		{
			JOptionPane.showMessageDialog(null, "Error!  Please enter only double values for the percent values.", "Error Message", 0);
			System.exit(0);
		}

		// Checks if any percents were set to a negative number
		for (int index = 0; index < this.percentages.length; index ++)
		{
			if (this.percentages[index] < 0)
			{
				this.percentages[index] = 0;
			}
		}

		// Checks if the percentages entered add up to 100 percent
		if ((this.percentages[0] + this.percentages[1] + this.percentages[2] + this.percentages[3] + this.percentages[4]) != 100)
		{
			JOptionPane.showMessageDialog(null, "Error!  The percent values must add up to 100.", "Error Message", 0);
			System.exit(0);
		}

		// Gets the path of the proteome database to read
		this.databasePath = txtDatabase.getText();

		// Checks if no file was selected
		if (this.databasePath.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Error!  No proteome database file was selected.", "Error Message", 0);
			System.exit(0);
		}

		// Gets the path of the output file
		this.outputPath = txtOutput.getText();

		// Checks if no file was selected
		if (this.outputPath.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Error!  No output file was selected.", "Error Message", 0);
			System.exit(0);
		}

		frame.dispose();

		// Begins the process of generating the database
		BeginProgram runProgram = new BeginProgram();
		runProgram.run(this.databaseSize, this.units, this.percentages, this.databasePath, this.outputPath);
	}
}
