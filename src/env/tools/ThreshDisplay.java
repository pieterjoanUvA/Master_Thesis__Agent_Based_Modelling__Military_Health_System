// CArtAgO artifact code for project military_health

package tools;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ThreshDisplay extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Measured symptoms threshold levels for Triage Flags
	public JTextField sbp_lower_th_highriskI, sbp_lower_th_riskI, sbp_high_th_riskI, sbp_high_th_highriskI;
	public JTextField spo2_lower_th_highriskI, spo2_lower_th_riskI;
	public JTextField hr_lower_th_highriskI, hr_lower_th_riskI, hr_high_th_riskI, hr_high_th_highriskI;	
	public JTextField rr_lower_th_highriskI, rr_high_th_highriskI;	
	public JTextField  temp_lower_th_highriskI, temp_high_th_riskI, temp_high_th_highriskI;	
	public JTextField deathTempI, deathSbpI, deathRrI, deathAccI, deathHrI;
	public JTextField shotSbpI, shotSdI;
	public JTextField shockSiI;
	public JTextField hyperthermiaI, hyperpyrexiaI;
	public JTextField hypothermiaI;
	public JTextField apneaI, bradypneaI, tachypnea_hyperventI;
	public JTextField impaired_mentalI, unconsciousnessI;

	public JButton setValues, cancelUpdate, setValuesAndPropagate;

	public ThreshDisplay(String title, 
			Double sbp_lower_th_highrisk ,Double sbp_lower_th_risk, Double sbp_high_th_risk, Double sbp_high_th_highrisk,
			Double spo2_lower_th_highrisk, Double spo2_lower_th_risk,
			Double hr_lower_th_highrisk, Double hr_lower_th_risk, Double hr_high_th_risk, Double hr_high_th_highrisk,
			Double rr_lower_th_highrisk, Double rr_high_th_highrisk,
			Double temp_lower_th_highrisk, Double temp_high_th_risk, Double temp_high_th_highrisk,
			Double deathTemp, Double deathSbp, Double deathRr, Double deathAcc, Double deathHr,
			Double shotSbp, Double shotSd,
			Double shockSi,
			Double hypothermia,
			Double hyperthermia, Double hyperpyrexia,
			Double apnea, Double bradypnea, Double tachypnea_hypervent,
			Double impaired_mental, Double unconsciousness) {
		setTitle(".:: "+title+" Threshold-CONSOLE ::.");
		setSize(940,500);
		
		JPanel panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JPanel p0 = new JPanel();
		p0.add(new JLabel("thresholds: "));
		setValues = new JButton("Set All Values/Apply Changes");
		p0.add(setValues);
		setValuesAndPropagate = new JButton("Set and Propagate");
		setValuesAndPropagate.setVisible(false);
		p0.add(setValuesAndPropagate);
		cancelUpdate = new JButton("Cancel Update All Values/Reset");
		p0.add(cancelUpdate);
		panel.add(p0);
		
		JPanel p1 = new JPanel();
		p1.add(new JLabel("sbp thresholds in mmHg :  lower_th_highrisk: "));
		sbp_lower_th_highriskI = new JTextField(4);
		sbp_lower_th_highriskI.setText(""+sbp_lower_th_highrisk);
		p1.add(sbp_lower_th_highriskI);

		
		p1.add(new JLabel(" lower_th_risk: "));
		sbp_lower_th_riskI = new JTextField(4);
		sbp_lower_th_riskI.setText(sbp_lower_th_risk+"");
		p1.add(sbp_lower_th_riskI);
		p1.add(new JLabel(" high_th_risk: "));
		sbp_high_th_riskI = new JTextField(4);
		sbp_high_th_riskI.setText(""+sbp_high_th_risk);
		p1.add(sbp_high_th_riskI);
		p1.add(new JLabel(" high_th_highrisk: "));
		sbp_high_th_highriskI = new JTextField(4);
		sbp_high_th_highriskI.setText(""+sbp_high_th_highrisk);
		p1.add(sbp_high_th_highriskI);
		panel.add(p1);
		
		
		//define spo2
		JPanel p2 = new JPanel();
		p2.add(new JLabel("spo2 thresholds in % :  lower_th_highrisk: "));
		spo2_lower_th_highriskI = new JTextField(4);
		spo2_lower_th_highriskI.setText(""+spo2_lower_th_highrisk);
		p2.add(spo2_lower_th_highriskI);
		p2.add(new JLabel(" lower_th_risk: "));
		spo2_lower_th_riskI = new JTextField(4);
		spo2_lower_th_riskI.setText(""+spo2_lower_th_risk);
		p2.add(spo2_lower_th_riskI);
		panel.add(p2);
		
		//define heart rate
		JPanel p3 = new JPanel();
		p3.add(new JLabel("hr thresholds in bpm :  lower_th_highrisk: "));
		hr_lower_th_highriskI = new JTextField(4);
		hr_lower_th_highriskI.setText(""+hr_lower_th_highrisk);
		p3.add(hr_lower_th_highriskI);
		p3.add(new JLabel(" lower_th_risk: "));
		hr_lower_th_riskI = new JTextField(4);
		hr_lower_th_riskI.setText(""+hr_lower_th_risk);
		p3.add(hr_lower_th_riskI);
		p3.add(new JLabel(" high_th_risk: "));
		hr_high_th_riskI = new JTextField(4);
		hr_high_th_riskI.setText(""+hr_high_th_risk);
		p3.add(hr_high_th_riskI);
		p3.add(new JLabel(" high_th_highrisk: "));
		hr_high_th_highriskI = new JTextField(4);
		hr_high_th_highriskI.setText(""+hr_high_th_highrisk);
		p3.add(hr_high_th_highriskI);
		panel.add(p3);
		
		//define respiratory rate
		JPanel p4 = new JPanel();
		p4.add(new JLabel("rr thresholds in mmHg:  lower_th_highrisk: "));
		rr_lower_th_highriskI = new JTextField(4);
		rr_lower_th_highriskI.setText(""+rr_lower_th_highrisk);
		p4.add(rr_lower_th_highriskI);
		p4.add(new JLabel(" high_th_highrisk: "));
		rr_high_th_highriskI = new JTextField(4);
		rr_high_th_highriskI.setText(""+rr_high_th_highrisk);
		p4.add(rr_high_th_highriskI);
		panel.add(p4);
		
		//define temp thresholds
		JPanel p5 = new JPanel();
		p5.add(new JLabel("temp thresholds in Celcius:  lower_th_highrisk: "));
		temp_lower_th_highriskI = new JTextField(4);
		temp_lower_th_highriskI.setText(""+temp_lower_th_highrisk);
		p5.add(temp_lower_th_highriskI);
		p5.add(new JLabel(" high_th_risk: "));
		temp_high_th_riskI = new JTextField(4);
		temp_high_th_riskI.setText(""+temp_high_th_risk);
		p5.add(temp_high_th_riskI);
		p5.add(new JLabel(" high_th_highrisk: "));
		temp_high_th_highriskI = new JTextField(4);
		temp_high_th_highriskI.setText(""+temp_high_th_highrisk);
		p5.add(temp_high_th_highriskI);
		panel.add(p5);
		
		//define symptoms with symptom independent thresholds, since deviation from triage flagging
		JPanel p6 = new JPanel();
		p6.add(new JLabel("likelyDeath:  hr < : "));
		deathHrI = new JTextField(4);
		deathHrI.setText(""+deathHr);
		p6.add(deathHrI);
		p6.add(new JLabel(" sbp < :"));
		deathSbpI = new JTextField(4);
		deathSbpI.setText(""+deathSbp);
		p6.add(deathSbpI);
		p6.add(new JLabel(" rr < :"));
		deathRrI = new JTextField(4);
		deathRrI.setText(""+deathRr);
		p6.add(deathRrI);
		p6.add(new JLabel(" temp < :"));
		deathTempI = new JTextField(4);
		deathTempI.setText(""+deathTemp);
		p6.add(deathTempI);
		p6.add(new JLabel(" Acc > :"));
		deathAccI = new JTextField(4);
		deathAccI.setText(""+deathAcc);
		p6.add(deathAccI);
		panel.add(p6);
		
		// shot and shock
		JPanel p7 = new JPanel();
		p7.add(new JLabel("Shot:  sbp < : "));
		shotSbpI = new JTextField(4);
		shotSbpI.setText(""+shotSbp);
		p7.add(shotSbpI);
		p7.add(new JLabel(" standard_dev : "));
		shotSdI = new JTextField(4);
		shotSdI.setText(""+shotSd);
		p7.add(shotSdI);
		panel.add(p7);
		JPanel p8 = new JPanel();
		p8.add(new JLabel("Shock: Shock Index (SI) > : "));
		shockSiI = new JTextField(4);
		shockSiI.setText(""+shockSi);
		p8.add(shockSiI);
		panel.add(p8);
		
		// temperature illnesses
		JPanel p9 = new JPanel();
		p9.add(new JLabel("Undercooled:  hypothermia < : "));
		hypothermiaI = new JTextField(4);
		hypothermiaI.setText(""+hypothermia);
		p9.add(hypothermiaI);
		panel.add(p9);
		JPanel p10 = new JPanel();
		p10.add(new JLabel("Heat Illness:  hyperthermia >= : "));
		hyperthermiaI = new JTextField(4);
		hyperthermiaI.setText(""+hyperthermia);
		p10.add(hyperthermiaI);
		p10.add(new JLabel(" hyperpyrexia >= : "));
		hyperpyrexiaI = new JTextField(4);
		hyperpyrexiaI.setText(""+hyperpyrexia);
		p10.add(hyperpyrexiaI);
		panel.add(p10);
		
		// respiratory troubles
		JPanel p11 = new JPanel();
		p11.add(new JLabel("Respiratory :  apnea rr <= : "));
		apneaI = new JTextField(4);
		apneaI.setText(""+apnea);
		p11.add(apneaI);
		p11.add(new JLabel(" bradypnea <= : "));
		bradypneaI = new JTextField(4);
		bradypneaI.setText(""+bradypnea);
		p11.add(bradypneaI);
		p11.add(new JLabel(" tachypnea/hyperventilation >= : "));
		tachypnea_hyperventI = new JTextField(4);
		tachypnea_hyperventI.setText(""+tachypnea_hypervent);
		p11.add(tachypnea_hyperventI);
		panel.add(p11);
		
		// mental functions and consciousness
		JPanel p12 = new JPanel();
		p12.add(new JLabel("Impaired mental function: spo2 <= : "));
		impaired_mentalI = new JTextField(4);
		impaired_mentalI.setText(""+impaired_mental);
		p12.add(impaired_mentalI);
		p12.add(new JLabel(" Un-Consciousness: spo2 <= : "));
		unconsciousnessI = new JTextField(4);
		unconsciousnessI.setText(""+unconsciousness);
		p12.add(unconsciousnessI);
		panel.add(p12);
	}

}

