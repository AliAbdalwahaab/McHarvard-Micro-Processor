import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

public class GUI  {
	Processor processor;
	boolean parsed;
	String codeCopyForStep;
	public GUI(Processor processor) {
		initComponents();
		this.processor = processor;
		parsed = false;
	}
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		window = new JFrame();
		scrollPane1 = new JScrollPane();
		codeTextBox = new JTextArea();
		stepButton = new JButton();
		runButton = new JButton();
		label1 = new JLabel();
		scrollPane2 = new JScrollPane();
		registerTextBox = new JTextArea();
		label2 = new JLabel();
		scrollPane3 = new JScrollPane();
		pcTextBox = new JTextArea();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		scrollPane4 = new JScrollPane();
		dataMemoryTextbox = new JTextArea();
		textArea2 = new JTextArea();
		scrollPane5 = new JScrollPane();
		instructionMemoryTextbox = new JTextArea();
		scrollPane6 = new JScrollPane();
		sregTextBox = new JTextArea();
		scrollPane7 = new JScrollPane();
		logTextBox = new JTextArea();
		label6 = new JLabel();

		//======== window ========
		{
			Container windowContentPane = window.getContentPane();

			window.setTitle("McHarvard Assembly Simulator");

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(codeTextBox);
			}

			//---- stepButton ----
			stepButton.setText("Step");

			//---- runButton ----
			runButton.setText("Run");

			//---- label1 ----
			label1.setText("Register File");

			//======== scrollPane2 ========
			{

				//---- registerTextBox ----
				registerTextBox.setEditable(false);
				registerTextBox.setRows(64);
				scrollPane2.setViewportView(registerTextBox);
			}

			//---- label2 ----
			label2.setText("PC");

			//======== scrollPane3 ========
			{
				scrollPane3.setViewportView(pcTextBox);
			}

			//---- label3 ----
			label3.setText("SREG");

			//---- label4 ----
			label4.setText("Data Memory");

			//---- label5 ----
			label5.setText("Instruction Memory");

			//======== scrollPane4 ========
			{

				//---- dataMemoryTextbox ----
				dataMemoryTextbox.setRows(2048);
				scrollPane4.setViewportView(dataMemoryTextbox);
			}

			//======== scrollPane5 ========
			{

				//---- instructionMemoryTextbox ----
				instructionMemoryTextbox.setRows(1024);
				scrollPane5.setViewportView(instructionMemoryTextbox);
			}

			//======== scrollPane6 ========
			{
				scrollPane6.setViewportView(sregTextBox);
			}

			//======== scrollPane7 ========
			{
				scrollPane7.setViewportView(logTextBox);
			}

			//---- label6 ----
			label6.setText("Log");

