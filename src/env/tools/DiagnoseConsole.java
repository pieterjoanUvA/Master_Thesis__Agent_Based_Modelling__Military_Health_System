// CArtAgO artifact code for project military_health

package tools;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Date;
import cartago.*;
import cartago.tools.*;

public class DiagnoseConsole extends GUIArtifact {
	/* List for add/remove short Agent_Flag_Age DiagnoseJLabel listing */
	List<AgentDiag> listAgentDiag = new ArrayList<AgentDiag>();
	
	/* Lists for Deferred Sending to Command Centre */
	// mostRecentAck list to be sent after end radio silence
	// then prune and flag on mostRecentAck listing
	List<Diagnosed> listDiagnosed = new ArrayList<Diagnosed>();
	//List<AgentDiag> listAgentFlag = new ArrayList<AgentDiag>();
	
	private DiagnoseDisplay display;
	boolean isSimulation = false;
	String selectedAgent;
	Object selectedAgentObject;
	boolean radioSilenceStatus = false;
	void init(String title, boolean ccagent, boolean visible, boolean isSimulation, boolean isDbAgent) {

		display = new DiagnoseDisplay(title);
		display.setSize(1299,400);
		display.setLocation(10, 240);
		linkActionEventToOp(display.button, "agentSelected");
		linkActionEventToOp(display.toggleRadioSilence, "toggleRadioSilence");
		linkActionEventToOp(display.updateTeamReportJbutton, "updateTeamReportButton");
		linkActionEventToOp(display.myBox, "agentSelected");
		linkActionEventToOp(display.simulateCostsModelButton, "simulateCostsModel");
		defineObsProperty("radioSilence", radioSilenceStatus);
		if ( ccagent == true) {
			display.p17.setVisible(true);
			display.simulateCostsModelButton.setVisible(true);
			display.setSize(1299, 600);
			display.setLocation(40, 400);
		}
		if (isSimulation == true) {
			display.simulateCostsModelButton.setVisible(false);
			display.setLocation(600, 400);
			display.setSize(1299, 500);
			display.radioSilenceStatus.setVisible(false);
			display.toggleRadioSilence.setVisible(false);
			display.button.setVisible(false);
		}
		if (isDbAgent == true) {
			display.p17.setVisible(false);
			display.p1.setVisible(false);
			display.p0.setVisible(true);
			display.textpanel.setVisible(false);
			display.setSize(1000,400);
			display.setLocation(680, 0);
		}
		
		display.setVisible(visible);
		this.init();
	}
	@INTERNAL_OPERATION void updateTeamReportButton(ActionEvent ev) {
		updateTeamReport();
	}
	@OPERATION void updateTeamReport() {
		signal("cmd", "doUpdateTeamReport");
	}
	@OPERATION void showGuiAgain() {
		display.setVisible(true);
	}
	@INTERNAL_OPERATION void toggleRadioSilence(ActionEvent ev) {
		Boolean bool = (Boolean) getObsProperty("radioSilence").getValue();
		if ( bool == false ) {
			bool = true;
			display.radioSilenceStatus.setText(bool.toString());
			getObsProperty("radioSilence").updateValue(bool);
		} else if ( bool == true ) {
			bool = false;
			display.radioSilenceStatus.setText(bool.toString());
			getObsProperty("radioSilence").updateValue(bool);
		}
		signal("cmd","setRadioSilence",bool);
	}
	@INTERNAL_OPERATION void simulateCostsModel(ActionEvent ev){
		signal("cmd","costs_sim");
	}
	@OPERATION void toggleStatusWithoutNotify(boolean bool) {
		Boolean mybool = bool;
		display.radioSilenceStatus.setText(mybool.toString());
		getObsProperty("radioSilence").updateValue(mybool);
	}
	
	@INTERNAL_OPERATION void agentSelected(ActionEvent ev){
		selectedAgent = display.myBox.getSelectedItem().toString();
		display.input.setText(selectedAgent);
		clearDiag();
		display.age.setText("-- seconds");
		this.signal("cmd", "getDiags", selectedAgent);
	}

