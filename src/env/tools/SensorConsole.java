// CArtAgO artifact code for project military_health

package tools;

import org.apache.commons.math3.stat.regression.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import cartago.*;
import cartago.tools.*;

public class SensorConsole extends GUIArtifact {
	
	private SensorDisplay display;
	double initialValue = 0.0; // initialValue of sensor if not given in constructor, thus ArtifactInitialisation.
	double lowerBound, upperBound; 
	double calibrationValue = 0.0 ;
	ArrayList<Double> myRegressionValues = new ArrayList<Double>();
	String title;

	void init( String title, double initialValue, String measuringUnit, double calibrationValue, String type, Boolean visible) {
		String sensorHealth= "Ok";
		String sensorReadings="Ok";
		String sensorStatus="Online";
		String regressionEnabled="No";
		
		defineObsProperty("sensorHealth", sensorHealth);
		defineObsProperty("sensorReadings", sensorReadings);
		defineObsProperty("sensorStatus", sensorStatus);
		defineObsProperty("sensorValue", initialValue);
		defineObsProperty("calibrationValue", calibrationValue);
		defineObsProperty("sensorType", type);
		defineObsProperty("regressionEnabled", regressionEnabled);
		
		display = new SensorDisplay(title, measuringUnit, type, initialValue, calibrationValue); //create Gui Panel
		linkActionEventToOp(display.setSensorHealthNok, "sensorHealthNok");
		linkActionEventToOp(display.setSensorHealthOk, "sensorHealthOk");
		linkActionEventToOp(display.setSensorOffline, "sensorOffline");
		linkActionEventToOp(display.setSensorOnline, "sensorOnline");
		linkActionEventToOp(display.setSensorReadingsNok, "sensorReadingsNok"); //PENDING status
		linkActionEventToOp(display.setSensorReadingsOk, "sensorReadingsOk");
		
		linkActionEventToOp(display.setSensorValue, "setSensorValueButton");
		linkKeyStrokeToOp(display.valueToInput,"ENTER","setSensorValueButton"); //enables ENTER in textfield action
		linkActionEventToOp(display.setCalibrationValue, "setCalibrationValueButton");
		linkKeyStrokeToOp(display.calibrationValueToInput, "ENTER", "setCalibrationValueButton");
		/* Thesis demonstration location settings */
		if ( title.equals("sensor_agent42s1_sensorTemp1")) {
			display.setLocation(640, 0);
			
		} else if ( title.equals("sensor_agent42s2_sensorTemp2")) {
			display.setLocation(1200, 0);
			
		}
		
		display.setVisible(visible);
		this.title = title;
		this.init();

		setCalibrationValue(calibrationValue);	
	}

	  @OPERATION void getAlive(OpFeedbackParam<String> isAlive) {
		  String currLivelyness = "Alive";
		  isAlive.set(currLivelyness);
	  }
	@OPERATION void toggleRegression() {
		String regressionStatus = getObsProperty("regressionEnabled").getValue().toString();
		if (regressionStatus.equals("Yes")) {
			getObsProperty("regressionEnabled").updateValue("No");
			display.regressionEnabled.setText(", Regression_Enabled: No");
		} else if (regressionStatus.equals("No")) {
			getObsProperty("regressionEnabled").updateValue("Yes");
			display.regressionEnabled.setText(", Regression_Enabled: Yes");
		}
	}
	@OPERATION void setSensorHealth(String value) {
	    	display.sensorHealth.setText(value);
	    	getObsProperty("sensorHealth").updateValue(value);
	}
	@OPERATION void setSensorReadings(String value) {
		if (value.equals("Unreliable") || value.equals("Ok") || value.equals("Broken")) {
	    	display.sensorReadings.setText(value);
	    	getObsProperty("sensorReadings").updateValue(display.sensorReadings.getText());
		}
	}
	@OPERATION void setSensorStatus(String value) {
		if (value.equals("Offline")  || value.equals("Online") ) {
	    	display.sensorStatus.setText(value);
	    	getObsProperty("sensorStatus").updateValue(display.sensorStatus.getText());
		}
	}
	@OPERATION void setSensorValue(double value) {
		String regressionStatus = getObsProperty("regressionEnabled").getValue().toString();
		if (regressionStatus.equals("No")) {
	    	display.sensorValueDisplay.setText(String.valueOf(value));
	    	double new_input = value - Double.parseDouble(display.calibrationValueDisplay.getText());
	    	display.valueToInput.setText(""+new_input);
	    	getObsProperty("sensorValue").updateValue(value);
		} else if (regressionStatus.equals("Yes")) {
			// do regression validation function to update sensorHealth value
			boolean setSensorReadingBool = evalRegression(value);
			if (setSensorReadingBool == false) {
				setSensorReadings("Unreliable");
			} else if (setSensorReadingBool == true) {
				setSensorReadings("Ok");
			}
	    	display.sensorValueDisplay.setText(String.valueOf(value));
	    	double new_input = value - Double.parseDouble(display.calibrationValueDisplay.getText());
	    	display.valueToInput.setText(""+new_input);
	    	getObsProperty("sensorValue").updateValue(value);
		}
	}
	boolean evalRegression(double value) {
		int toPredictCounter = 0;
		double stdErr = 0;
		double myPrediction = 0;
		double myLastValue = 0;
		double minThreshold = 2;
		boolean validValue = false;

		SimpleRegression regression = new SimpleRegression();
		/* for minimum statistics enabling and fill array with some variance */
		if (myRegressionValues.isEmpty() | myRegressionValues.size() == 0) {
			for ( int i = 0 ; i < 15 ; i++) {
				myRegressionValues.add(value + Math.random());
				myRegressionValues.add(value - Math.random());
			}
		}
		
		for(int i = 0 ; i < myRegressionValues.size() ; i++) {
			regression.addData(i, myRegressionValues.get(i));
			toPredictCounter++;
			myLastValue = myRegressionValues.get(i);
		}
		toPredictCounter++;
		myPrediction = regression.predict(toPredictCounter);
		//System.out.println("predicted "+title +" "+myPrediction+" setValue "+ value);
		stdErr = regression.getSlopeStdErr();
		double rawDifference = value - myPrediction;
		if(rawDifference < 0) {
			rawDifference = rawDifference * -1;
		}
		double diffLastCurrentValue =  value - myLastValue;
		if(diffLastCurrentValue < 0) {
			diffLastCurrentValue = diffLastCurrentValue * -1;
		}
		/* prediction value difference handling */
		if (rawDifference < stdErr | rawDifference < minThreshold ) {
			validValue = true;
		}
		/* last value- curr value and statistical stdErr-of-the-slope */
		if (diffLastCurrentValue < stdErr | diffLastCurrentValue < minThreshold ) {
			validValue = true;
		}
		/* dynamic threshold calculation and evaluation */
		double dynamicThreshold = myLastValue - myPrediction;
		if(dynamicThreshold <0) {
			dynamicThreshold = dynamicThreshold * -1;
		}
		dynamicThreshold = dynamicThreshold + stdErr;
		if (rawDifference < dynamicThreshold | diffLastCurrentValue < dynamicThreshold) {
			validValue = true;
		}
		/* All guarantee that a value is quite close to the last value */
		
		/* remove oldest reading from list and add the last one, regardless of valid */
		/* just the status changes, and could create a full reset of valid measurements otherwise */
		/* therefore, consistent changes will propagate over time */
		/* the slope gradually changes, and after a period of unreliable readings, */
		/* when steady values appear, valid measurements are evaluated */
		myRegressionValues.remove(0);
		myRegressionValues.add(value);
		
		return validValue;
	}
	