			GroupLayout windowContentPaneLayout = new GroupLayout(windowContentPane);
			windowContentPane.setLayout(windowContentPaneLayout);
			windowContentPaneLayout.setHorizontalGroup(
				windowContentPaneLayout.createParallelGroup()
					.addGroup(windowContentPaneLayout.createSequentialGroup()
						.addGap(26, 26, 26)
						.addGroup(windowContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addGroup(windowContentPaneLayout.createSequentialGroup()
								.addComponent(runButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(stepButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE))
						.addGap(33, 33, 33)
						.addGroup(windowContentPaneLayout.createParallelGroup()
							.addGroup(windowContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(label1)
								.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
								.addComponent(scrollPane3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
								.addComponent(label2)
								.addComponent(label3)
								.addComponent(scrollPane6, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
								.addComponent(scrollPane7, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
							.addComponent(label6))
						.addGap(35, 35, 35)
						.addGroup(windowContentPaneLayout.createParallelGroup()
							.addComponent(scrollPane4)
							.addGroup(windowContentPaneLayout.createSequentialGroup()
								.addGroup(windowContentPaneLayout.createParallelGroup()
									.addComponent(label4)
									.addComponent(label5))
								.addGap(0, 288, Short.MAX_VALUE))
							.addComponent(scrollPane5, GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE))
						.addGap(39, 39, 39))
			);
			windowContentPaneLayout.setVerticalGroup(
				windowContentPaneLayout.createParallelGroup()
					.addGroup(windowContentPaneLayout.createSequentialGroup()
						.addGap(25, 25, 25)
						.addGroup(windowContentPaneLayout.createParallelGroup()
							.addGroup(windowContentPaneLayout.createSequentialGroup()
								.addGroup(windowContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label1)
									.addComponent(label4))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(windowContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addGroup(windowContentPaneLayout.createSequentialGroup()
										.addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(label5))
									.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
								.addGroup(windowContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
									.addGroup(GroupLayout.Alignment.LEADING, windowContentPaneLayout.createSequentialGroup()
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(scrollPane5, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
									.addGroup(windowContentPaneLayout.createSequentialGroup()
										.addGap(14, 14, 14)
										.addComponent(label2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(label3)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(scrollPane6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(label6)
										.addGap(2, 2, 2)
										.addComponent(scrollPane7, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))))
							.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(windowContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(stepButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addComponent(runButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGap(28, 28, 28))
			);
			window.setSize(1185, 775);
			window.setLocationRelativeTo(null);
			registerTextBox.setEditable(false);
			pcTextBox.setEditable(false);
			dataMemoryTextbox.setEditable(false);
			instructionMemoryTextbox.setEditable(false);
			sregTextBox.setEditable(false);
			logTextBox.setEditable(false);

			runButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Code to be executed when the button is clicke
					registerTextBox.setText("");
					pcTextBox.setText("");
					dataMemoryTextbox.setText("");
					instructionMemoryTextbox.setText("");
					sregTextBox.setText("");
					codeCopyForStep = codeTextBox.getText();
					if (codeCopyForStep == null || codeCopyForStep.length() == 0) {
						codeCopyForStep = codeTextBox.getText();
					}


					String code = codeCopyForStep;
					// write code in assembly.txt
					try {
						FileWriter fileWriter = new FileWriter("src/assembly.txt");
						fileWriter.write(code);
						fileWriter.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					try {
						parsed = false;
						processor.loadAssemblyAndParse();
						processor.executeProgram();
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}


					registerTextBox.setText(processor.registerFile.toString());
					instructionMemoryTextbox.setText(processor.instructionMemory.toString());
					pcTextBox.setText(processor.pc.guiText());
					sregTextBox.setText(processor.sreg.guiText());
					dataMemoryTextbox.setText(processor.dataMemory.toString());
					logTextBox.setText(processor.log);
					stepButton.setEnabled(true);
					processor.reset();
					codeTextBox.setEditable(true);
				}
			});

			stepButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Code to be executed when the button is clicked
					registerTextBox.setText("");
					pcTextBox.setText("");
					dataMemoryTextbox.setText("");
					instructionMemoryTextbox.setText("");
					sregTextBox.setText("");

					if (codeCopyForStep == null || codeCopyForStep.length() == 0) {
						codeCopyForStep = codeTextBox.getText();
					}
					if (!parsed) {
						try {
							FileWriter fileWriter = new FileWriter("src/assembly.txt");
							fileWriter.write(codeCopyForStep);
							fileWriter.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						try {
							processor.loadAssemblyAndParse();
						} catch (IOException ex) {
							throw new RuntimeException(ex);
						}
						parsed = true;
					}
					String code = new String(codeCopyForStep);

					// write code in assembly.txt
					try {
						FileWriter fileWriter = new FileWriter("src/assembly.txt");
						fileWriter.write(code);
						fileWriter.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}


					processor.step();
					String[] codeLines = code.split("\n");
					if (processor.pc.getAddress() - 1 < codeLines.length)
						codeLines[processor.pc.getAddress() - 1] = codeLines[processor.pc.getAddress() - 1] + " <<";
					String codeBack = "";
					for (String s: codeLines) codeBack += s + "\n";
					codeTextBox.setText(codeBack);
					codeTextBox.setEditable(false);
					if (processor.lastInstruction) {
						stepButton.setEnabled(false);
						codeTextBox.setEditable(true);
						codeTextBox.setText(codeCopyForStep);
					}


					registerTextBox.setText(processor.registerFile.toString());
					instructionMemoryTextbox.setText(processor.instructionMemory.toString());
					pcTextBox.setText(processor.pc.guiText());
					sregTextBox.setText(processor.sreg.guiText());
					dataMemoryTextbox.setText(processor.dataMemory.toString());
					logTextBox.setText(processor.log);
				}
			});
		}

	}

	private JFrame window;
	private JScrollPane scrollPane1;
	private JTextArea codeTextBox;
	private JButton stepButton;
	private JButton runButton;
	private JLabel label1;
	private JScrollPane scrollPane2;
	private JTextArea registerTextBox;
	private JLabel label2;
	private JScrollPane scrollPane3;
	private JTextArea pcTextBox;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JScrollPane scrollPane4;
	private JTextArea dataMemoryTextbox;
	private JTextArea textArea2;
	private JScrollPane scrollPane5;
	private JTextArea instructionMemoryTextbox;
	private JScrollPane scrollPane6;
	private JTextArea sregTextBox;
	private JScrollPane scrollPane7;
	private JTextArea logTextBox;
	private JLabel label6;

	public static void main(String[] args) {
		Processor processor = new Processor();
		GUI gui = new GUI(processor);
		gui.window.setVisible(true);
	}
}
