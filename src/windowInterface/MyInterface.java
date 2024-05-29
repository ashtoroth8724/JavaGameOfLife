package windowInterface;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import backend.Simulator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

//added imports for loading jsons
import java.io.FileReader; 
import java.util.Iterator; 
import java.util.Map; 
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 


public class MyInterface extends JFrame {

	private static final long serialVersionUID = -6840815447618468846L;
	private JPanel contentPane;
	private JLabel stepLabel;
	private JLabel borderLabel;
	private JLabel speedLabel;
	private JPanelDraw panelDraw;
	private Simulator mySimu=null;
	private JSlider randSlider;
	private JSlider speedSlider;
	private JLabel clickLabel;

	/**
	 * Create the frame.
	 */
	public MyInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panelTop = new JPanel();
		contentPane.add(panelTop, BorderLayout.NORTH);

		JPanel panelRight = new JPanel();
		panelRight.setLayout(new GridLayout(12,1));
		contentPane.add(panelRight, BorderLayout.EAST);

		JButton btnStop = new JButton("Stop/Reset");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopSimu();
			}
		});
		panelTop.add(btnStop);

		JButton btnGo = new JButton("Start/Pause");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicButtonGo();
			}
		});
		panelTop.add(btnGo);

		JButton btnClickAct = new JButton("Toggle Click");
		btnClickAct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicButtonToggleClickAction();
			}
		});
		panelTop.add(btnClickAct);

		stepLabel = new JLabel("Step : X");
		panelTop.add(stepLabel);

		speedLabel = new JLabel("speed slider : ");
		panelTop.add(speedLabel);

		speedSlider = new JSlider();
		speedSlider.setValue(3);
		speedSlider.setMinimum(0);
		speedSlider.setMaximum(10);
		speedSlider.setOrientation(SwingConstants.HORIZONTAL);
		speedSlider.setPreferredSize(new Dimension(100,30));
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				changeSpeed();
			}
		});
		panelTop.add(speedSlider);

