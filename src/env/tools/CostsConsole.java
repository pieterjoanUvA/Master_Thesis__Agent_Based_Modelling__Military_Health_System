// CArtAgO artifact code for project military_health

package tools;

import java.awt.event.ActionEvent;

import cartago.*;
import cartago.tools.*;

public class CostsConsole extends GUIArtifact {
	private CostsDisplay display;
	
		void init(String title, double sbp, double spo2, double hr, double rr, double temp, 
				double death, double shot, double shock, double heat, double undercooled, double resp, double impaired_mental_costs, double unconsciousness_costs, 
				double catSensor, double catDiagnosed, double catCumulative, double rescueEquipmentCost, double rescuePersonnelCount, double rescuePersonnelCost, 
				double teamMemberHumanLifeValue, boolean doCountLikelyDeathStatus, boolean visible) {
	
			// calculate total from init values before display init		
			Double totalRescueCosts = rescueEquipmentCost + (rescuePersonnelCount * rescuePersonnelCost);
	
			display = new CostsDisplay( title,  sbp,  spo2,  hr,  rr,  temp, 
					 death,  shot,  shock,  heat,  undercooled,  resp,  impaired_mental_costs,  unconsciousness_costs, 
					 catSensor,  catDiagnosed,  catCumulative,  rescueEquipmentCost,  rescuePersonnelCount,  rescuePersonnelCost, 
					 teamMemberHumanLifeValue,  doCountLikelyDeathStatus,  totalRescueCosts);
			
			defineObsProperty("sbp", sbp);
			defineObsProperty("spo2", spo2);
			defineObsProperty("hr", hr);
			defineObsProperty("rr", rr);
			defineObsProperty("temp", temp);
			defineObsProperty("death", death);
			defineObsProperty("shot", shot);
			defineObsProperty("shock", shock);
			defineObsProperty("heat", heat);
			defineObsProperty("undercooled", undercooled);
			defineObsProperty("resp", resp);
			defineObsProperty("impaired_mental_costs", impaired_mental_costs);
			defineObsProperty("unconsciousness_costs", unconsciousness_costs);
			defineObsProperty("catSensor", catSensor);
			defineObsProperty("catDiagnosed", catDiagnosed);
			defineObsProperty("catCumulative", catCumulative);
			defineObsProperty("rescueEquipmentCost", rescueEquipmentCost);
			defineObsProperty("rescuePersonnelCount", rescuePersonnelCount);
			defineObsProperty("rescuePersonnelCost", rescuePersonnelCost);
			defineObsProperty("teamMemberHumanLifeValue", teamMemberHumanLifeValue);
			defineObsProperty("doCountLikelyDeathStatus", doCountLikelyDeathStatus);
			defineObsProperty("totalRescueCosts", totalRescueCosts);
			//defineObsProperty("ugh", null).updateValues(display);
			
			
			linkActionEventToOp(display.applyValues, "setCostValuesButton");
			linkActionEventToOp(display.resetValues, "cancelCostValuesButton");
			linkActionEventToOp(display.doCountLikelyDeath, "toggleCostDeathCountButton");
			
	
			display.setVisible(visible);
			display.setLocation(720, 320);
			this.init();
	
		}
		@INTERNAL_OPERATION void cancelCostValuesButton(ActionEvent ev) {
			display.sbpI.setText(""+getObsProperty("sbp").getValue().toString());
			display.spo2I.setText(""+getObsProperty("spo2").getValue().toString());
			display.hrI.setText(""+getObsProperty("hr").getValue().toString());
			display.rrI.setText(""+getObsProperty("rr").getValue().toString());
			display.tempI.setText(""+getObsProperty("temp").getValue().toString());
			display.deathI.setText(""+getObsProperty("death").getValue().toString());
			display.shotI.setText(""+getObsProperty("shot").getValue().toString());
			display.shockI.setText(""+getObsProperty("shock").getValue().toString());
			display.heatI.setText(""+getObsProperty("heat").getValue().toString());
			display.undercooledI.setText(""+getObsProperty("undercooled").getValue().toString());
			display.respI.setText(""+getObsProperty("resp").getValue().toString());
			display.impaired_mental_costsI.setText(""+getObsProperty("impaired_mental_costs").getValue().toString());
			display.unconsciousness_costsI.setText(""+getObsProperty("unconsciousness_costs").getValue().toString());
			display.catSensorI.setText(""+getObsProperty("catSensor").getValue().toString());
			display.catDiagnosedI.setText(""+getObsProperty("catDiagnosed").getValue().toString());
			display.catCumulativeI.setText(""+getObsProperty("catCumulative").getValue().toString());
			display.rescueEquipmentCostI.setText(""+getObsProperty("rescueEquipmentCost").getValue().toString());
			display.rescuePersonnelCountI.setText(""+getObsProperty("rescuePersonnelCount").getValue().toString());
			display.rescuePersonnelCostI.setText(""+getObsProperty("rescuePersonnelCost").getValue().toString());
			display.teamMemberHumanLifeValueI.setText(""+getObsProperty("teamMemberHumanLifeValue").getValue().toString());
			// .doCountLikelyDeathStatusL does change instantly in Gui but does not get saved in ObsProperty
			display.doCountLikelyDeathStatusL.setText("subtract enabled: "+getObsProperty("doCountLikelyDeathStatus").getValue().toString());

		}
		/*(sbp,  spo2,  hr,  rr,  temp, 
					death,  shot,  shock,  heat,  undercooled,  resp,  impaired_mental_costs,  unconsciousness_costs, 
					catSensor,  catDiagnosed,  catCumulative,  rescueEquipmentCost,  rescuePersonnelCount,  rescuePersonnelCost, 
					teamMemberHumanLifeValue,  doCountLikelyDeathStatus);
					*/
		@INTERNAL_OPERATION void setCostValuesButton(ActionEvent ev) {
			double sbp = Double.parseDouble(display.sbpI.getText());
			double spo2 = Double.parseDouble(display.spo2I.getText());
			double hr = Double.parseDouble(display.hrI.getText());
			double rr = Double.parseDouble(display.rrI.getText());
			double temp = Double.parseDouble(display.tempI.getText());
			double death = Double.parseDouble(display.deathI.getText());
			double shot = Double.parseDouble(display.shotI.getText());
			double shock = Double.parseDouble(display.shockI.getText());
			double heat = Double.parseDouble(display.heatI.getText());
			double undercooled = Double.parseDouble(display.undercooledI.getText());
			double resp = Double.parseDouble(display.respI.getText());
			double impaired_mental_costs = Double.parseDouble(display.impaired_mental_costsI.getText());
			double unconsciousness_costs = Double.parseDouble(display.unconsciousness_costsI.getText());
			double catSensor = Double.parseDouble(display.catSensorI.getText());
			double catDiagnosed = Double.parseDouble(display.catDiagnosedI.getText());
			double catCumulative = Double.parseDouble(display.catCumulativeI.getText());
			double rescueEquipmentCost = Double.parseDouble(display.rescueEquipmentCostI.getText());
			double rescuePersonnelCount = Double.parseDouble(display.rescuePersonnelCountI.getText());
			double rescuePersonnelCost = Double.parseDouble(display.rescuePersonnelCostI.getText());
			double teamMemberHumanLifeValue = Double.parseDouble(display.teamMemberHumanLifeValueI.getText());

			Boolean doCountLikelyDeathStatus = (Boolean) getObsProperty("doCountLikelyDeathStatus").getValue();
			
			setCostValues(sbp,  spo2,  hr,  rr,  temp, 
					death,  shot,  shock,  heat,  undercooled,  resp,  impaired_mental_costs,  unconsciousness_costs, 
					catSensor,  catDiagnosed,  catCumulative,  rescueEquipmentCost,  rescuePersonnelCount,  rescuePersonnelCost, 
					teamMemberHumanLifeValue,  doCountLikelyDeathStatus);
		}
		
