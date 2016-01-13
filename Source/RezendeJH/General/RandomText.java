package RezendeJH.General;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
*An app that generates a text of random words
*based on a source text.
*/

public class RandomText {
	public static void main (String[] args) {
		RandomText.makeWindow(); //rt = new RandomText();
		//rt.go();
	}

	/**
	*Creates the app's windows
	*so it can run.
	*/

	private static void makeWindow() {
		Window w = new Window();
	}
}

class Window {

	JFrame frame;
	JLabel sourceName;
	String sourceAddress = "";
	JTextArea text;
	File source;
	Dict dictio;

	public Window() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);
		frame.setContentPane(mainPanel);

		JPanel botPanel = new JPanel();
		JPanel contextPanel = new JPanel();

		JButton sourceB = new JButton("Choose Source Text");
		sourceB.addActionListener(new chooseListener());
		sourceName = new JLabel(sourceAddress);

		JLabel contextL = new JLabel("Context");
		JSpinner spin = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
		contextPanel.add(contextL);
		contextPanel.add(spin);

		JButton addB = new JButton("Add");
		//addB.addActionListener(new addWordListener());

		JButton resetB = new JButton("Reset");
		//resetB.addActionListener(new resetListener());

		JButton saveB = new JButton("Save");
		//saveB.addActionListener(new saveListener());

		botPanel.add(addB);
		botPanel.add(resetB);
		botPanel.add(saveB);

		text = new JTextArea(25, 50);
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		sourceB.setAlignmentX(JButton.CENTER_ALIGNMENT);
		mainPanel.add(sourceB);
		sourceName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		mainPanel.add(sourceName);
		contextPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		mainPanel.add(contextPanel);
		scroll.setAlignmentX(JScrollPane.CENTER_ALIGNMENT);
		mainPanel.add(scroll);
		botPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		mainPanel.add(botPanel);

		frame.setSize(500, 500);
		frame.setVisible(true);
		}

	class chooseListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			chooseFile();
			TextLoader tL = new TextLoader(source);
			dictio = new Dict(tL);
			dictio.build(1);
			System.out.println(dictio.contextToWords);
		}
	}

	private void chooseFile() {
		JFileChooser fileOpen = new JFileChooser( );
		fileOpen.showOpenDialog(frame);
		source = fileOpen.getSelectedFile();
		sourceAddress = source.getAbsolutePath();
		sourceName.setText(sourceAddress);
	}


}

class TextLoader {

	private String text = "";

	public TextLoader(File source) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(source));
			Object[] lines = reader.lines().toArray();
			reader.close();
			for(int i = 0; i < lines.length; i++) {
				String line = (String) lines[i];
				text = text + line;
			}
			
		} catch(Exception ex) {
			System.out.println("There is no such file.");
			ex.printStackTrace();
		}
	}

	public String revealText() {
		return text;
	}
}

class Dict {

	HashMap<String, ArrayList<String>> contextToWords = new HashMap<String, ArrayList<String>>();
	ArrayList<String> words = new ArrayList<String>();

	public Dict(TextLoader txt) {
		Object[] words1 = txt.revealText().split(" ");
		for (Object w : words1) {
			words.add(((String) w).trim());
		}
		while (words.contains("")) {
			words.remove("");
		}
	}

	public void build(int contextSize) {
		
		for(int i = 0; i < words.size() - (contextSize - 1); i++) {
			String key = "";
			ArrayList<String> value = new ArrayList<String>();
			for(int j = 0; j < contextSize; j++) {
				key = key + words.get(i) + " ";
			}
			if(i !=  words.size() - contextSize) {
				value.add(words.get(i + contextSize));
			}
			if(contextToWords.containsKey(key)) {
				ArrayList<String> oldValue = contextToWords.get(key);
				oldValue.addAll(value);
				oldValue = null;
			} else {
				contextToWords.put(key, value);
			}
			key = null;
			value = null;
		}
	}

	public String[] getContexts() {
		Object[] protoContexts = contextToWords.keySet().toArray();
		String[] contexts = Arrays.copyOf(protoContexts, protoContexts.length, String[].class);
		return contexts;
	}
}

