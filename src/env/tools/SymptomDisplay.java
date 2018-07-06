// CArtAgO artifact code for project military_health

package tools;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SymptomDisplay extends JFrame {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3781409839601241732L;
	public JButton bpr_under90, bpr_under85, bpr_above95;
	public JButton spo2_under90, spo2_under93, spo2_above95;
	public JButton br_0, br_under10, br_over29, br_over24;
	public JButton hr_0, hr_under60, hr60_140, hr_over140, hr_over160;
	public JButton temp_under28, temp_under36, temp_over40, temp_norm;
	public JButton move_min, move_intense, move_moderate;
	public JButton dead, shot, shock, hosp, reset; 
	// intensity and fatigue to be defined

	public JLabel sensorStatus, sensorHealth ,sensorReadings, sensorValue, calibrationValue; 
	
	public SymptomDisplay(String title) {
		setTitle(".:: "+title+" -CONSOLE ::.");
		setSize(480,300);
		
		JPanel panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		// sub_panel of 'panel' for sensor values + status
		JPanel p1 = new JPanel();
		bpr_under85 = new JButton("sbp_under85");
		p1.add(bpr_under85);
		bpr_under90 = new JButton("sbp_under90");
		p1.add(bpr_under90);
		bpr_above95 = new JButton("sbp_above95");
		p1.add(bpr_above95);
		panel.add(p1);
		
		// sub_panel of 'panel' for buttons
		JPanel p2 = new JPanel();		
		spo2_under90 = new JButton("spo2_under90");
		p2.add(spo2_under90);
		spo2_under93 = new JButton("spo2_under93");
		p2.add(spo2_under93);
		spo2_above95 = new JButton("spo2_above95");
		p2.add(spo2_above95);
		panel.add(p2);
		
		JPanel p3 = new JPanel();
		br_0 = new JButton("rr_0");
		p3.add(br_0);
		br_under10 = new JButton("rr_under10");
		p3.add(br_under10);
		br_over29 = new JButton("rr_over29");
		p3.add(br_over29);
		br_over24 = new JButton("rr_over24");
		p3.add(br_over24);
		panel.add(p3);
		
		JPanel p4 = new JPanel();	
		hr_0 = new JButton("hr_0");
		p4.add(hr_0);
		hr_under60 = new JButton("hr<60");
		p4.add(hr_under60);
		hr60_140 = new JButton("hr60-140");
		p4.add(hr60_140);
		hr_over140 = new JButton("hr>140");
		p4.add(hr_over140);
		hr_over160 = new JButton("hr>160");
		p4.add(hr_over160);
		panel.add(p4);
		
		JPanel p5 = new JPanel();
		temp_under28 = new JButton("temp<28");
		p5.add(temp_under28);
		temp_under36 = new JButton("temp<36");
		p5.add(temp_under36);
		temp_over40 = new JButton("temp>40");
		p5.add(temp_over40);
		temp_norm = new JButton("temp_norm");
		p5.add(temp_norm);
		panel.add(p5);
		
		JPanel p6 = new JPanel();
		move_min = new JButton("move_min");
		p6.add(move_min);
		move_intense = new JButton("move_intense");
		p6.add(move_intense);
		move_moderate = new JButton("move_moderate");
		p6.add(move_moderate);
		panel.add(p6);
		
		JPanel p7 = new JPanel();
		dead = new JButton("dead");
		p7.add(dead);
		shock = new JButton("shock");
		p7.add(shock);
		hosp = new JButton("hosp");
		p7.add(hosp);
		reset = new JButton("reset");
		p7.add(reset);
		shot = new JButton("shot");
		p7.add(shot);
		panel.add(p7);
	}
	// does a threaded update of field below for fast running instances ....
    public void updateNumMsgField(final int value){
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
         //       numMsg.setText(""+value);
            }
        });
    }
}

