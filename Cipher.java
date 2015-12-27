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
				for (int i = 0; i < msg.size(); i++){
					codify(msg.get(i), deck, alpha);
					System.out.println(msg.get(i));
	//				writer.write(joinChars(msg[i]));
	//				if (i != (msg.size() - 1)){
	//					writer.write("\n");
	//				}
				}
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
		//System.out.println(deck);
		int n = Integer.parseInt(evoNum.getText());
		for (int i = 0; i < n; i++){
			evolve(deck);
		}
		//System.out.println(deck);
		return deck;
	}

	private int evolve(ArrayList<Integer> list){
		int bJ = max(list);
		int sJ = bJ - 1;
		int ks = bJ;
		while (ks == bJ || ks == sJ){
			moveJ(list, sJ);
			moveJ(list, bJ);
			moveJ(list, bJ);
			tripleCut(list, sJ, bJ);
			//System.out.println(list);
			topToBot(list, bJ);
			//System.out.println(list);
			ks = getTopIndex(list, bJ);
		}
		return ks;
	}

	private int max(ArrayList<Integer> list){
		int max = 0;
		for (int i = 0; i < (list.size()); i++){
			max = Math.max(list.get(i), max);
		}
		return max;
	}

	private void moveJ(ArrayList<Integer> list, int j){
		int i = list.indexOf(j);
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

	private void tripleCut(ArrayList<Integer> list, int sJ, int bJ){
		int fJIndex = Math.min(list.indexOf(sJ), list.indexOf(bJ));
		int sJIndex = Math.max(list.indexOf(sJ), list.indexOf(bJ));
		ArrayList<Integer> cut1 = new ArrayList<Integer> (list.subList(0, fJIndex));
		ArrayList<Integer> cut2 = new ArrayList<Integer> (list.subList(fJIndex, (sJIndex + 1)));
		ArrayList<Integer> cut3;
		if (sJIndex != (list.size() - 1)){
			cut3 = new ArrayList<Integer> (list.subList((sJIndex + 1), list.size()));
		} else {
			cut3 = new ArrayList<Integer> (list.subList(sJIndex, sJIndex));
		}
		list.removeAll(cut1);
		list.removeAll(cut3);
		list.addAll(cut1);
		list.addAll(0, cut3);
	}

	private void topToBot(ArrayList<Integer> list, int bJ){
		int n = list.get(list.size() - 1);
		if (n == bJ){
			n--;
		}
		ArrayList<Integer> top = new ArrayList<Integer> (list.subList(0, n));
		list.removeAll(top);
		list.addAll((list.size() - 1), top);
	}

	private int getTopIndex(ArrayList<Integer> list, int bJ){
		int n = list.get(0);
		if (n == bJ){
			n--;
		}
		return list.get(n);
	}

	private void codify(ArrayList<String> line, ArrayList<Integer> deck, ArrayList<String> alpha){
		int ks;
		for (int i = 0; i < line.size(); i++){
			ks = evolve(deck);
			int n = alpha.indexOf(line.get(i));
			if (encMode.isSelected()){
				n = (n + ks) % alpha.size();
			} else {
				if((n - ks) >= 0){
					n = (n - ks) % alpha.size();
				} else {
					n = (alpha.size() + n - ks) % alpha.size();
				}
			}
			line.set(i, alpha.get(n)); 
		}
	}

	//joinChars
}