	@OPERATION void setCalibrationValue(double calibrationValue) {
		double v =  Double.parseDouble(display.valueToInput.getText()) + calibrationValue;
		display.calibrationValueDisplay.setText(String.valueOf(calibrationValue));
		getObsProperty("calibrationValue").updateValue(calibrationValue);
		getObsProperty("sensorValue").updateValue(v); //displayed_measured value must also be updated
		display.sensorValueDisplay.setText(""+v);
 

	}
	
    @INTERNAL_OPERATION void sensorHealthNok(ActionEvent ev){
    	display.sensorHealth.setText("Nok");
    	getObsProperty("sensorHealth").updateValue(display.sensorHealth.getText());
    }
    @INTERNAL_OPERATION void sensorHealthOk(ActionEvent ev){
    	display.sensorHealth.setText("Ok");
    	getObsProperty("sensorHealth").updateValue(display.sensorHealth.getText());
    }
    @INTERNAL_OPERATION void sensorOffline(ActionEvent ev){
    	display.sensorStatus.setText("Offline");
    	getObsProperty("sensorStatus").updateValue(display.sensorStatus.getText());
    }
    @INTERNAL_OPERATION void sensorOnline(ActionEvent ev){
    	display.sensorStatus.setText("Online");
    	getObsProperty("sensorStatus").updateValue(display.sensorStatus.getText());
    }    
    @INTERNAL_OPERATION void sensorReadingsNok(ActionEvent ev){
    	display.sensorReadings.setText("Unrealiable");
    	getObsProperty("sensorReadings").updateValue(display.sensorReadings.getText());
    } 
    @INTERNAL_OPERATION void sensorReadingsOk(ActionEvent ev){
    	display.sensorReadings.setText("Ok");
    	getObsProperty("sensorReadings").updateValue(display.sensorReadings.getText());
    } 
    
    @INTERNAL_OPERATION void setSensorValueButton(ActionEvent ev) {
    	double a = Double.parseDouble(display.calibrationValueToInput.getText());
    	double b = Double.parseDouble(display.valueToInput.getText());
    	double v = a + b;
    	display.sensorValueDisplay.setText(String.valueOf(v));
    	getObsProperty("sensorValue").updateValue(v);
    }
    @INTERNAL_OPERATION void setCalibrationValueButton(ActionEvent ev) {
    	double v = Double.parseDouble(display.calibrationValueToInput.getText());
    	display.calibrationValueDisplay.setText(String.valueOf(v));
    	getObsProperty("calibrationValue").updateValue(v);
    	// Current Sensor Value has to be updated after Calibration Change to be consistent
    	double a = Double.parseDouble(display.valueToInput.getText());
    	double b = v + a;
    	display.sensorValueDisplay.setText(String.valueOf(b));
    	getObsProperty("sensorValue").updateValue(b);
    }
}

