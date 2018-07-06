// CArtAgO artifact code for project military_health

package tools;

import java.awt.event.ActionEvent;

import cartago.*;
import cartago.tools.*;

public class ThreshConsole extends GUIArtifact {
	private ThreshDisplay display;
	
	void init(String title, 
			double sbp_lower_th_highrisk, double sbp_lower_th_risk, double sbp_high_th_risk, double sbp_high_th_highrisk,
			double spo2_lower_th_highrisk, double spo2_lower_th_risk,
			double hr_lower_th_highrisk, double hr_lower_th_risk, double hr_high_th_risk, double hr_high_th_highrisk,
			double rr_lower_th_highrisk, double rr_high_th_highrisk,
			double temp_lower_th_highrisk, double temp_high_th_risk, double temp_high_th_highrisk,
			double deathTemp, double deathSbp, double deathRr, double deathAcc, double deathHr,
			double shotSbp, double shotSd,
			double shockSi,
			double hypothermia,
			double hyperthermia, double hyperpyrexia,
			double apnea, double bradypnea, double tachypnea_hypervent,
			double impaired_mental, double unconsciousness, boolean visible, boolean enablepropagate) {

		display = new ThreshDisplay( title, 
				 sbp_lower_th_highrisk, sbp_lower_th_risk, sbp_high_th_risk, sbp_high_th_highrisk,
				 spo2_lower_th_highrisk, spo2_lower_th_risk,
				 hr_lower_th_highrisk, hr_lower_th_risk, hr_high_th_risk, hr_high_th_highrisk,
				 rr_lower_th_highrisk, rr_high_th_highrisk,
				 temp_lower_th_highrisk, temp_high_th_risk, temp_high_th_highrisk,
				 deathTemp, deathSbp, deathRr, deathAcc, deathHr,
				 shotSbp, shotSd,
				 shockSi,
				 hypothermia,
				 hyperthermia, hyperpyrexia,
				 apnea, bradypnea, tachypnea_hypervent,
				 impaired_mental, unconsciousness);
		
		defineObsProperty("sbp_lower_th_highrisk", sbp_lower_th_highrisk);
		defineObsProperty("sbp_lower_th_risk", sbp_lower_th_risk);
		defineObsProperty("sbp_high_th_risk", sbp_high_th_risk);
		defineObsProperty("sbp_high_th_highrisk", sbp_high_th_highrisk);
		defineObsProperty("spo2_lower_th_highrisk", spo2_lower_th_highrisk);
		defineObsProperty("spo2_lower_th_risk", spo2_lower_th_risk);
		defineObsProperty("hr_lower_th_highrisk", hr_lower_th_highrisk);
		defineObsProperty("hr_lower_th_risk", hr_lower_th_risk);
		defineObsProperty("hr_high_th_risk", hr_high_th_risk);
		defineObsProperty("hr_high_th_highrisk", hr_high_th_highrisk);
		defineObsProperty("rr_lower_th_highrisk", rr_lower_th_highrisk);
		defineObsProperty("rr_high_th_highrisk", rr_high_th_highrisk);
		defineObsProperty("temp_lower_th_highrisk", temp_lower_th_highrisk);
		defineObsProperty("temp_high_th_risk", temp_high_th_risk);
		defineObsProperty("temp_high_th_highrisk", temp_high_th_highrisk);
		defineObsProperty("deathTemp", deathTemp);
		defineObsProperty("deathSbp", deathSbp);
		defineObsProperty("deathRr", deathRr);
		defineObsProperty("deathAcc", deathAcc);
		defineObsProperty("deathHr", deathHr);
		defineObsProperty("shotSbp", shotSbp);
		defineObsProperty("shotSd", shotSd);
		defineObsProperty("shockSi", shockSi);		
		defineObsProperty("hypothermia", hypothermia);
		defineObsProperty("hyperthermia", hyperthermia);
		defineObsProperty("hyperpyrexia", hyperpyrexia);
		defineObsProperty("apnea", apnea);
		defineObsProperty("bradypnea", bradypnea);
		defineObsProperty("tachypnea_hypervent", tachypnea_hypervent);
		defineObsProperty("impaired_mental", impaired_mental);
		defineObsProperty("unconsciousness", unconsciousness);

		linkActionEventToOp(display.setValues, "setValuesButton");
		linkActionEventToOp(display.cancelUpdate, "cancelUpdateButton");
		linkActionEventToOp(display.setValuesAndPropagate, "setValuesAndPropagate");
		
		display.setValuesAndPropagate.setVisible(enablepropagate);
		if (enablepropagate == true) {
			display.setValues.setVisible(false);
		}
		display.setLocation(740, 260);
		display.setVisible(visible);
		this.init();
	}
	@INTERNAL_OPERATION void setValuesAndPropagate(ActionEvent ev) {
		double sbp_lower_th_highrisk = Double.parseDouble(display.sbp_lower_th_highriskI.getText());
		double sbp_lower_th_risk = Double.parseDouble(display.sbp_lower_th_riskI.getText());
		double sbp_high_th_risk = Double.parseDouble(display.sbp_high_th_riskI.getText());
		double sbp_high_th_highrisk = Double.parseDouble(display.sbp_high_th_highriskI.getText());
		double spo2_lower_th_highrisk = Double.parseDouble(display.spo2_lower_th_highriskI.getText());
		double spo2_lower_th_risk = Double.parseDouble(display.spo2_lower_th_riskI.getText());
		double hr_lower_th_highrisk = Double.parseDouble(display.hr_lower_th_highriskI.getText());
		double hr_lower_th_risk = Double.parseDouble(display.hr_lower_th_riskI.getText());
		double hr_high_th_risk = Double.parseDouble(display.hr_high_th_riskI.getText());
		double hr_high_th_highrisk = Double.parseDouble(display.hr_high_th_highriskI.getText());
		double rr_lower_th_highrisk = Double.parseDouble(display.rr_lower_th_highriskI.getText());
		double rr_high_th_highrisk = Double.parseDouble(display.rr_high_th_highriskI.getText());
		double temp_lower_th_highrisk = Double.parseDouble(display.temp_lower_th_highriskI.getText());
		double temp_high_th_risk = Double.parseDouble(display.temp_high_th_riskI.getText());
		double temp_high_th_highrisk = Double.parseDouble(display.temp_high_th_highriskI.getText());
		double deathTemp = Double.parseDouble(display.deathTempI.getText());
		double deathSbp = Double.parseDouble(display.deathSbpI.getText());
		double deathRr = Double.parseDouble(display.deathRrI.getText());
		double deathAcc = Double.parseDouble(display.deathAccI.getText());
		double deathHr = Double.parseDouble(display.deathHrI.getText());
		double shotSbp = Double.parseDouble(display.shotSbpI.getText());
		double shotSd = Double.parseDouble(display.shotSdI.getText());
		double shockSi = Double.parseDouble(display.shockSiI.getText());
		double hypothermia = Double.parseDouble(display.hypothermiaI.getText());
		double hyperthermia = Double.parseDouble(display.hyperthermiaI.getText());
		double hyperpyrexia = Double.parseDouble(display.hyperpyrexiaI.getText());
		double apnea = Double.parseDouble(display.apneaI.getText());
		double bradypnea = Double.parseDouble(display.bradypneaI.getText());
		double tachypnea_hypervent = Double.parseDouble(display.tachypnea_hyperventI.getText());
		double impaired_mental = Double.parseDouble(display.impaired_mentalI.getText());
		double unconsciousness = Double.parseDouble(display.unconsciousnessI.getText());
		
		setValues(sbp_lower_th_highrisk, sbp_lower_th_risk, sbp_high_th_risk, sbp_high_th_highrisk,
				 spo2_lower_th_highrisk, spo2_lower_th_risk,
				 hr_lower_th_highrisk, hr_lower_th_risk, hr_high_th_risk, hr_high_th_highrisk,
				 rr_lower_th_highrisk, rr_high_th_highrisk,
				 temp_lower_th_highrisk, temp_high_th_risk, temp_high_th_highrisk,
				 deathTemp, deathSbp, deathRr, deathAcc, deathHr,
				 shotSbp, shotSd,
				 shockSi,
				 hypothermia,
				 hyperthermia, hyperpyrexia,
				 apnea, bradypnea, tachypnea_hypervent,
				 impaired_mental, unconsciousness);
		signal("cmd","propagateThresholdValues", sbp_lower_th_highrisk, sbp_lower_th_risk, sbp_high_th_risk, sbp_high_th_highrisk,
				 spo2_lower_th_highrisk, spo2_lower_th_risk, hr_lower_th_highrisk, hr_lower_th_risk, hr_high_th_risk, hr_high_th_highrisk,
				 rr_lower_th_highrisk, rr_high_th_highrisk, temp_lower_th_highrisk, temp_high_th_risk, temp_high_th_highrisk,
				 deathTemp, deathSbp, deathRr, deathAcc, deathHr, shotSbp, shotSd, shockSi,hypothermia, hyperthermia, hyperpyrexia,
				 apnea, bradypnea, tachypnea_hypervent, impaired_mental, unconsciousness  );
	}
	@INTERNAL_OPERATION void setValuesButton(ActionEvent ev) {
		double sbp_lower_th_highrisk = Double.parseDouble(display.sbp_lower_th_highriskI.getText());
		double sbp_lower_th_risk = Double.parseDouble(display.sbp_lower_th_riskI.getText());
		double sbp_high_th_risk = Double.parseDouble(display.sbp_high_th_riskI.getText());
		double sbp_high_th_highrisk = Double.parseDouble(display.sbp_high_th_highriskI.getText());
		double spo2_lower_th_highrisk = Double.parseDouble(display.spo2_lower_th_highriskI.getText());
		double spo2_lower_th_risk = Double.parseDouble(display.spo2_lower_th_riskI.getText());
		double hr_lower_th_highrisk = Double.parseDouble(display.hr_lower_th_highriskI.getText());
		double hr_lower_th_risk = Double.parseDouble(display.hr_lower_th_riskI.getText());
		double hr_high_th_risk = Double.parseDouble(display.hr_high_th_riskI.getText());
		double hr_high_th_highrisk = Double.parseDouble(display.hr_high_th_highriskI.getText());
		double rr_lower_th_highrisk = Double.parseDouble(display.rr_lower_th_highriskI.getText());
		double rr_high_th_highrisk = Double.parseDouble(display.rr_high_th_highriskI.getText());
		double temp_lower_th_highrisk = Double.parseDouble(display.temp_lower_th_highriskI.getText());
		double temp_high_th_risk = Double.parseDouble(display.temp_high_th_riskI.getText());
		double temp_high_th_highrisk = Double.parseDouble(display.temp_high_th_highriskI.getText());
		double deathTemp = Double.parseDouble(display.deathTempI.getText());
		double deathSbp = Double.parseDouble(display.deathSbpI.getText());
		double deathRr = Double.parseDouble(display.deathRrI.getText());
		double deathAcc = Double.parseDouble(display.deathAccI.getText());
		double deathHr = Double.parseDouble(display.deathHrI.getText());
		double shotSbp = Double.parseDouble(display.shotSbpI.getText());
		double shotSd = Double.parseDouble(display.shotSdI.getText());
		double shockSi = Double.parseDouble(display.shockSiI.getText());
		double hypothermia = Double.parseDouble(display.hypothermiaI.getText());
		double hyperthermia = Double.parseDouble(display.hyperthermiaI.getText());
		double hyperpyrexia = Double.parseDouble(display.hyperpyrexiaI.getText());
		double apnea = Double.parseDouble(display.apneaI.getText());
		double bradypnea = Double.parseDouble(display.bradypneaI.getText());
		double tachypnea_hypervent = Double.parseDouble(display.tachypnea_hyperventI.getText());
		double impaired_mental = Double.parseDouble(display.impaired_mentalI.getText());
		double unconsciousness = Double.parseDouble(display.unconsciousnessI.getText());
		
		setValues(sbp_lower_th_highrisk, sbp_lower_th_risk, sbp_high_th_risk, sbp_high_th_highrisk,
				 spo2_lower_th_highrisk, spo2_lower_th_risk,
				 hr_lower_th_highrisk, hr_lower_th_risk, hr_high_th_risk, hr_high_th_highrisk,
				 rr_lower_th_highrisk, rr_high_th_highrisk,
				 temp_lower_th_highrisk, temp_high_th_risk, temp_high_th_highrisk,
				 deathTemp, deathSbp, deathRr, deathAcc, deathHr,
				 shotSbp, shotSd,
				 shockSi,
				 hypothermia,
				 hyperthermia, hyperpyrexia,
				 apnea, bradypnea, tachypnea_hypervent,
				 impaired_mental, unconsciousness);
	}
	
