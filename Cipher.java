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
	private ArrayList<Integer> deck;
	private ArrayList<String> alpha;
	private ArrayList<ArrayList<String>> msg;
	private JRadioButton encMode;
	private JRadioButton decMode;
	private JTextField evoNum;
	private File secret;

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
		evoNum = new JTextField(10);
		alphaName = new JLabel(aName);
		fileName = new JLabel(fName);
		encMode = new JRadioButton("Encrypt");
		decMode = new JRadioButton("Decrypt");
		encMode.addActionListener(new modeListener());
		decMode.addActionListener(new modeListener());
		encMode.setSelected(true);
		decMode.setSelected(!encMode.isSelected());
		JButton run = new JButton("Run");
		run.addActionListener(new runListener());

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

		frame.setSize(500,500);
		frame.setVisible(true);
	}

	class alphaListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			alpha = chooseAlpha();
			//System.out.println(alpha);
		}
	}

	class fileListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			msg = chooseFile();
			//System.out.println(msg);
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
		Object[] text = new Object[0];
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			text = reader.lines().toArray();
			reader.close();
			
		} catch(Exception ex) {
			System.out.println("There is no such file.");
			ex.printStackTrace();
		}
		return text;
	}

	private ArrayList<String> sepChars(Object ob){
		ArrayList<String> chars = new ArrayList<String>();
		String line = (String) ob;
		chars.addAll(Arrays.asList(line.split("")));
		return chars; 
	}

	private ArrayList<ArrayList<String>> chooseFile(){
		JFileChooser fileOpen = new JFileChooser( );
		ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
		fileOpen.showOpenDialog(frame);
		secret = fileOpen.getSelectedFile();
		fName = secret.getAbsolutePath();
		fileName.setText(fName);
		Object[] lines = loadText(fileOpen.getSelectedFile());
		for (Object ob:lines){
			msg.add(sepChars(ob));
		}
		return msg;
	}

	class modeListener implements ActionListener {
		public void actionPerformed (ActionEvent a){
			if (a.getSource() == encMode){
				decMode.setSelected(!encMode.isSelected());
			} if (a.getSource() == decMode){
				encMode.setSelected(!decMode.isSelected());
			}
		}
	}

	class runListener implements ActionListener {
		public void actionPerformed (ActionEvent a){
			deck = genDeck();
	//		try{
	//			File noSecret = new File("!.txt");
	//			BufferedWriter writer = new BufferedWriter(new FileWriter(noSecret));			
	//			for (int i = 0; i < msg.size(); i++){
	//				msg[i] = codify(msg[i]);
	//				writer.write(joinChars(msg[i]));
	//				if (i != (msg.size() - 1)){
	//					writer.write("\n");
	//				}
	//			}
	//			writer.close();
	//		} catch (Exception e){
	//			e.printStackTrace();
	//		}
	//		aName = "";
	//		fName = "";
	//		evoNum.setText("");
	//		evoNum.requestFocus();
	//		secret.delete();
		}
	}

	private ArrayList<Integer> genDeck(){
		ArrayList<Integer> deck = new ArrayList<Integer>();
		int m = alpha.size() + 2;
		for (int i = 1; i <= m; i++){
			deck.add(i);
		}
		System.out.println(deck);
		int n = Integer.parseInt(evoNum.getText());
		for (int i = 0; i < n; i++){
			evolve(deck);
		}
		System.out.println(deck);
		return deck;
	}

	private int evolve(ArrayList<Integer> list){ //get_next_keystream_value
		int bJ = max(list);
		int sJ = bJ - 1;
		int ks = bJ;
		//while (ks == bJ || ks == sJ){
			moveJ(list, sJ);
			moveJ(list, bJ);
			moveJ(list, bJ);
	//		tripleCut(list);
	//		topToBot(list);
	//		ks = getTopIndex(list);
		//}
		return ks;
	}

	private int max(ArrayList<Integer> list){
		int max = 0;
		for (int i = 0; i < (list.size()); i++){
			max = Math.max(list.get(i), max);
		}
		return max;
	}

	private void moveJ(ArrayList<Integer> list, int sJ){
		int i = list.indexOf(sJ);
		swap(list, i);
	}

	private void swap(ArrayList<Integer> list, int index){
		int guard = list.get(index);
		if (index == (list.size() - 1)){
			list.set(index, list.get(0));
			list.set(0, guard);
		} else {
			list.set(index,list.get(index + 1));
			list.set((index + 1), guard);
		}
	}

	//codify 

	//joinChars
}