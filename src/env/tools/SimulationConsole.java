// CArtAgO artifact code for project military_health

package tools;

import cartago.*;
import cartago.tools.GUIArtifact;
import java.awt.event.ActionEvent;

public class SimulationConsole extends GUIArtifact {
	
	private SimulationDisplay display;
	
	void init(String title, int startStage, boolean visible) {
		
		defineObsProperty("currentStage", startStage);
		
		display = new SimulationDisplay(title, startStage);
		linkActionEventToOp(display.stage0Button, "stage0Button");
		linkActionEventToOp(display.stage1Button, "stage1Button");
		linkActionEventToOp(display.stage2Button, "stage2Button");
		linkActionEventToOp(display.stage3Button, "stage3Button");
		linkActionEventToOp(display.stage4Button, "stage4Button");
		
		display.setVisible(visible);
		this.init();
		
	}
	@INTERNAL_OPERATION void stage0Button(ActionEvent ev) {
		setStage(0);
	}
	@INTERNAL_OPERATION void stage1Button(ActionEvent ev) {
		setStage(1);
	}	
	@INTERNAL_OPERATION void stage2Button(ActionEvent ev) {
		setStage(2);
	}	
	@INTERNAL_OPERATION void stage3Button(ActionEvent ev) {
		setStage(3);
	}	
	@INTERNAL_OPERATION void stage4Button(ActionEvent ev) {
		setStage(4);
	}
	
	@OPERATION void setStage(Integer x) {
		
		display.currentStageStatusdisplay.setText("Current Active Stage : "+ x);
		//await_time(1000); 
		getObsProperty("currentStage").updateValue(x);
	}

}