	@OPERATION void personaliseThreshold(String thresholdname, Object value_i) {
		Double value = Double.parseDouble(value_i.toString());
		Double orig_value = Double.parseDouble(getObsProperty(thresholdname).getValue().toString());
		Double adjusted_value = value + orig_value;
		getObsProperty(thresholdname).updateValue(adjusted_value);
		updateFromObsProperties();
	}
	
	@OPERATION void setValues(double sbp_lower_th_highrisk, double sbp_lower_th_risk, double sbp_high_th_risk, double sbp_high_th_highrisk,
			double spo2_lower_th_highrisk, double spo2_lower_th_risk,
			double hr_lower_th_highrisk, double hr_lower_th_risk, double hr_high_th_risk, double hr_high_th_highrisk,
			double rr_lower_th_highrisk, double rr_high_th_highrisk,
			double temp_lower_th_highrisk, double temp_high_th_risk, double temp_high_th_highrisk,
			double deathTemp, double deathSbp, double deathRr, double deathAcc, double deathHr,
			double shotSbp, double shotSd,
			double shockSi,
			double hypothermia,
			double hyperthermia, double hyperpyrexia,
			double apnea, double bradypnea, double tachypnea_hypervent,
			double impaired_mental, double unconsciousness) {
		getObsProperty("sbp_lower_th_highrisk").updateValue(sbp_lower_th_highrisk);
		display.sbp_lower_th_highriskI.setText(""+sbp_lower_th_highrisk);
		getObsProperty("sbp_lower_th_risk").updateValue(sbp_lower_th_risk);
		display.sbp_lower_th_riskI.setText(""+sbp_lower_th_risk);
		getObsProperty("sbp_high_th_risk").updateValue(sbp_high_th_risk);
		display.sbp_high_th_riskI.setText(""+sbp_high_th_risk);
		getObsProperty("sbp_high_th_highrisk").updateValue(sbp_high_th_highrisk);
		display.sbp_high_th_highriskI.setText(""+sbp_high_th_highrisk);
		getObsProperty("spo2_lower_th_highrisk").updateValue(spo2_lower_th_highrisk);
		display.spo2_lower_th_highriskI.setText(""+spo2_lower_th_highrisk);
		getObsProperty("spo2_lower_th_risk").updateValue(spo2_lower_th_risk);
		display.spo2_lower_th_riskI.setText(""+spo2_lower_th_risk);
		getObsProperty("hr_lower_th_highrisk").updateValue(hr_lower_th_highrisk);
		display.hr_lower_th_highriskI.setText(""+hr_lower_th_highrisk);
		getObsProperty("hr_lower_th_highrisk").updateValue(hr_lower_th_highrisk);
		display.hr_lower_th_highriskI.setText(""+hr_lower_th_highrisk);
		getObsProperty("hr_lower_th_risk").updateValue(hr_lower_th_risk);
		display.hr_lower_th_riskI.setText(""+hr_lower_th_risk);
		getObsProperty("hr_high_th_risk").updateValue(hr_high_th_risk);
		display.hr_high_th_riskI.setText(""+hr_high_th_risk);
		getObsProperty("hr_high_th_highrisk").updateValue(hr_high_th_highrisk);
		display.hr_high_th_highriskI.setText(""+hr_high_th_highrisk);
		getObsProperty("rr_lower_th_highrisk").updateValue(rr_lower_th_highrisk);
		display.rr_lower_th_highriskI.setText(""+rr_lower_th_highrisk);
		getObsProperty("rr_high_th_highrisk").updateValue(rr_high_th_highrisk);
		display.rr_high_th_highriskI.setText(""+rr_high_th_highrisk);
		getObsProperty("temp_lower_th_highrisk").updateValue(temp_lower_th_highrisk);
		display.temp_lower_th_highriskI.setText(""+temp_lower_th_highrisk);
		getObsProperty("temp_high_th_risk").updateValue(temp_high_th_risk);
		display.temp_high_th_riskI.setText(""+temp_high_th_risk);
		getObsProperty("temp_high_th_highrisk").updateValue(temp_high_th_highrisk);
		display.temp_high_th_highriskI.setText(""+temp_high_th_highrisk);
		getObsProperty("deathTemp").updateValue(deathTemp);
		display.deathTempI.setText(""+deathTemp);
		getObsProperty("deathSbp").updateValue(deathSbp);
		display.deathSbpI.setText(""+deathSbp);
		getObsProperty("deathRr").updateValue(deathRr);
		display.deathRrI.setText(""+deathRr);
		getObsProperty("deathAcc").updateValue(deathAcc);
		display.deathAccI.setText(""+deathAcc);
		getObsProperty("deathHr").updateValue(deathHr);
		display.deathHrI.setText(""+deathHr);
		getObsProperty("shotSbp").updateValue(shotSbp);
		display.shotSbpI.setText(""+shotSbp);
		getObsProperty("shotSd").updateValue(shotSd);
		display.shotSdI.setText(""+shotSd);
		getObsProperty("shockSi").updateValue(shockSi);
		display.shockSiI.setText(""+shockSi);
		getObsProperty("hypothermia").updateValue(hypothermia);
		display.hypothermiaI.setText(""+hypothermia);
		getObsProperty("hyperthermia").updateValue(hyperthermia);
		display.hyperthermiaI.setText(""+hyperthermia);
		getObsProperty("hyperpyrexia").updateValue(hyperpyrexia);
		display.hyperpyrexiaI.setText(""+hyperpyrexia);
		getObsProperty("apnea").updateValue(apnea);
		display.apneaI.setText(""+apnea);
		getObsProperty("bradypnea").updateValue(bradypnea);
		display.bradypneaI.setText(""+bradypnea);
		getObsProperty("tachypnea_hypervent").updateValue(tachypnea_hypervent);
		display.tachypnea_hyperventI.setText(""+tachypnea_hypervent);
		getObsProperty("impaired_mental").updateValue(impaired_mental);
		display.impaired_mentalI.setText(""+impaired_mental);
		getObsProperty("unconsciousness").updateValue(unconsciousness);
		display.unconsciousnessI.setText(""+unconsciousness);		
	}
	