		@INTERNAL_OPERATION void toggleCostDeathCountButton(ActionEvent ev) {
			// !! even niet -- this function only updates the Gui, the value is only applied after the setValuesButton press
			Boolean bool = (Boolean) getObsProperty("doCountLikelyDeathStatus").getValue();
			if ( bool == false ) {
				bool = true;
				display.doCountLikelyDeathStatusL.setText("subtract enabled: "+bool.toString());
				getObsProperty("doCountLikelyDeathStatus").updateValue(bool);
			} else if ( bool == true ) {
				bool = false;
				display.doCountLikelyDeathStatusL.setText("subtract enabled: "+bool.toString());
				getObsProperty("doCountLikelyDeathStatus").updateValue(bool);
			}
		}
		@OPERATION void setCostValues(Double sbp, Double spo2, Double hr, Double rr, Double temp, 
			Double death, Double shot, Double shock, Double heat, Double undercooled, Double resp, Double impaired_mental_costs, Double unconsciousness_costs, 
			Double catSensor, Double catDiagnosed, Double catCumulative, Double rescueEquipmentCost, Double rescuePersonnelCount, Double rescuePersonnelCost, 
			Double teamMemberHumanLifeValue, Boolean doCountLikelyDeathStatus) {
			// calculate total from method parameters 	
			Double totalRescueCosts = rescueEquipmentCost + (rescuePersonnelCount * rescuePersonnelCost);
			
			getObsProperty("sbp").updateValue(sbp);
			display.sbpI.setText(""+sbp);
			getObsProperty("spo2").updateValue(spo2);
			display.spo2I.setText(""+spo2);
			getObsProperty("hr").updateValue(hr);
			display.hrI.setText(""+hr);
			getObsProperty("rr").updateValue(rr);
			display.rrI.setText(""+rr);
			getObsProperty("temp").updateValue(temp);
			display.tempI.setText(""+temp);
			getObsProperty("death").updateValue(death);
			display.deathI.setText(""+death);
			getObsProperty("shot").updateValue(shot);
			display.shotI.setText(""+shot);
			getObsProperty("shock").updateValue(shock);
			display.shockI.setText(""+shock);
			getObsProperty("heat").updateValue(heat);
			display.heatI.setText(""+heat);
			getObsProperty("undercooled").updateValue(undercooled);
			display.undercooledI.setText(""+undercooled);
			getObsProperty("resp").updateValue(resp);
			display.respI.setText(""+resp);
			getObsProperty("impaired_mental_costs").updateValue(impaired_mental_costs);
			display.impaired_mental_costsI.setText(""+impaired_mental_costs);
			getObsProperty("unconsciousness_costs").updateValue(unconsciousness_costs);
			display.unconsciousness_costsI.setText(""+unconsciousness_costs);
			getObsProperty("catSensor").updateValue(catSensor);
			display.catSensorI.setText(""+catSensor);
			getObsProperty("catDiagnosed").updateValue(catDiagnosed);
			display.catDiagnosedI.setText(""+catDiagnosed);
			getObsProperty("catCumulative").updateValue(catCumulative);
			display.catCumulativeI.setText(""+catCumulative);
			getObsProperty("rescueEquipmentCost").updateValue(rescueEquipmentCost);
			display.rescueEquipmentCostI.setText(""+rescueEquipmentCost);
			getObsProperty("rescuePersonnelCost").updateValue(rescuePersonnelCost);
			display.rescuePersonnelCostI.setText(""+rescuePersonnelCost);
			getObsProperty("teamMemberHumanLifeValue").updateValue(teamMemberHumanLifeValue);
			display.teamMemberHumanLifeValueI.setText(""+teamMemberHumanLifeValue);
			getObsProperty("doCountLikelyDeathStatus").updateValue(doCountLikelyDeathStatus);
			display.doCountLikelyDeathStatusL.setText("subtract enabled: "+doCountLikelyDeathStatus);
			getObsProperty("totalRescueCosts").updateValue(totalRescueCosts);
			display.totalRescueCostsL.setText(""+totalRescueCosts);
		}

}

