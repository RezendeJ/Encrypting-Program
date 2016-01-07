package RezendeJH;

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
	//File source;

	public Window() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);

		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);
		frame.setContentPane(mainPanel);

		JPanel botPanel = new JPanel();
		JPanel contextPanel = new JPanel();

		JButton sourceB = new JButton("Choose Source Text");
		//sourceB.addActionListener(new chooseListener());
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

		mainPanel.add(sourceB);
		mainPanel.add(sourceName);
		mainPanel.add(contextPanel);
		mainPanel.add(scroll);
		mainPanel.add(botPanel);

	}
}