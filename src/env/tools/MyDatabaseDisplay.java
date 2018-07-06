// CArtAgO artifact code for project military_health

package tools;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;

public class MyDatabaseDisplay extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton startDb, stopDb, flushDb, startDiagnoseGui;
	SortedComboBoxModel<Object> modelTeam = new SortedComboBoxModel<Object>(); 
	SortedComboBoxModel<Object> modelMission = new SortedComboBoxModel<Object>(); 
	SortedComboBoxModel<Object> modelAgent = new SortedComboBoxModel<Object>(); 
	SortedComboBoxModel<Object> modelTimeStamp = new SortedComboBoxModel<Object>(); 
	public JComboBox<Object> myTeamBox, myMissionBox, myAgentBox, myTimeStampBox;
	public JButton getTeams, getMissions, getAgents, getTimeStamps, getResults;
	public JLabel dbStatusLabel, dbConnLabel;
	
	
	public MyDatabaseDisplay(String title) {
		setTitle(".:: "+title+" Medical Diagnose-CONSOLE ::.");
		setSize(640,200);
		
		JPanel panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JPanel p0 = new JPanel();
		startDb = new JButton("Start Db");
		stopDb = new JButton("Stop Db");
		flushDb = new JButton("Flush Db");
		startDiagnoseGui = new JButton("startGui");
		p0.add(startDb);
		p0.add(stopDb);
		p0.add(flushDb);
		p0.add(startDiagnoseGui);
		
		panel.add(p0);
		
		JPanel pbutton = new JPanel();
		getTeams = new JButton("Get Teams");
		getMissions = new JButton("Get Missions");
		getAgents = new JButton("Get Agents");
		getTimeStamps = new JButton("Get Timestamps");
		getResults = new JButton("Get Results");
		pbutton.add(getTeams);
		pbutton.add(getMissions);
		pbutton.add(getAgents);
		pbutton.add(getTimeStamps);
		pbutton.add(getResults);
		
		panel.add(pbutton);
		
		JPanel p1 = new JPanel();
        myTeamBox  = new JComboBox<Object>( modelTeam );
        myTeamBox.setMaximumRowCount(14);
        p1.add(myTeamBox);
        myMissionBox  = new JComboBox<Object>( modelMission );
        myMissionBox.setMaximumRowCount(14);
        p1.add(myMissionBox);
        myAgentBox  = new JComboBox<Object>( modelAgent );
        myAgentBox.setMaximumRowCount(14);
        p1.add(myAgentBox);
        myTimeStampBox  = new JComboBox<Object>( modelTimeStamp );
        myTimeStampBox.setMaximumRowCount(14);
        p1.add(myTimeStampBox);
        
        panel.add(p1);
        
        JPanel p2 = new JPanel();
        dbStatusLabel = new JLabel("dbRunning: false ");
        dbConnLabel = new  JLabel("Connection: "+"No Connection");
        p2.add(dbStatusLabel);
        p2.add(dbConnLabel);
        
        panel.add(p2);
        
	}
	
}