	@OPERATION void saveMsg(String kind, String status, Object values, Object flag, String name,  String team, long timestamp) {
		String s_values = values.toString();
		String s_flag = flag.toString();
		listDiagnosed.add(new Diagnosed(kind,status,s_values,s_flag,name,team,timestamp));
	}
	
	@OPERATION void sendingMsg(boolean bool) {
		/* for preventing a ConcurrentModificationException */
		List<Diagnosed> templist = new ArrayList<Diagnosed>();
		for(Diagnosed diag : listDiagnosed) {
			String name = diag.getName();
			String kind = diag.getKind() ;
			Integer flag = Integer .parseInt(diag.getFlag());
			String status = diag.getStatus();
			String values = diag.getValues();
			String team = diag.getTeam();
			long timestamp = diag.getTimestamp();
			templist.add(diag);
			signal("cmd","sendToCC",kind,status,values,flag,name,team,timestamp);
		}
		listDiagnosed.removeAll(templist);
	}

	@OPERATION void printMsg(Object msg, String colour, long timestamp, long currtime){ 

    	String agentnames = msg.toString();
    	AgentDiag search = new AgentDiag(agentnames);
    	/* check if item already exists in JComboBox, otherwise add */
    	if(display.model.getIndexOf(msg) == -1) {
    		display.myBox.addItem( msg );
    	}
    	/* remove new sent agent_result from ArrayList<AgentDiag> (customModel) */
    	listAgentDiag.remove( search);
    	listAgentDiag.add(new AgentDiag(agentnames, colour, timestamp));
		Collections.sort(listAgentDiag, new AgentDiagChainedComparator(new AgentDiagNameComparator()) );
		String message = "";
		/* ArrayList Printing Loop */
		for (AgentDiag name : listAgentDiag) {
			message = message + name;
		}
		display.myLabel.setText(message);
    }

	@OPERATION void printDiagnoseHeader(String agent, long timestamp, String team, Object mission_i ) {
		Date date = new Date(timestamp);
		display.diagnoseHeaderLabel.setText("AgentName: "+agent+" TeamName: "+team+" MissionId: "+mission_i.toString()+" TimeStamp: "+timestamp+" Date: "+date);
	}
	
	@OPERATION void updateListTimes() {
		String message = "";
		/* ArrayList Printing Loop */
		for (AgentDiag name : listAgentDiag) {
			message = message + name;
		}
		display.myLabel.setText(message);
	}
	
