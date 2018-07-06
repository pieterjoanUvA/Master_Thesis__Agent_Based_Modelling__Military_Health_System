// CArtAgO artifact code for project military_health

package tools;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SimulationDisplay extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton stage0Button, stage1Button, stage2Button, stage3Button, stage4Button;
	public JLabel	currentStageStatusdisplay;
	
	public SimulationDisplay(String title, Integer startStage) {
		setTitle(".:: "+title+" Simulation-CONSOLE ::.");
		setSize(480,140);
		JPanel panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		JPanel p0 = new JPanel();
		currentStageStatusdisplay = new JLabel("Current Active Stage : "+ startStage);
		p0.add(currentStageStatusdisplay);
		panel.add(p0);
		JPanel p1 = new JPanel();
		stage0Button = new JButton("Stage 0");
		stage1Button = new JButton("Stage 1");
		stage2Button = new JButton("Stage 2");
		stage3Button = new JButton("Stage 3");
		stage4Button = new JButton("Stage 4");
		p1.add(stage0Button);
		p1.add(stage1Button);
		p1.add(stage2Button);
		p1.add(stage3Button);
		p1.add(stage4Button);
		panel.add(p1);
		
		
	}

	
	
}

