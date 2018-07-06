// CArtAgO artifact code for project military_health

package tools;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SensorDisplay extends JFrame {
	
	private static final long serialVersionUID = 2681641118711017891L;
	public JButton setSensorValue, setSensorOnline, setSensorOffline, setSensorHealthOk, setSensorHealthNok;
	public JButton setSensorReadingsOk, setSensorReadingsNok, setCalibrationValue;
	public JTextArea text; 
	public JTextField valueToInput, calibrationValueToInput;
	public JLabel sensorStatus, sensorHealth ,sensorReadings, sensorValueDisplay, calibrationValueDisplay, regressionEnabled; 
	
	public SensorDisplay(String title, String measuringUnit, String type, Double initialValue, Double calibrationValue) {
		setTitle(".:: "+title+" Sensor-CONSOLE ::.");
		setSize(540,240);
		JPanel panel = new JPanel();
		
		JPanel p0 = new JPanel();
		p0.add(new JLabel("SensorType: "+ type));
		//p6.add(new JLabel("ObsSensorValue(displayed) is sum Measured + Calibration values"));
		panel.add(p0);
		
		setContentPane(panel);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		// sub_panel of 'panel' for sensor values + status
		JPanel p1 = new JPanel();

		
		setSensorValue = new JButton("setSensorValue");
		p1.add(setSensorValue);
		valueToInput = new JTextField(6);
		double inputDisplayValue = initialValue - calibrationValue;
		valueToInput.setText(""+inputDisplayValue);
		p1.add(valueToInput);
		p1.add(new JLabel("<html><p style=\"color:red\"><b>Corrected Value : </b></p></html>"));
		sensorValueDisplay = new JLabel(""+initialValue);
		p1.add(sensorValueDisplay);
		p1.add(new JLabel(measuringUnit));
		panel.add(p1);
		
		// calibration values for sensor adjustments, either via GUI or AgentSpeak
		JPanel p2 = new JPanel();
		setCalibrationValue = new JButton("setCalibrationValue");
		p2.add(setCalibrationValue);
		calibrationValueToInput = new JTextField(6);
		calibrationValueToInput.setText(""+calibrationValue);
		p2.add(calibrationValueToInput);
		p2.add(new JLabel("Calibration Value : "));
		calibrationValueDisplay = new JLabel(""+calibrationValue);
		p2.add(calibrationValueDisplay);
		p2.add(new JLabel(measuringUnit));
		panel.add(p2);
		
		JPanel p3 = new JPanel();
		// Labels + actual value
		setSensorOnline = new JButton("Online");
		p3.add(setSensorOnline);
		setSensorOffline = new JButton("Offline");
		p3.add(setSensorOffline);
		p3.add(new JLabel("OnlineStatus : "));
		sensorStatus = new JLabel("Online");
		p3.add(sensorStatus);
		regressionEnabled = new JLabel(", Regression_Enabled: No");
		p3.add(regressionEnabled);
		panel.add(p3);
		
		JPanel p4 = new JPanel();
		setSensorHealthOk = new JButton("Sensor_Ok");
		p4.add(setSensorHealthOk);
		setSensorHealthNok = new JButton("Sensor_Not_Ok");
		p4.add(setSensorHealthNok);
		p4.add(new JLabel("SensorHealth : "));
		sensorHealth = new JLabel("Ok");
		p4.add(sensorHealth);
		panel.add(p4);

		
		JPanel p5 = new JPanel();
		setSensorReadingsOk = new JButton("Readings_Ok");
		p5.add(setSensorReadingsOk);
		setSensorReadingsNok = new JButton("Readings_Not_Ok");
		p5.add(setSensorReadingsNok);
		p5.add(new JLabel("SensorReadings : "));
		sensorReadings = new JLabel("Ok");
		p5.add(sensorReadings);		
		panel.add(p5);
		
		
	}
	
}