    @OPERATION void printCounsel(Object rescueO,Object doCountDeathO,Object deathCount, Object rescueCount, Object thNotMetAndDeathCounted) {
    	String htmlstring = "";
    	Boolean rescue = (Boolean) rescueO;
    	Boolean doCountDeath = (Boolean) doCountDeathO;
    	Integer dc =  Integer .parseInt(deathCount.toString());
    	Integer rc = Integer .parseInt(rescueCount.toString());
    	Integer dt = Integer .parseInt(thNotMetAndDeathCounted.toString());
    	Integer amount = rc - dc; 
    	String open = "<html><p> <b> Rescue Counsel : </b></p>";
    	String close = "</html>" ;
    	String countingDeathString = "Setting Subtract <i>Likely Dead </i> from Rescue Count: Status: <b>"+ doCountDeathO  + "</b><br>";
    	String deathButNotTheMetString = "Amount of <i>Likely Dead and RescueThresholds not reached</i> :<b> " + dt + "</b><br>";
    	String deathAndMetThresholds = "Amount of <i> Likely Dead and reached Rescue Thresholds</i> : <b> " + dc + "</b><br>";
    	String rawViableRescueCount = "Amount of <i> Rescue Thresholds reached </i>:  <b> " + rc +"</b><br><br>";
    	String countReportString = countingDeathString+deathButNotTheMetString+deathAndMetThresholds+rawViableRescueCount;
    	
    	String rescueviableStringEnabledDeath = "<p style=\"color: green; font-size:24\">Final Viable Rescue Count : "+ amount +"</p>";
    	String rescuevialbeStringDisabledDeath = "<p style=\"color: green; font-size:24\">Final Viable Rescue Count : "+ rc +"</p>";
    	
    	String negativeCounselString = "<p style=\"color: green; font-size: 24\"><b>!!!! Rescue Thresholds Not Reached !!!!</b></p>";
    	String positiveCounselString = "<p style=\"color: red; font-size: 24\"><b>!!!! Rescue Thresholds Reached: Please Initiate Rescue Mission !!!!</b></p>";
    	if(rescue == true && doCountDeath == true) {
    		//print rescue + html rescC - DeathC = finalC    		
    		htmlstring = positiveCounselString+ countReportString + rescueviableStringEnabledDeath;
    		display.costAction.setText(open+htmlstring+close);
    	} else if (rescue == true && doCountDeath == false) {
    		// print rescue + html show only final Count
    		htmlstring = positiveCounselString + countReportString + rescuevialbeStringDisabledDeath;
    		display.costAction.setText(open+htmlstring+close);
    	} else if (rescue == false && doCountDeath == true) {
    		// print No Rescue with rescue - deathC == finalC
    		htmlstring = negativeCounselString + countReportString + rescueviableStringEnabledDeath;
    		display.costAction.setText(open+htmlstring+close);
    	} else if (rescue == false && doCountDeath == false) {
    		// print No Rescue with viableRescueCount
    		htmlstring = negativeCounselString + countReportString + rescuevialbeStringDisabledDeath;
    		display.costAction.setText(open+htmlstring+close);
    	}
    }
    @OPERATION void printDiag(Object kind, Object status, Object values, Object flag) {
 
    	if (flag.toString().equals("1")) {
    		flag = "green";
    	} else if (flag.toString().equals("2")) {
    		flag = "orange";
    	} else if (flag.toString().equals("3")) {
    		flag = "red";
    	}
    	String htmlopen = ("<html><p style=\"color:"+flag+"\">");
    	String htmlclose = ("</p></html>");
   
    	if (kind.equals("death")) {
    		display.deathstatus.setText(htmlopen+status+htmlclose);
    		display.deathvalues.setText(htmlopen+values+htmlclose);
    		display.deathflag.setText(htmlopen+flag+htmlclose);
    	} else if (kind.equals("unconsciousness")) {
        	display.unconsciousness_flag.setText(htmlopen+flag+htmlclose);
        	display.unconsciousness_status.setText(htmlopen+status+htmlclose);
        	display.unconsciousnessvalues.setText(htmlopen+values+htmlclose);	
    	} else if (kind.equals("impaired_mental")) {
        	display.impaired_mentalflag.setText(htmlopen+flag+htmlclose);
        	display.impaired_mentalstatus.setText(htmlopen+status+htmlclose);
        	display.impaired_mentalvalues.setText(htmlopen+values+htmlclose);
    	} else if (kind.equals("resp")) {
        	display.respflag.setText(htmlopen+flag+htmlclose);
        	display.respstatus.setText(htmlopen+status+htmlclose);
        	display.respvalues.setText(htmlopen+values+htmlclose);
    	} else if (kind.equals("heat")) {
        	display.heatflag.setText(htmlopen+flag+htmlclose);
        	display.heatstatus.setText(htmlopen+status+htmlclose);
        	display.heatvalues.setText(htmlopen+values+htmlclose);
    	} else if (kind.equals("undercooled")) {
        	display.undercooledflag.setText(htmlopen+flag+htmlclose);
        	display.undercooledstatus.setText(htmlopen+status+htmlclose);
        	display.undercooledvalues.setText(htmlopen+values+htmlclose);
    	} else if (kind.equals("shock")) {
        	display.shockflag.setText(htmlopen+flag+htmlclose);
        	display.shockstatus.setText(htmlopen+status+htmlclose);
        	display.shockvalues.setText(htmlopen+values+htmlclose);
    	} else if (kind.equals("shot")) {
        	display.shotflag.setText(htmlopen+flag+htmlclose);
        	display.shotstatus.setText(htmlopen+status+htmlclose);
        	display.shotvalues.setText(htmlopen+values+htmlclose);
    	} else if (kind.equals("temp")) {
        	display.tempflag.setText(htmlopen+flag+htmlclose);
        	display.temprisk.setText(htmlopen+status+htmlclose);
        	display.tempvalue.setText(htmlopen+values+htmlclose);
    	} else if (kind.equals("rr")) {
        	display.rrflag.setText(htmlopen+flag+htmlclose);
        	display.rrrisk.setText(htmlopen+status+htmlclose);
        	display.rrvalue.setText(htmlopen+values+htmlclose);
    	} else if (kind.equals("hr")) {
        	display.hrflag.setText(htmlopen+flag+htmlclose);
        	display.hrrisk.setText(htmlopen+status+htmlclose);
        	display.hrvalue.setText(htmlopen+values+htmlclose);
    	} else if (kind.equals("sbp")) {
        	display.sbprisk.setText(htmlopen+status+htmlclose);
        	display.sbpvalue.setText(htmlopen+values+htmlclose);
        	display.sbpflag.setText(htmlopen+flag+htmlclose);
    	} else if (kind.equals("spo2")) {
        	display.spo2flag.setText(htmlopen+flag+htmlclose);
        	display.spo2risk.setText(htmlopen+status+htmlclose);
        	display.spo2value.setText(htmlopen+values+htmlclose);
    	}

    }
    @OPERATION void printDiagTimeStamp(long timeStamp, long currTime){
    	Long age = currTime - timeStamp;
    	age = age / 1000;
    	if ( age > 60 ) {
    		age = age / 60;
    		String time = age.intValue() +" min";
    		display.age.setText(time);
    	} else {
        	Integer realage = age.intValue();
        	display.age.setText(realage+" seconds");
    	}
    }