//		JButton btnSpeed = new JButton("Set Speed");
//		btnSpeed.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				clicButtonSpeed();
//			}
//		});
//		panelTop.add(btnSpeed);

		JButton btnLoad = new JButton("Load World");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicLoadFileButton();
			}
		});
		panelRight.add(btnLoad);

		JButton btnSave = new JButton("Save World");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicSaveToFileButton();
			}
		});
		panelRight.add(btnSave);
		

		JButton btnLoadRule = new JButton("Load Rule");
		btnLoadRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicLoadRuleFileButton();
			}
		});
		panelRight.add(btnLoadRule);
		
		JButton btnLoadAgents = new JButton("Load Agents");
		btnLoadAgents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicLoadAgentsFileButton();;
			}
		});
		panelRight.add(btnLoadAgents);

		JButton btnSaveAgents = new JButton("Save Agents");
		btnSaveAgents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicSaveAgentsToFileButton();
			}
		});
		panelRight.add(btnSaveAgents);

		JButton btnRandGen = new JButton("Random Field");
		btnRandGen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateRandomBoard();
			}
		});
		panelRight.add(btnRandGen);
		

		JLabel randLabel = new JLabel("random density slider :");
		panelRight.add(randLabel);
		
		randSlider = new JSlider();
		randSlider.setValue(50);
		randSlider.setMinimum(0);
		randSlider.setMaximum(100);
		randSlider.setPreferredSize(new Dimension(30,200));
		randSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				changeDansity();
			}
		});
		panelRight.add(randSlider);
		

		JButton btnBorder = new JButton("Toggle Border");
		btnBorder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clicButtonBorder();
			}
		});
		panelRight.add(btnBorder);

		panelDraw = new JPanelDraw(this);
		contentPane.add(panelDraw, BorderLayout.CENTER);
		
		instantiateSimu();

		borderLabel = new JLabel("border : X");
		panelRight.add(borderLabel);
		borderLabel.setText("border : " +
				(mySimu.isLoopingBorder()?"loop":"closed"));

		clickLabel = new JLabel("click : X");
		panelRight.add(clickLabel);
		clickLabel.setText("click : " + mySimu.clickActionName());
	}
	public void stopSimu() {
		mySimu.togglePause();
		mySimu = new Simulator(this);
		panelDraw.setSimu(mySimu);
		panelDraw.repaint();
		randSlider.setValue(50);
		speedSlider.setValue(30);
	}

	public void setStepBanner(String s) {
		stepLabel.setText(s);
	}

	public void setBorderBanner(String s) {
		borderLabel.setText(s);
	}

	public void setClickBanner(String s) {
		clickLabel.setText(s);
	}

	public JPanelDraw getPanelDessin() {
		return panelDraw;
	}
	
	public void instantiateSimu() {
		if(mySimu==null) {
			mySimu = new Simulator(this);
			panelDraw.setSimu(mySimu);
		}
	}

	public void clicButtonGo() {
		this.instantiateSimu();
		if(!mySimu.isAlive()) {
			mySimu.start();
		} else {
			mySimu.togglePause();
		}
	}

	public void clicButtonToggleClickAction() {
		if(mySimu != null) {
			mySimu.toggleClickAction();
			clickLabel.setText("click : " + mySimu.clickActionName());
		}
	}

	public void clicButtonBorder() {
		if(mySimu != null) {
			mySimu.toggleLoopingBorder();
			borderLabel.setText("border : " +
					(mySimu.isLoopingBorder()?"loop":"closed"));
		}
	}
	
	public void generateRandomBoard() {
		this.instantiateSimu();
		float chanceOfLife = ((float)randSlider.getValue())/((float)randSlider.getMaximum());
		mySimu.generateRandom(chanceOfLife);
		panelDraw.repaint();
	}
	
	public void changeSpeed() {
		if(mySimu != null) {
			int delay = (int)Math.pow(2, 10-speedSlider.getValue());
			mySimu.setLoopDelay(delay);
		} else {
			speedSlider.setValue(3);
		}
	}

	public void changeDansity() {
		if(mySimu != null) {
			double density = ((double)randSlider.getValue())/((double)randSlider.getMaximum());
			mySimu.setDansity(density);
		} else {
			randSlider.setValue(50);
		}
	}

	public void clicLoadFileButton() {
		Simulator loadedSim = new Simulator(this);
		String fileName=SelectFile();
		ArrayList<String> stringArray = new ArrayList<String>();
		if (fileName.length()>0) {
			try {
				BufferedReader fileContent = new BufferedReader(new FileReader(fileName));
				String line = fileContent.readLine();
				while (line != null) {
					stringArray.add(line);
					line = fileContent.readLine();
				}
				fileContent.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(mySimu != null) {
				mySimu.stopSimu();
				this.eraseLabels();
			}
			loadedSim.loadSaveState(stringArray);
			mySimu = loadedSim;
			panelDraw.setSimu(mySimu);
			this.repaint();
		}
	}

	public void clicLoadRuleFileButton() {
		String fileName=SelectFile();
		ArrayList<String> stringArray = new ArrayList<String>();
		if (fileName.length()>0) {
			try {
				mySimu.loadRule(fileName);
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			this.repaint();
		}
	}
	
	public void clicLoadAgentsFileButton() {
		String fileName=SelectFile();
		ArrayList<String> stringArray = new ArrayList<String>();
		if (fileName.length()>0) {
			try {
				BufferedReader fileContent = new BufferedReader(new FileReader(fileName));
				String line = fileContent.readLine();
				while (line != null) {
					stringArray.add(line);
					line = fileContent.readLine();
				}
				fileContent.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mySimu.loadAgents(stringArray);
			this.repaint();
		}
	}

	
	public void clicSaveToFileButton() {
		String fileName=SelectFile();
		if (fileName.length()>0) {
			ArrayList<String> content = mySimu.getSaveState();
			writeFile(fileName, (String[]) content.toArray());
		}
	}

	public void clicSaveAgentsToFileButton() {
		String fileName=SelectFile();
		if (fileName.length()>0) {
			ArrayList<String> content = mySimu.getAgentsSave();
			writeFile(fileName, (String[]) content.toArray());
		}
	}
	
	
	public String SelectFile() {
		String s;
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose a file");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(true);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			s=chooser.getSelectedFile().toString();
		} else {
			System.out.println("No Selection ");
			s="";
		}
		return s;
	} 
	
	public void writeFile(String fileName, String[] content) {
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(fileName);
			for (String row : content) {
			    csvWriter.append(row);
			    csvWriter.append("\n");
			}
			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update (int stepCount) {
		System.out.println("update called");
		this.setStepBanner("Step : "+ stepCount);
		this.repaint();
	}
	
	public void eraseLabels() {
		this.setStepBanner("Step : X");
		this.setBorderBanner("border : X");
		this.setClickBanner("click : X");
		speedSlider.setValue(3);
	}

}