	@INTERNAL_OPERATION void cancelUpdateButton(ActionEvent ev) {
		updateFromObsProperties();
	}
	
	@OPERATION void updateFromObsProperties() {
		display.sbp_lower_th_highriskI.setText(""+getObsProperty("sbp_lower_th_highrisk").getValue().toString());
		display.sbp_lower_th_riskI.setText(""+getObsProperty("sbp_lower_th_risk").getValue().toString());
		display.sbp_high_th_riskI.setText(""+getObsProperty("sbp_high_th_risk").getValue().toString());
		display.sbp_high_th_highriskI.setText(""+getObsProperty("sbp_high_th_highrisk").getValue().toString());
		display.spo2_lower_th_highriskI.setText(""+getObsProperty("spo2_lower_th_highrisk").getValue().toString());
		display.spo2_lower_th_riskI.setText(""+getObsProperty("spo2_lower_th_risk").getValue().toString());
		display.hr_lower_th_highriskI.setText(""+getObsProperty("hr_lower_th_highrisk").getValue().toString());
		display.hr_lower_th_highriskI.setText(""+getObsProperty("hr_lower_th_highrisk").getValue().toString());
		display.hr_lower_th_riskI.setText(""+getObsProperty("hr_lower_th_risk").getValue().toString());
		display.hr_high_th_riskI.setText(""+getObsProperty("hr_high_th_risk").getValue().toString());
		display.hr_high_th_highriskI.setText(""+getObsProperty("hr_high_th_highrisk").getValue().toString());
		display.rr_lower_th_highriskI.setText(""+getObsProperty("rr_lower_th_highrisk").getValue().toString());
		display.rr_high_th_highriskI.setText(""+getObsProperty("rr_high_th_highrisk").getValue().toString());
		display.temp_lower_th_highriskI.setText(""+getObsProperty("temp_lower_th_highrisk").getValue().toString());
		display.temp_high_th_riskI.setText(""+getObsProperty("temp_high_th_risk").getValue().toString());
		display.temp_high_th_highriskI.setText(""+getObsProperty("temp_high_th_highrisk").getValue().toString());
		display.deathTempI.setText(""+getObsProperty("deathTemp").getValue().toString());
		display.deathSbpI.setText(""+getObsProperty("deathSbp").getValue().toString());
		display.deathRrI.setText(""+getObsProperty("deathRr").getValue().toString());
		display.deathAccI.setText(""+getObsProperty("deathAcc").getValue().toString());
		display.deathHrI.setText(""+getObsProperty("deathHr").getValue().toString());
		display.shotSbpI.setText(""+getObsProperty("shotSbp").getValue().toString());
		display.shotSdI.setText(""+getObsProperty("shotSd").getValue().toString());
		display.shockSiI.setText(""+getObsProperty("shockSi").getValue().toString());
		display.hypothermiaI.setText(""+getObsProperty("hypothermia").getValue().toString());
		display.hyperthermiaI.setText(""+getObsProperty("hyperthermia").getValue().toString());
		display.hyperpyrexiaI.setText(""+getObsProperty("hyperpyrexia").getValue().toString());
		display.apneaI.setText(""+getObsProperty("apnea").getValue().toString());
		display.bradypneaI.setText(""+getObsProperty("bradypnea").getValue().toString());
		display.tachypnea_hyperventI.setText(""+getObsProperty("tachypnea_hypervent").getValue().toString());
		display.impaired_mentalI.setText(""+getObsProperty("impaired_mental").getValue().toString());
		display.unconsciousnessI.setText(""+getObsProperty("unconsciousness").getValue().toString());	
	}

}

