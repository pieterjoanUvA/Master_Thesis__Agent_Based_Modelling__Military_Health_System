// CArtAgO artifact code for project military_health

package tools;

import java.awt.event.ActionEvent;

import cartago.*;
import cartago.tools.*;

public class SymptomConsole extends GUIArtifact {
	private SymptomDisplay display;
	private static int n = 0;
	//private boolean visible = false;
	void init( String title, String ag, boolean visible) {

		display = new SymptomDisplay(title); //create Gui Panel
		linkActionEventToOp(display.bpr_above95, "bpr_above95");
		linkActionEventToOp(display.bpr_under85, "bpr_under85");
		linkActionEventToOp(display.bpr_under90, "bpr_under90");
		linkActionEventToOp(display.br_over24, "br_over24");
		linkActionEventToOp(display.br_over29, "br_over29");
		linkActionEventToOp(display.br_under10, "br_under10");
		linkActionEventToOp(display.br_0, "br_0");
		linkActionEventToOp(display.dead, "dead");//
		linkActionEventToOp(display.hosp, "hosp");//
		linkActionEventToOp(display.hr60_140, "hr60_140");
		linkActionEventToOp(display.hr_0, "hr_0");
		linkActionEventToOp(display.hr_over140, "hr_over140");
		linkActionEventToOp(display.hr_over160, "hr_over160");
		linkActionEventToOp(display.hr_under60, "hr_under60");
		linkActionEventToOp(display.move_intense, "move_intense");
		linkActionEventToOp(display.move_min, "move_min");
		linkActionEventToOp(display.move_moderate, "move_moderate");
		linkActionEventToOp(display.reset, "reset"); //
		linkActionEventToOp(display.shock, "shock");//
		linkActionEventToOp(display.shot, "shot");//
		linkActionEventToOp(display.spo2_above95, "spo2_above95");
		linkActionEventToOp(display.spo2_under90, "spo2_under90");
		linkActionEventToOp(display.spo2_under93, "spo2_under93");
		linkActionEventToOp(display.temp_norm, "temp_norm");
		linkActionEventToOp(display.temp_under28, "temp_under28");
		linkActionEventToOp(display.temp_over40, "temp_over40");
		linkActionEventToOp(display.temp_under36, "temp_under36");
		display.setLocation(n * 10, 40);
		n++;
		display.setVisible(visible);
		this.init();

		//try {
		//	    Thread.sleep(1000);                 //1000 milliseconds is one second.
		//	} catch(InterruptedException ex) {
		//	    Thread.currentThread().interrupt();
		//	}
	}
    @INTERNAL_OPERATION void temp_norm(ActionEvent ev){
    	this.signal("cmd", "setValue", "temp", 37.2);
    }
    @INTERNAL_OPERATION void temp_under28(ActionEvent ev){
    	this.signal("cmd", "setValue", "temp", 24.4);
    }
    @INTERNAL_OPERATION void temp_under36(ActionEvent ev){
    	this.signal("cmd", "setValue", "temp", 34.5);
    }
    @INTERNAL_OPERATION void temp_over40(ActionEvent ev){
    	this.signal("cmd", "setValue", "temp", 42.1);
    }
    @INTERNAL_OPERATION void spo2_above95(ActionEvent ev){
    	this.signal("cmd", "setValue", "spo2", 97);
    }
    @INTERNAL_OPERATION void spo2_under90(ActionEvent ev){
    	this.signal("cmd", "setValue", "spo2", 87);
    }
    @INTERNAL_OPERATION void spo2_under93(ActionEvent ev){
    	this.signal("cmd", "setValue", "spo2", 92);
    }
    @INTERNAL_OPERATION void move_moderate(ActionEvent ev){
    	this.signal("cmd", "setValue", "gyro", 2.42);
    	this.signal("cmd", "setValue", "acc", 2.42);
    }
    @INTERNAL_OPERATION void move_min(ActionEvent ev){
    	this.signal("cmd", "setValue", "gyro", 0.242);
    	this.signal("cmd", "setValue", "acc", 0.244);
    }
    @INTERNAL_OPERATION void move_intense(ActionEvent ev){
    	this.signal("cmd", "setValue", "gyro", 4);
    	this.signal("cmd", "setValue", "acc", 4);
    }
    @INTERNAL_OPERATION void hr60_140(ActionEvent ev){
    	this.signal("cmd", "setValue", "hr", 100);
    }
    @INTERNAL_OPERATION void hr_0(ActionEvent ev){
    	this.signal("cmd", "setValue", "hr", 0);
    }
    @INTERNAL_OPERATION void hr_over140(ActionEvent ev){
    	this.signal("cmd", "setValue", "hr", 150);
    }
    @INTERNAL_OPERATION void hr_over160(ActionEvent ev){
    	this.signal("cmd", "setValue", "hr", 180);
    }
    @INTERNAL_OPERATION void hr_under60(ActionEvent ev){
    	this.signal("cmd", "setValue", "hr", 30);
    }
    @INTERNAL_OPERATION void hosp(ActionEvent ev){
    	this.signal("cmd", "hosp");
    	this.signal("cmd", "setValue", "rr", 6);
    	this.signal("cmd", "setValue", "spo2", 80);
    	this.signal("cmd", "setValue", "hr", 110);
    	this.signal("cmd", "setValue", "sbp", 70);
    	this.signal("cmd", "setValue", "temp", 34.4);
    } 
    @INTERNAL_OPERATION void dead(ActionEvent ev){
    	this.signal("cmd", "dead");
    	this.signal("cmd", "setValue", "temp", 22.2);
    	this.signal("cmd", "setValue", "spo2", 60);
    	this.signal("cmd", "setValue", "gyro", 0.42);
    	this.signal("cmd", "setValue", "acc", 0.24);
    	this.signal("cmd", "setValue", "hr", 0);
    	this.signal("cmd", "setValue", "sbp", 8);
    	this.signal("cmd", "setValue", "rr", 0);
    } 
    @INTERNAL_OPERATION void reset(ActionEvent ev){
    	this.signal("cmd", "reset");
    	this.signal("cmd", "setValue", "temp", 37.2);
    	this.signal("cmd", "setValue", "spo2", 97);
    	this.signal("cmd", "setValue", "gyro", 2.42);
    	this.signal("cmd", "setValue", "acc", 2.42);
    	this.signal("cmd", "setValue", "hr", 100);
    	this.signal("cmd", "setValue", "sbp", 100);
    	this.signal("cmd", "setValue", "rr", 25);
    } 
    @INTERNAL_OPERATION void shock(ActionEvent ev){
    	this.signal("cmd", "shock"); 
    	//normal is SI(0.5-0.7) and SI=hr_/sbp_ above 0.9 alarming
    	this.signal("cmd", "setValue", "hr", 120);
    	this.signal("cmd", "setValue", "sbp", 120);
    } 
    @INTERNAL_OPERATION void shot(ActionEvent ev){
    	this.signal("cmd", "shot");
    	this.signal("cmd", "setValue", "sbp", 70);
    }
    @INTERNAL_OPERATION void bpr_above95(ActionEvent ev){
    	this.signal("cmd", "setValue", "sbp", 100);
    }
    @INTERNAL_OPERATION void bpr_under85(ActionEvent ev){
    	this.signal("cmd", "setValue", "sbp", 6);
    }
    @INTERNAL_OPERATION void bpr_under90(ActionEvent ev){
    	this.signal("cmd", "setValue", "sbp", 87);
    }
    @INTERNAL_OPERATION void br_over24(ActionEvent ev){
    	this.signal("cmd", "setValue", "rr", 25);
    }
    @INTERNAL_OPERATION void br_over29(ActionEvent ev){
    	this.signal("cmd", "setValue", "rr", 32);
    }
    @INTERNAL_OPERATION void br_under10(ActionEvent ev){
    	this.signal("cmd", "setValue", "rr", 8);
    }
    @INTERNAL_OPERATION void br_0(ActionEvent ev){
    	this.signal("cmd", "setValue", "rr", 1);
    }
	@OPERATION
	void inc() {
		ObsProperty prop = getObsProperty("count");
		prop.updateValue(prop.intValue()+1);
		signal("tick");
	}
}

