// CArtAgO artifact code for project military_health

package tools;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CostsDisplay extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField sbpI;
	public JTextField spo2I;
	public JTextField hrI;
	public JTextField rrI;
	public JTextField tempI;
	public JTextField deathI;
	public JTextField shotI;
	public JTextField shockI;
	public JTextField heatI;
	public JTextField undercooledI;
	public JTextField respI;
	public JTextField impaired_mental_costsI;
	public JTextField unconsciousness_costsI;
	

	public JTextField catSensorI, catDiagnosedI, catCumulativeI;
	
	public JButton doCountLikelyDeath;
	public JLabel doCountLikelyDeathStatusL,totalRescueCostsL;
	public JButton applyValues, resetValues;
	
	
	public JTextField rescueEquipmentCostI, rescuePersonnelCountI, rescuePersonnelCostI, teamMemberHumanLifeValueI;

	public CostsDisplay(String title, Double sbp, Double spo2, Double hr, Double rr, Double temp, 
			Double death, Double shot, Double shock, Double heat, Double undercooled, Double resp, Double impaired_mental_costs, Double unconsciousness_costs, 
			Double catSensor, Double catDiagnosed, Double catCumulative, Double rescueEquipmentCost, Double rescuePersonnelCount, Double rescuePersonnelCost, 
			Double teamMemberHumanLifeValue, Boolean doCountLikelyDeathStatus, Double totalRescueCosts) {
		
		setTitle(".:: "+title+" Rescue Costs-CONSOLE ::.");
		setSize(940,600);
		
		JPanel masterpanel = new JPanel();
		setContentPane(masterpanel);
		//masterpanel.setLayout(new BoxLayout(masterpanel,BoxLayout.X_AXIS));
		masterpanel.setLayout(new GridLayout(1, 2, 1, 1) );
		JPanel leftpanel = new JPanel();
		leftpanel.setLayout(new BoxLayout(leftpanel,BoxLayout.Y_AXIS));
		masterpanel.add(leftpanel);
		JPanel rightpanel = new JPanel();
		rightpanel.setLayout(new BoxLayout(rightpanel,BoxLayout.Y_AXIS));
		masterpanel.add(rightpanel);
		
		/* leftpanel items */
		JPanel p0 = new JPanel();
		p0.add(new JLabel("SensorStatusWeights: Weight= ( 1 * Value ) "));
		leftpanel.add(p0);
		
		JPanel p1 = new JPanel();
		p1.add(new JLabel(" sbp: "));
		sbpI = new JTextField(4);
		sbpI.setText(""+sbp);
		p1.add(sbpI);
		leftpanel.add(p1);
		
		JPanel p2 = new JPanel();
		p2.add(new JLabel(" spo2: "));
		spo2I = new JTextField(4);
		spo2I.setText(""+spo2);
		p2.add(spo2I);
		leftpanel.add(p2);
		
		JPanel p3 = new JPanel();
		p3.add(new JLabel(" hr: "));
		hrI = new JTextField(4);
		hrI.setText(""+hr);
		p3.add(hrI);
		leftpanel.add(p3);
		
		JPanel p4 = new JPanel();
		p4.add(new JLabel(" rr: "));
		rrI = new JTextField(4);
		rrI.setText(""+rr);
		p4.add(rrI);
		leftpanel.add(p4);
		
		JPanel p5 = new JPanel();
		p5.add(new JLabel(" temp: "));
		tempI = new JTextField(4);
		tempI.setText(""+temp);
		p5.add(tempI);
		leftpanel.add(p5);
		
		JPanel pthreshdiv = new JPanel();
		pthreshdiv.add(new JLabel("Category Thresholds"));
		leftpanel.add(pthreshdiv);

		/* category thresholds */
		JPanel p15 = new JPanel();
		p15.add(new JLabel(" Cat.Sensor Th.: "));
		catSensorI = new JTextField(4);
		catSensorI.setText(""+catSensor);
		p15.add(catSensorI);
		leftpanel.add(p15);
		
		JPanel p16 = new JPanel();
		p16.add(new JLabel(" Cat.Diagnosed Th.: "));
		catDiagnosedI = new JTextField(4);
		catDiagnosedI.setText(""+catDiagnosed);
		p16.add(catDiagnosedI);
		leftpanel.add(p16);
		
		JPanel pcumudiv = new JPanel();
		pcumudiv.add(new JLabel("<html><p>If a Summed Category Threshold is reached, <br> a score of \"1.0\" is appointed</html>"));
		leftpanel.add(pcumudiv);
		JPanel pcumuexp = new JPanel();
		pcumuexp.add(new JLabel("<html><p> the Cumulative catSensorScore + catDiag must <br>  be greater: than = combined Threshold</html>"));
		leftpanel.add(pcumuexp);
		JPanel pcumuexp2 = new JPanel();
		pcumuexp2.add(new JLabel("<html><p> This then is a +1 count in the Team Report <br> for the viable_Persons_to_Rescue</html>"));
		leftpanel.add(pcumuexp2);
		
		JPanel p17 = new JPanel();
		p17.add(new JLabel("<html> Cat.Cumulative Th.: <br> (e.g. 2.0 for required both Categories above Threshold Values)</html>"));
		catCumulativeI = new JTextField(4);
		catCumulativeI.setText(""+catCumulative);
		p17.add(catCumulativeI);
		leftpanel.add(p17);
		
		JPanel pCountDeath = new JPanel();
		doCountLikelyDeath = new JButton("Subtract likelyDead from viableRescueCount");
		pCountDeath.add(doCountLikelyDeath);
		doCountLikelyDeathStatusL = new JLabel("subtract enabled: "+doCountLikelyDeathStatus);
		pCountDeath.add(doCountLikelyDeathStatusL);
		leftpanel.add(pCountDeath);
		
		JPanel buttons = new JPanel();
		applyValues = new JButton("Apply Values");
		resetValues = new JButton("Reset Values");
		buttons.add(applyValues);
		buttons.add(resetValues);
		leftpanel.add(buttons);

		/* rightpanel items */
		//Double death, Double shot, Double shock, Double heat, Double undercooled, Double resp, Double impaired_mental_costs, Double unconsciousness_costs, 
		
		JPanel prightdescription = new JPanel();
		prightdescription.add(new JLabel("<html><p>Diagnosed Condition Weights: Weight= ( 1 * Value )"));
		rightpanel.add(prightdescription);
		
		JPanel p6 = new JPanel();
		p6.add(new JLabel(" death: "));
		deathI = new JTextField(4);
		deathI.setText(""+death);
		p6.add(deathI);
		rightpanel.add(p6);
		
		JPanel p7 = new JPanel();
		p7.add(new JLabel(" shot: "));
		shotI = new JTextField(4);
		shotI.setText(""+shot);
		p7.add(shotI);
		rightpanel.add(p7);
		
		JPanel p8 = new JPanel();
		p8.add(new JLabel(" shock: "));
		shockI = new JTextField(4);
		shockI.setText(""+shock);
		p8.add(shockI);
		rightpanel.add(p8);
		
		JPanel p9 = new JPanel();
		p9.add(new JLabel(" heat: "));
		heatI = new JTextField(4);
		heatI.setText(""+heat);
		p9.add(heatI);
		rightpanel.add(p9);
		
		JPanel p10 = new JPanel();
		p10.add(new JLabel(" undercooled: "));
		undercooledI = new JTextField(4);
		undercooledI.setText(""+undercooled);
		p10.add(undercooledI);
		rightpanel.add(p10);
		
		JPanel p11 = new JPanel();
		p11.add(new JLabel(" resp: "));
		respI = new JTextField(4);
		respI.setText(""+resp);
		p11.add(respI);
		rightpanel.add(p11);
		
		JPanel p12 = new JPanel();
		p12.add(new JLabel(" impaired_mental_costs: "));
		impaired_mental_costsI = new JTextField(4);
		impaired_mental_costsI.setText(""+impaired_mental_costs);
		p12.add(impaired_mental_costsI);
		rightpanel.add(p12);
		
		JPanel p14 = new JPanel();
		p14.add(new JLabel(" unconsciousness_costs: "));
		unconsciousness_costsI = new JTextField(4);
		unconsciousness_costsI.setText(""+unconsciousness_costs);
		p14.add(unconsciousness_costsI);
		rightpanel.add(p14);
		
		JPanel costsdiv = new JPanel();
		costsdiv.add(new JLabel("Costs and Values"));
		rightpanel.add(costsdiv);
		
		JPanel p18 = new JPanel();
		p18.add(new JLabel(" rescueEquipmentCost: "));
		rescueEquipmentCostI = new JTextField(10);
		rescueEquipmentCostI.setText(""+rescueEquipmentCost);
		p18.add(rescueEquipmentCostI);
		rightpanel.add(p18);

		JPanel p19 = new JPanel();
		p19.add(new JLabel(" rescuePersonnelCount: "));
		rescuePersonnelCountI = new JTextField(10);
		rescuePersonnelCountI.setText(""+rescuePersonnelCount);
		p19.add(rescuePersonnelCountI);
		rightpanel.add(p19);
		
		JPanel p20 = new JPanel();
		p20.add(new JLabel(" rescuePersonnelCost: "));
		rescuePersonnelCostI = new JTextField(10);
		rescuePersonnelCostI.setText(""+rescuePersonnelCost);
		p20.add(rescuePersonnelCostI);
		rightpanel.add(p20);
		
		JPanel p21 = new JPanel();
		p21.add(new JLabel(" totalRescueCosts: "));
		totalRescueCostsL = new JLabel(""+totalRescueCosts);
		p21.add(totalRescueCostsL);
		rightpanel.add(p21);
		
		JPanel p22 = new JPanel();
		p22.add(new JLabel("teamMember HumanLifeValue: "));
		teamMemberHumanLifeValueI = new JTextField(10);
		teamMemberHumanLifeValueI.setText(""+teamMemberHumanLifeValue);
		p22.add(teamMemberHumanLifeValueI);
		rightpanel.add(p22);
		
		
	}

}

