import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.io.FileNotFoundException;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.UIManager;

public class frame extends JFrame {

	 private String[] list = { "Japanese yen(JPY)", "Euro(EUR)", "US Dollars(USD)", "Australian Dollars(AUD)", 
			"Canadian Dollars(CAD)", "South Korean Won(KRW)", "Thai Baht(THB)", "United Arab Emirates Dirham(AED)" };
	 private String [] symbol = {"¥", "€", "$", "A$", "C$", "₩", "฿", "د.إ"};
     private JComboBox<String> comboBox;
     private JPanel contentPane;
	 private JTextField textField;
	 private JTextField showResult;
	 private JCheckBox ReverseCheckBox;
	 private JLabel countShow;
	 private double [] Factors = new double [8];
	 private String [] Symbols = new String [8];
	 private String ResultSymbol;
	 private boolean CurrencyFromFile = false;
	 
	 int count=0;
	 
	 String [] testSymbols = {"Â¥", "â‚¬", "$", "A$", "C$", "â‚©", "฿", "Ø¯.Ø¥", "kr", "R"};
	 
	 /**
	  * Launch the application.
	  */
	 public static void main(String[] args) {
	 	EventQueue.invokeLater(new Runnable() {
	 		public void run() {
	 			try {
	 				frame frame = new frame();
	 				frame.setVisible(true);
	 			} catch (Exception e) {
	 				e.printStackTrace();
	 			}
	 		}
	 	});
	 }
	 
	/**
	 * Create the frame.
	 */
	 
	
	frame() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		ActionListener listener = new ConvertListener();
		
		JLabel lblNewLabel = new JLabel("To (Select Currency):");
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(302, 52, 137, 14);
		
	    comboBox = new JComboBox(list);
		comboBox.setBounds(302, 74, 226, 28);
		
		JButton Convert = new JButton("Convert");
		Convert.addActionListener(listener);
		Convert.setBounds(75, 147, 89, 23);
		
