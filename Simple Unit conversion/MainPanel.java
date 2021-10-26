import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Checkbox;
import java.awt.event.KeyAdapter;
import java.text.DecimalFormat;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JSeparator;


/**
 * The main graphical panel used to display conversion components.
 * 
 * This is the starting point for the assignment.
 * 
 * The variable names have been deliberately made vague and generic, and most comments have been removed.
 * 
 * You may want to start by improving the variable names and commenting what the existing code does.
 * 
 * @author mdixon
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	
    //declaring the variables
	private final static String[] list = { "inches/cm", "Pounds/Kilograms", "Degrees/Radians", "Acres/Hectares", "Miles/Kilometres", "Yards/Metres", "Celsius/Fahrenheit" };
	private JTextField textField;
	private JLabel label;
	private JComboBox<String> combo;
	private JLabel counter;
	private JCheckBox reverseCheckBox;
    int count=0;


	JMenuBar setupMenu() {

		JMenuBar menuBar = new JMenuBar();
		
		
        //creating menu
		JMenu File = new JMenu("File");
	    JMenu Save = new JMenu("Save");
		JMenu Help = new JMenu("Help");
		
		
		// adding menus to the menubar
		menuBar.add(File);
		menuBar.add(Save);
		menuBar.add(Help);
		
		
		//creating menuitem	
		JMenuItem Open = new JMenuItem("Open");
		JMenuItem Copy = new JMenuItem("Copy");
		JMenuItem Paste = new JMenuItem("Paste");
		JMenuItem Cut = new JMenuItem("Cut");
		JMenuItem Exit = new JMenuItem("Exit");
		
		
		//adding the menuitems into file menu
		JSeparator se= new JSeparator(); //use to separate
		File.add(Open);
		File.add(Copy);
		File.add(Paste);
		File.add(Cut);
		File.add(se);
		File.add(Exit);
		
		
		//to add the shortcut(mnemonics) keys
		Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		Cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		
		
		//adds the text when hover
		File.setToolTipText("File Expolorer");
		Save.setToolTipText("Press to save file");
		Help.setToolTipText("Get help");
		Open.setToolTipText("Press to open the exsiting file");
		Copy.setToolTipText("Press to save the file");
		Paste.setToolTipText("Press to create new file");
		Cut.setToolTipText("Press to delete the file");
		Exit.setToolTipText("Press to terminate");
		
		
		//adding icon to the menu and the menuitems
		File.setIcon(new ImageIcon("C:/Users/Dell/Documents/Converter/icon/file.png"));
		Save.setIcon(new ImageIcon("C:/Users/Dell/Documents/Converter/icon/save.png"));
		Help.setIcon(new ImageIcon("C:/Users/Dell/Documents/Converter/icon/help.png"));
		Open.setIcon(new ImageIcon("C:/Users/Dell/Documents/Converter/icon/open1.png"));	
		Copy.setIcon(new ImageIcon("C:/Users/Dell/Documents/Converter/icon/copy1.png"));
		Paste.setIcon(new ImageIcon("C:/Users/Dell/Documents/Converter/icon/paste.png"));
		Cut.setIcon(new ImageIcon("C:/Users/Dell/Documents/Converter/icon/cut.png"));
		Exit.setIcon(new ImageIcon("C:/Users/Dell/Documents/Converter/icon/exit.png"));
		
		
		//adding listener that exit the application 
		Exit.addActionListener(e -> System.exit(0));  
		Exit.addActionListener(e ->{
			JOptionPane.showMessageDialog(this, "Thank You!!"); // shows the message box when exit
		});
		
		//creating menuitems 		
		JMenuItem Save1 = new JMenuItem("Save");
		JMenuItem SaveAs = new JMenuItem("Save As");
		
		//adding menuitems to the save menu
		Save.add(Save1);
		Save.add(SaveAs);
		
		//creating menuitems 
		JMenuItem ViewHelp = new JMenuItem("View Help");	
		JMenuItem About = new JMenuItem("About");
		About.addActionListener(e ->{
			JOptionPane.showMessageDialog(this, "This application is for the efficient conversion and it is also user-friendly.\n@RimaPalli2020", "About", 1); //shows message when about is pressed
		});
		
		
		//adds menuitems into help menu
		Help.add(ViewHelp);
		Help.add(About);
		
		

		return menuBar;
	}

	private void setDefaultCloseOperation(int exitOnClose) {
		// TODO Auto-generated method stub
		
	}


	MainPanel() {

		ActionListener listener = new ConvertListener();

		combo = new JComboBox<String>(list);
		combo.addActionListener(listener);         //convert values when option changed

		JLabel inputLabel = new JLabel("Enter value:");
		

		JButton convertButton = new JButton("Convert");
		convertButton.addActionListener(listener); // convert values when pressed
		convertButton.setToolTipText("Press to get the result");
		
		counter= new JLabel(String.valueOf("Conversion Count:"+ count ));  //
		
		JButton Clear = new JButton("Clear");
		Clear.addActionListener(e ->{           //clear all the values when clicked
			textField.setText(null);        
			label.setText(null);  
			count=0;              //shows count 0 when clear is clicked
			counter.setText("Conversion Count: 0");	 		
		});
		Clear.setToolTipText("Clear Everything");
		
		
		reverseCheckBox = new JCheckBox("Reverse Conversion");  
		label = new JLabel("---");
		textField = new JTextField(5);
		textField.addKeyListener(new KeyAdapter() {    // convert values when enter is pressed
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)   
					convertButton.doClick();
			}
		});
		
		//adds components in the panel
		add(combo);
		add(inputLabel);
		add(textField);
		add(convertButton);
		add(Clear);	
		add(label);
		add(reverseCheckBox);
		add(counter);


		setPreferredSize(new Dimension(800, 80));
		setBackground(Color.LIGHT_GRAY);
	}

	private class ConvertListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {			
			String text = textField.getText().trim();
			try{
			if (textField.getText().isEmpty()) {   // shows message if input isnot given
			    JOptionPane.showMessageDialog(textField, "You have not entered any Number");
			}

			if (text.isEmpty() == false) {
					
				
				double value = Double.parseDouble(text);

				// the factor applied during the conversion
				double factor = 0;

				// the offset applied during the conversion.
				double offset = 0;

				// Setup the correct factor/offset values depending on required conversion
				switch (combo.getSelectedIndex()) {

				case 0: // inches/cm
					factor = 2.54;
					break;
					
				case 1: // Pounds/Kilograms
					factor = 0.453592;
					break;
					
				case 2: // Degrees/Radians
					factor = 0.0174533;
					break;
					
				case 3: // Acres/Hectares
					factor = 0.404686;
					break;
					
				case 4: // Miles/Kilometres
					factor = 1.60934;
					break;
					
				case 5: // Yards/Metres
					factor = 0.9144;
					break;
					
				case 6: // Celsius/Fahrenheit
					factor = (factor*1.8)+32;
					break;
				}
							
				double result = factor * value + offset;
                String finalResult= new DecimalFormat("#.##").format(result);
				label.setText(finalResult);

                counter.setText("Conversion Count: " + Integer.toString(++count));
			}
			
		
			if (reverseCheckBox.isSelected()) {
				if(text.isEmpty() == false) {
					double value = Double.parseDouble(text);

					// the factor applied during the conversion
					double factor = 0;

					// the offset applied during the conversion.
					double offset = 0;

					// Setup the correct factor/offset values depending on required conversion
					switch (combo.getSelectedIndex()) {

					case 0: // cm/inches
						factor = 0.393701;
						break;
					
					case 1: // kilogram/pound
						factor = 2.20462;
						break;
						
					case 2: //Radians/Degrees
						factor= 57.2958;
						break;
						
					case 3: //Hectares/Acres
						factor = 2.47105;
						break;
					
					case 4: //Kilometers/Miles
						factor = 0.621371;
						break;
						
					case 5: //Meters/Yards
						factor = 1.09361;
						break;
						
					case 6: //Fahreinheit/Celsius
						factor = (value-32)*0.556;
						break;
						
				}
                     //to show final output
					double result = factor * value + offset;
					String s = new DecimalFormat("#.##").format(result);  //shows only two decimal places
					label.setText(s);
				}
			}
			
			}

			catch(NumberFormatException ex) //shows invalid message if invalid input is given
			{
				JOptionPane.showMessageDialog(null, "Enter a valid number please");
			}
		}
	}
  }
