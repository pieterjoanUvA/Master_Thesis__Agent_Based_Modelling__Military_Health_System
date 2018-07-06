// CArtAgO artifact code for project military_health

package tools;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.*;
import javax.swing.JTextPane;
import java.awt.*;
import java.awt.Color;

public class DiagnoseDisplay extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8257508384630296530L;
	public JTextArea textarea;
	public JTextPane textpane;
	public JScrollPane scrollpane;
	public JButton myButton, button;
	public JButton toggleRadioSilence, updateTeamReportJbutton, simulateCostsModelButton, closeSimulationButton;
	public JLabel radioSilenceStatus;
	public JLabel myLabel;
	public JList<String> myAgents;
	/* Modeled ComboBox for searching inside with indexOf() */
	SortedComboBoxModel<Object> model = new SortedComboBoxModel<Object>();
	public JComboBox<Object> myBox;
	public JTextField input;

	public JLabel age;
	// measured symptoms
	public JLabel sbp, sbprisk, sbpvalue, sbpflag;
	public JLabel spo2, spo2risk, spo2value, spo2flag;
	public JLabel hr, hrrisk, hrvalue, hrflag;
	public JLabel rr, rrrisk, rrvalue, rrflag;
	public JLabel temp, temprisk, tempvalue, tempflag;
	// diagnosed conditions where status is the diagnosed label of that condition
	public JLabel death, deathstatus, deathvalues, deathflag;
	public JLabel shot, shotstatus, shotvalues, shotflag;
	public JLabel shock, shockstatus, shockvalues, shockflag;
	public JLabel undercooled, undercooledstatus, undercooledvalues, undercooledflag;
	public JLabel heat, heatstatus, heatvalues, heatflag;
	public JLabel resp, respstatus, respvalues, respflag;
	public JLabel impaired_mental, impaired_mentalstatus, impaired_mentalvalues, impaired_mentalflag;
	public JLabel unconsciousness, unconsciousness_status, unconsciousnessvalues, unconsciousness_flag;

	public JLabel costDivider, costAction, diagnoseHeaderLabel;
	public JPanel p17, p18, p1, textpanel, p0;
	

	public DiagnoseDisplay(String title) {
		setTitle(".:: "+title+" Medical Diagnose-CONSOLE ::.");
		//setSize(1299,600);
		
		JPanel masterpanel = new JPanel();
		setContentPane(masterpanel);
		masterpanel.setLayout(new BoxLayout(masterpanel, BoxLayout.X_AXIS));;
		
		textpanel = new JPanel();
		textpanel.setLayout(new BoxLayout(textpanel,BoxLayout.Y_AXIS));
		textpanel.setPreferredSize(new Dimension(290, 200));
        masterpanel.add(textpanel);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		masterpanel.add(panel);

		myLabel = new JLabel();
		myLabel.setText("");
		myLabel.setVerticalAlignment(JLabel.TOP);

        JScrollPane scroller = new JScrollPane(myLabel);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textpanel.add(scroller);
        input = new JTextField(14);
        input.setText(" -= Agent Name =- ");
        button = new JButton("Update Agent Results");
        
        p0 = new JPanel();
        diagnoseHeaderLabel = new JLabel(" ");
        p0.add(diagnoseHeaderLabel);
        p0.setVisible(false);
        panel.add(p0);
        
        p1 = new JPanel();
        
        myBox  = new JComboBox<Object>( model );
        myBox.setMaximumRowCount(14);
        p1.add(myBox);
        age = new JLabel("age of diag");
        p1.add(button);        
        p1.add(input);
        p1.add(age);
        toggleRadioSilence = new JButton("RadioSilence On/Off");
        p1.add(toggleRadioSilence);
        radioSilenceStatus = new JLabel("false");
        p1.add(radioSilenceStatus);
        simulateCostsModelButton = new JButton("CostsSim.");
        p1.add(simulateCostsModelButton);
        simulateCostsModelButton.setVisible(false);

        panel.add(p1);
        
        JPanel pstatus = new JPanel();
        pstatus.setLayout(new GridLayout(1, 4, 1, 1) );
        JLabel kind = new JLabel("Kind of Diagnose");
        pstatus.add(kind);
        JLabel status = new JLabel("Diagnosed Status");
        pstatus.add(status);
        JLabel values = new JLabel("Measured Values");
        pstatus.add(values);
        JLabel tflag = new JLabel("Triage Flag Colour");
        pstatus.add(tflag);
        panel.add(pstatus);
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(1, 4, 1, 1) );
        sbp = new JLabel();
        sbp.setText("sbp: ");
        p2.add(sbp);
        sbprisk = new JLabel();
        sbprisk.setText("riskvalue NaN");
        p2.add(sbprisk);
        sbpvalue = new JLabel();
        sbpvalue.setText("value NaN");
        p2.add(sbpvalue);
        sbpflag = new JLabel();
        sbpflag.setText("Flag");
        p2.add(sbpflag);
        panel.add(p2);
        
        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(1, 4, 1, 1) );
        spo2 = new JLabel();
        spo2.setText("spo2: ");
        p3.add(spo2);
        spo2risk = new JLabel();
        spo2risk.setText("riskvalue NaN");
        p3.add(spo2risk);
        spo2value = new JLabel();
        spo2value.setText("value NaN");
        p3.add(spo2value);
        spo2flag = new JLabel();
        spo2flag.setText("Flag");
        p3.add(spo2flag);
        panel.add(p3);
        
        JPanel p4 = new JPanel();
        p4.setLayout(new GridLayout(1, 4, 1, 1) );
        hr = new JLabel();
        hr.setText("hr: ");
        p4.add(hr);
        hrrisk = new JLabel();
        hrrisk.setText("riskvalue NaN");
        p4.add(hrrisk);
        hrvalue = new JLabel();
        hrvalue.setText("value NaN");
        p4.add(hrvalue);
        hrflag = new JLabel();
        hrflag.setText("Flag");
        p4.add(hrflag);
        panel.add(p4);
        
        JPanel p5 = new JPanel();
        p5.setLayout(new GridLayout(1, 4, 1, 1) );
        rr = new JLabel();
        rr.setText("rr: ");
        p5.add(rr);
        rrrisk = new JLabel();
        rrrisk.setText("riskvalue NaN");
        p5.add(rrrisk);
        rrvalue = new JLabel();
        rrvalue.setText("value NaN");
        p5.add(rrvalue);
        rrflag = new JLabel();
        rrflag.setText("Flag");
        p5.add(rrflag);
        panel.add(p5);
        
        JPanel p6 = new JPanel();
        p6.setLayout(new GridLayout(1, 4, 1, 1) );
        temp = new JLabel();
        temp.setText("temp: ");
        p6.add(temp);
        temprisk = new JLabel();
        temprisk.setText("riskvalue NaN");
        p6.add(temprisk);
        tempvalue = new JLabel();
        tempvalue.setText("value NaN");
        p6.add(tempvalue);
        tempflag = new JLabel();
        tempflag.setText("Flag");
        p6.add(tempflag);
        panel.add(p6);

        JPanel p7 = new JPanel();
        p7.setLayout(new GridLayout(1, 4, 1, 1) );
        death = new JLabel();
        death.setText("dead: ");
        p7.add(death);
        deathstatus = new JLabel();
        deathstatus.setText("status");
        p7.add(deathstatus);
        deathvalues = new JLabel();
        deathvalues.setText("values string");
        p7.add(deathvalues);
        deathflag = new JLabel();
        deathflag.setText("Flag");
        p7.add(deathflag);
        panel.add(p7);
        
        JPanel p8 = new JPanel();
        p8.setLayout(new GridLayout(1, 4, 1, 1) );
        shot = new JLabel();
        shot.setText("shot: ");
        p8.add(shot);
        shotstatus = new JLabel();
        shotstatus.setText("status");
        p8.add(shotstatus);
        shotvalues = new JLabel();
        shotvalues.setText("values string");
        p8.add(shotvalues);
        shotflag = new JLabel();
        shotflag.setText("Flag");
        p8.add(shotflag);
        panel.add(p8);
        		
        JPanel p9 = new JPanel();
        p9.setLayout(new GridLayout(1, 4, 1, 1) );
        shock = new JLabel();
        shock.setText("shock: ");
        p9.add(shock);
        shockstatus = new JLabel();
        shockstatus.setText("status");
        p9.add(shockstatus);
        shockvalues = new JLabel();
        shockvalues.setText("values string");
        p9.add(shockvalues);
        shockflag = new JLabel();
        shockflag.setText("Flag");
        p9.add(shockflag);
        panel.add(p9);

        JPanel p11 = new JPanel();
        p11.setLayout(new GridLayout(1, 4, 1, 1) );
        undercooled = new JLabel();
        undercooled.setText("undercooled: ");
        p11.add(undercooled);
        undercooledstatus = new JLabel();
        undercooledstatus.setText("status");
        p11.add(undercooledstatus);
        undercooledvalues = new JLabel();
        undercooledvalues.setText("values string");
        p11.add(undercooledvalues);
        undercooledflag = new JLabel();
        undercooledflag.setText("Flag");
        p11.add(undercooledflag);
        panel.add(p11);
        
        JPanel p12 = new JPanel();
        p12.setLayout(new GridLayout(1, 4, 1, 1) );
        heat = new JLabel();
        heat.setText("heat: ");
        p12.add(heat);
        heatstatus = new JLabel();
        heatstatus.setText("status");
        p12.add(heatstatus);
        heatvalues = new JLabel();
        heatvalues.setText("values string");
        p12.add(heatvalues);
        heatflag = new JLabel();
        heatflag.setText("Flag");
        p12.add(heatflag);
        panel.add(p12);

        JPanel p14 = new JPanel();
        p14.setLayout(new GridLayout(1, 4, 1, 1) );
        resp = new JLabel();
        resp.setText("resp: ");
        p14.add(resp);
        respstatus = new JLabel();
        respstatus.setText("status");
        p14.add(respstatus);
        respvalues = new JLabel();
        respvalues.setText("values string");
        p14.add(respvalues);
        respflag = new JLabel();
        respflag.setText("Flag");
        p14.add(respflag);
        panel.add(p14);
        
        JPanel p15 = new JPanel();
        p15.setLayout(new GridLayout(1, 4, 1, 1) );
        impaired_mental = new JLabel();
        impaired_mental.setText("impaired_mental: ");
        p15.add(impaired_mental);
        impaired_mentalstatus = new JLabel();
        impaired_mentalstatus.setText("status");
        p15.add(impaired_mentalstatus);
        impaired_mentalvalues = new JLabel();
        impaired_mentalvalues.setText("values string");
        p15.add(impaired_mentalvalues);
        impaired_mentalflag = new JLabel();
        impaired_mentalflag.setText("Flag");
        p15.add(impaired_mentalflag);
        panel.add(p15);
        
        JPanel p16 = new JPanel();
        p16.setLayout(new GridLayout(1, 4, 1, 1) );
        unconsciousness = new JLabel();
        unconsciousness.setText("unconsciousness: ");
        p16.add(unconsciousness);
        unconsciousness_status = new JLabel();
        unconsciousness_status.setText("status");
        p16.add(unconsciousness_status);
        unconsciousnessvalues = new JLabel();
        unconsciousnessvalues.setText("values string");
        p16.add(unconsciousnessvalues);
        unconsciousness_flag = new JLabel();
        unconsciousness_flag.setText("Flag");
        p16.add(unconsciousness_flag);
        panel.add(p16);
        
        p17 = new JPanel();
        p17.setLayout(new BoxLayout(p17,BoxLayout.X_AXIS));


        p17.setVisible(false);

        updateTeamReportJbutton = new JButton("UpdateTeamReport");
        p17.add(updateTeamReportJbutton);
        p17.setBorder(BorderFactory.createLineBorder(Color.black));
        costAction = new JLabel(""
        		+ "<html><p> <b> Rescue Counsel : </b></p>"
        		+ "<p style=\"color: green; font-size:24\"><b>!!!! No Calculation of Rescue Thresholds has been performed yet !!!!</b></p>"
        		+ "Setting Subtract <i>Likely Dead </i> from Rescue Count: Status: <b>"+ "--" + "</b><br>"
        		+ "Amount of <i>Likely Dead and RescueThresholds not reached</i> :<b> " + "--" + "</b><br>"
        		+ "Amount of <i> Likely Dead and reached Rescue Thresholds</i> : <b> " + "--" + "</b><br>"
        		+ "Amount of <i> Rescue Thresholds reached </i>:  <b> " + "--" +"</b><br><br>"
        		+ "<p style=\"color: green; font-size:24\">Final Viable Rescue Count : "+ "--" +"</p>"
        		+ " </html>");
        p17.add(costAction);

        panel.add(p17);
	}

}
