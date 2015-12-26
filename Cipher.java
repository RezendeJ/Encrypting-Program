import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Cipher {

	private JFrame frame;
	private JLabel alphaName;
	private JLabel fileName;
	private String aName = "";
	private String fName = "";
	private ArrayList<String> alpha;
	private ArrayList<ArrayList<String>> msg;

	public static void main(String[] args) {
		Cipher c = new Cipher();
		c.go();
	}

	public void go(){
		frame = new JFrame("Crypting Program");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new BorderLayout());
		JButton alphab = new JButton("Alpha");
		alphab.addActionListener(new alphaListener());
		JButton fileb = new JButton("Choose File");
		fileb.addActionListener(new fileListener());
		JLabel numCall = new JLabel("Evolution Time");
		JLabel modeCall = new JLabel("Mode");
		JTextField evoNum = new JTextField(10);
		alphaName = new JLabel(aName);
		fileName = new JLabel(fName);
		JRadioButton encMode = new JRadioButton("Encrypt");
		JRadioButton decMode = new JRadioButton("Decrypt");
		JButton run = new JButton("Run");

		JPanel panel1 = new JPanel();
		BoxLayout layout = new BoxLayout(panel1, BoxLayout.Y_AXIS);
		panel1.setLayout(layout);
		frame.setContentPane(panel);
		
		JPanel alphaPan = new JPanel();
		alphaPan.add(alphab);
		alphaPan.add(alphaName);

		JPanel numPan = new JPanel();
		numPan.add(numCall);
		numPan.add(evoNum);

		JPanel modePan = new JPanel();
		modePan.add(modeCall);
		modePan.add(encMode);
		modePan.add(decMode);

		JPanel filePan = new JPanel();
		filePan.add(fileb);
		filePan.add(fileName);

		panel1.add(alphaPan);
		panel1.add(numPan);
		panel1.add(modePan);
		panel1.add(filePan);
		panel1.add(run);

		panel.add(panel1);
		
		frame.setVisible(true);
		frame.setSize(400,400);
	}

	public class alphaListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			alpha = chooseAlpha();
			System.out.println(alpha);
		}
	}

	class fileListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			msg = chooseFile();
			System.out.println(msg);
		}
	}

	private ArrayList<String> chooseAlpha() {
		JFileChooser fileOpen = new JFileChooser( );
		ArrayList<String> alpha;
		fileOpen.showOpenDialog(frame);
		aName = fileOpen.getSelectedFile().getAbsolutePath();
		alphaName.setText(aName);
		Object[] lines = loadText(fileOpen.getSelectedFile());
		alpha = sepChars(lines[0]);
		return alpha;
	}

	private Object[] loadText(File file){
		String line = "";
		Object[] letters = new Object[0];
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			letters = reader.lines().toArray();
			reader.close();
			
		} catch(Exception ex) {
			System.out.println("There is no such file.");
			ex.printStackTrace();
		}
		return letters;
	}

	private ArrayList<String> sepChars(Object ob){
		ArrayList<String> chars = new ArrayList<String>();
		String line = (String) ob;
		chars.addAll(Arrays.asList(line.trim().split("")));
		return chars; 
	}

	private ArrayList<ArrayList<String>> chooseFile(){
		JFileChooser fileOpen = new JFileChooser( );
		ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
		fileOpen.showOpenDialog(frame);
		fName = fileOpen.getSelectedFile().getAbsolutePath();
		fileName.setText(fName);
		Object[] lines = loadText(fileOpen.getSelectedFile());
		for (Object ob:lines){
			msg.add(sepChars(ob));
		}
		return msg;
	}
}