		JButton Reset = new JButton("Reset");
		Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(null);
				showResult.setText(null);
				count=0;
				countShow.setText("0");
			}
		});
		Reset.setBounds(174, 147, 89, 23);
		
		JLabel FromPound = new JLabel("From Pound:");
		FromPound.setBounds(21, 52, 85, 28);
				
		textField = new JTextField();
		textField.setBounds(144, 52, 137, 28);
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					Convert.doClick();
			}
		});
		
		JLabel convertedAmt = new JLabel("Converted amount:");
		convertedAmt.setBounds(21, 95, 114, 21);
		
		showResult = new JTextField();
		showResult.setBackground(Color.WHITE);
		showResult.setBounds(144, 91, 137, 28);
		showResult.setColumns(10);
		
	    ReverseCheckBox = new JCheckBox("ReverseCheckBox");
	    ReverseCheckBox.addActionListener(listener);
		ReverseCheckBox.setBounds(117, 186, 146, 23);
		
		JLabel counting = new JLabel("Conversion Count:");
		counting.setBounds(75, 231, 114, 14);
		
		countShow = new JLabel("----");
		countShow.setBounds(184, 231, 89, 14);
		
		panel.add(lblNewLabel);
		panel.add(comboBox);
		panel.add(Convert);
		panel.add(Reset);
		panel.add(FromPound);
		panel.add(textField);
		panel.add(convertedAmt);
		panel.add(showResult);
		panel.add(ReverseCheckBox);
		panel.add(counting);
		panel.add(countShow);			
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu File = new JMenu("File");
		JMenu Help = new JMenu("Help");
		
		menuBar.add(File);
		menuBar.add(Help);
		
		JMenuItem Load = new JMenuItem("Load");
		Load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	            JFileChooser jfc = new JFileChooser();

	            int userOption = jfc.showDialog(null, "Choose file");
	            jfc.setVisible(true);

	            if (userOption == JFileChooser.APPROVE_OPTION) {
	                File file = jfc.getSelectedFile();
	                loadFiles(file);
	                
	                int counterForInvalidData = 0;

	                for (int i = 0; i < comboBox.getItemCount(); i++) {
	                    if (comboBox.getItemAt(i).contains("Invalid data")) {
	                        counterForInvalidData++;
	                    }
	                }

	                if (comboBox.getItemCount() == counterForInvalidData) {

	                   String message = "Since none of the conversion data (conversion currencies, factors and symbols) " +
	                            "from the text file are valid,\nDo you want to use the default conversion data " +
	                            "(conversion currencies, factors and symbols)?";

	                    int userChoice = JOptionPane.showConfirmDialog(null, message,"NO VALID DATA", JOptionPane.YES_NO_OPTION);

	                    if (userChoice == JOptionPane.YES_OPTION) {
	                        comboBox.removeAllItems();
	                        for(String currency : list){
	                            comboBox.addItem(currency);
	                        }
	                        CurrencyFromFile = false;
	                    }else{
	                       
	                        CurrencyFromFile = true;
	                    }
	                }else {
	                    CurrencyFromFile = true;
	                }
	                textField.setText("");
	                showResult.setText("");
	            }
			}
		});
		
		
		JMenuItem Exit = new JMenuItem("Exit");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem About = new JMenuItem("About");
		About.addActionListener(e ->{
			JOptionPane.showMessageDialog(this, "This is the Best Conversion system till date\n@RimaPalli2020", "About", 1);
		});
		
		
		File.add(Load);
		File.add(Exit);
		Help.add(About);
		
		File.setToolTipText("File Expolorer");
		Help.setToolTipText("Get help");
		Load.setToolTipText("Click to load file");
		Exit.setToolTipText("Press to terminate");
		About.setToolTipText("About");
		Convert.setToolTipText("Press to get the result");
		Reset.setToolTipText("Clear Everything");
		
	    File.setIcon(new ImageIcon("C:/Users/Dell/Desktop/Currency Conversion(Rima Palli Sec-E, c7227229)/icon/file.png"));
	    Help.setIcon(new ImageIcon("C:/Users/Dell/Desktop/Currency Conversion(Rima Palli Sec-E, c7227229)/icon/help.png"));
	    Load.setIcon(new ImageIcon("C:/Users/Dell/Desktop/Currency Conversion(Rima Palli Sec-E, c7227229)/icon/load.png"));
		Exit.setIcon(new ImageIcon("C:/Users/Dell/Desktop/Currency Conversion(Rima Palli Sec-E, c7227229)/icon/exit.png"));
		About.setIcon(new ImageIcon("C:/Users/Dell/Desktop/Currency Conversion(Rima Palli Sec-E, c7227229)/icon/about.png"));
		
		Load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		About.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 354);
	}

	 void loadFiles(File userChoiceFile) {

	        try {

	            BufferedReader selectFromFile =
	                    new BufferedReader(new InputStreamReader(new FileInputStream(userChoiceFile), "UTF8"));

	            String line = selectFromFile.readLine();

	            int FactorsCount = 0;
	            int SymbolsCount = 0;

	            comboBox.removeAllItems();

	            while ( line != null ) {

	                String [] parts = line.split(",");

	                if (parts.length < 3) {
	                    JOptionPane.showMessageDialog(null, "Invalid number of data values!.There should be 3 values(currency, currency conversion factory and currency "
	                    		+ "symbol) in a line of the file!", "ERROR!", JOptionPane.ERROR_MESSAGE);
	                    comboBox.addItem("Invalid data ");
	                    Factors[FactorsCount] = 0.0;
	                    Symbols[SymbolsCount] = "Invalid";
	                    FactorsCount++;
	                    SymbolsCount++;
	                }else {
	                 
	                    for(int i = 0; i < parts.length; i++){
	                        if (i == 0) {
	                           
	                            comboBox.addItem(parts[i].trim());
	                        }else if (i == 1){
	                       
	                            try{
	                                Factors[FactorsCount] = Double.parseDouble(parts[i].trim());
	                                FactorsCount++;
	                            }catch (Exception e){
	                                JOptionPane.showMessageDialog(null,
	                                        "Invalid value is found in the File\n" + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
	                           
	                                comboBox.removeItemAt(comboBox.getItemCount() - 1);
	                                comboBox.addItem("Invalid data");
	                                Factors[FactorsCount] = 0.0;
	                                Symbols[SymbolsCount] = "Invalid";
	                               FactorsCount++;
	                               SymbolsCount++;

	                                break;
	                            }
	                        }else {

	                            String fileSymbol = parts[i].trim();

	                       
	                            boolean symbolDoesExist = false;
	                            for(String symbol : testSymbols){
	                                if (fileSymbol.equals(symbol)){
	                                    Symbols[SymbolsCount] = fileSymbol;
	                                    SymbolsCount++;
	                                    symbolDoesExist = true;
	                                }
	                            }
	                            if (!symbolDoesExist){
	                                JOptionPane.showMessageDialog(null, "Invalid currency symbol is found! Invalid symbol: \"" + fileSymbol , "ERROR!",
	                                		JOptionPane.ERROR_MESSAGE);
	                                comboBox.removeItemAt(comboBox.getItemCount() - 1);
	                                comboBox.addItem("Invalid data");
	                               
	                               Factors[FactorsCount - 1] = 0.0;
	                               Symbols[SymbolsCount] = "Invalid";
	                               FactorsCount++;
	                               SymbolsCount++;
	                            }
	                        }
	                    }
	                }
	                line = selectFromFile.readLine();
	            }
	  
	            selectFromFile.close();

	        } catch (Exception e) {

	            String errorMessage = e.getMessage();
	            JOptionPane.showMessageDialog(null, errorMessage, "ERROR!", JOptionPane.ERROR_MESSAGE);
	        }

	    }
	
	 private double getTxtFileFactors(int indexPosition, boolean isChecked){

	        double factor = 0;

	        if (isChecked){
	            ResultSymbol = "£";
	        }else {
	            ResultSymbol = Symbols[indexPosition];
	        }

	        switch(indexPosition){
	            case 0:
	                factor = Factors[0];
	                break;
	            case 1:
	                factor = Factors[1];
	                break;
	            case 2:
	                factor = Factors[2];
	                break;
	            case 3:
	                factor = Factors[3];
	                break;
	            case 4:
	                factor = Factors[4];
	                break;
	            case 5:
	                factor = Factors[5];
	                break;
	            case 6:
	                factor = Factors[6];
	                break;
	            case 7:
	                factor = Factors[7];
	                break;
	        }
	        return factor;
	    }
	 
	 private double getCurrencyConversionFactor(int indexPosition, boolean isChecked){

	        double factor = 0;

	        switch(comboBox.getSelectedIndex()){
	        case 0: //japanese yen
				factor = 137.52;
				break;
				
			case 1: //euro
				factor = 1.09;
				break;
				
			case 2: //US dollars
				factor = 1.29;
				break;
				
			case 3: // Australian Dollars
				factor = 1.78;
				break;
				
			case 4: // Canadian Dollars
				factor = 1.70;
				break;
				
			case 5: // South Korean won
				factor = 1537.75;
				break;
				
			case 6: // thai baht
				factor = 40.52;
				break;
				
			case 7: // AED
				factor = 4.75;
				break;
	        }

	        if (isChecked){
	            ResultSymbol = "£";
	        }else {
	            ResultSymbol = symbol[indexPosition];
	        }

	        return factor;
	    }
		
		private class ConvertListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent event) {			
				String text = textField.getText().trim();
	
	            if (!text.isEmpty() && !ReverseCheckBox.isSelected()) {

	                double value = 0;
	   
	                try{
	                    value = Double.parseDouble(text.trim());  
	                }catch (Exception e){
	                    JOptionPane.showMessageDialog(null,
	                            "Please enter the valid number!", "REMAINDER!", JOptionPane.WARNING_MESSAGE);
	                    return;
	                }

	                double factor = CurrencyFromFile ? getTxtFileFactors(comboBox.getSelectedIndex(), false)
	                		: getCurrencyConversionFactor(comboBox.getSelectedIndex(), false);
	                double result;
	               
	                if (factor == 0.0) {
	                    JOptionPane.showMessageDialog(null, "Invalid Data can't be converted", "Invalid Data!", JOptionPane.WARNING_MESSAGE);
	                    
	                }else {
	                   
	                    result = value * factor;
	                    String finalResult= new DecimalFormat("#.##").format(result);
	                    showResult.setText(ResultSymbol + finalResult);
	                }
	                count++;
	                countShow.setText("" + count);
	            } 
	            else if (!text.isEmpty() && ReverseCheckBox.isSelected()){

	                double value = 0;
	               
	                try{
	                    value = Double.parseDouble(text.trim());
	                   
	                }catch (Exception e){
	                    JOptionPane.showMessageDialog(null,
	                            "Please enter valid number!", "REMAINDER!", JOptionPane.WARNING_MESSAGE);
	                    return;
	                }

	                double factor = CurrencyFromFile ? getTxtFileFactors(comboBox.getSelectedIndex(),
	                        true)
	                        : getCurrencyConversionFactor(comboBox.getSelectedIndex(), true);
	                double result;
	               
	                if (factor == 0.0) {
	                    JOptionPane.showMessageDialog(null, "Invalid Data can't be converted", "Invalid Data!", JOptionPane.WARNING_MESSAGE);
	                    
	                }else {
	                    
	                    result = value / factor;
	                    String finalResult= new DecimalFormat("#.##").format(result);
	                    showResult.setText(ResultSymbol + finalResult);
	                }
	                count++;
	                countShow.setText("" + count);

	            }else {
	               
	                JOptionPane.showMessageDialog(null,
	                        "You havenot entered any values", "REMAINDER!", JOptionPane.WARNING_MESSAGE);
	            }
						
			}
	  }
}
       