    @OPERATION void clearDiag() {
    	// all JLabels .setText("NaN")
    	display.unconsciousness_flag.setText("NaN");
    	display.unconsciousness_status.setText("NaN");
    	display.unconsciousnessvalues.setText("NaN");
    	display.impaired_mentalflag.setText("NaN");
    	display.impaired_mentalstatus.setText("NaN");
    	display.impaired_mentalvalues.setText("NaN");
    	display.respflag.setText("NaN");
    	display.respstatus.setText("NaN");
    	display.respvalues.setText("NaN");
    	display.heatflag.setText("NaN");
    	display.heatstatus.setText("NaN");
    	display.heatvalues.setText("NaN");
    	display.undercooledflag.setText("NaN");
    	display.undercooledstatus.setText("NaN");
    	display.undercooledvalues.setText("NaN");
    	display.shockflag.setText("NaN");
    	display.shockstatus.setText("NaN");
    	display.shockvalues.setText("NaN");
    	display.shotflag.setText("NaN");
    	display.shotstatus.setText("NaN");
    	display.shotvalues.setText("NaN");
    	display.deathflag.setText("NaN");
    	display.deathstatus.setText("NaN");
    	display.deathvalues.setText("NaN");
    	//resetting all measured value_fields
    	display.tempflag.setText("NaN");
    	display.temprisk.setText("NaN");
    	display.tempvalue.setText("NaN");
    	display.rrflag.setText("NaN");
    	display.rrrisk.setText("NaN");
    	display.rrvalue.setText("NaN");
    	display.hrflag.setText("NaN");
    	display.hrrisk.setText("NaN");
    	display.hrvalue.setText("NaN");
    	display.sbprisk.setText("NaN");
    	display.sbpvalue.setText("NaN");
    	display.sbpflag.setText("NaN");
    	display.spo2flag.setText("NaN");
    	display.spo2risk.setText("NaN");
    	display.spo2value.setText("NaN");
    }

}

