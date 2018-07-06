// Agent sensor_agent in project military_health

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- 
			.print("hello world.");
			!waitForBodyArtifact.
			
+!waitForBodyArtifact : bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName) & bodyName(BName) & teamName(TName)
		<- 	.wait(math.random(200)+247);
			+myBodyAgent(BodyAgent);
			.wait(math.random(400)+284);
			joinWorkspace(Wsp_Name,Wsp_Id);
			!convertStringToDouble;
			.wait(math.random(400)+120);
			!createSensorArtifacts.				
 
+!waitForBodyArtifact : bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName) 
		<- 	-bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)];
			!waitForBodyArtifact.
+!waitForBodyArtifact <- .wait(1000); !waitForBodyArtifact.
-!waitForBodyArtifact <- .wait(1000); !waitForBodyArtifact.

+!convertStringToDouble : 	.findall(calibrationValue(SensorName,CaliValue,MeasurementUnit,InitValue,SensorType),
							calibrationValue(SensorName,CaliValue,MeasurementUnit,InitValue,SensorType),L) 
							& .length(L,Length) & Length > 0
	<-	!convertIndividualBeliefs(L).
+!convertStringToDouble.

+bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)] : bodyName(BName).
+bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)] <- -bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)].

+!convertIndividualBeliefs([]).
+!convertIndividualBeliefs([calibrationValue(SensorName,CaliValue,MeasurementUnit,InitValue,SensorType)|T])
	<- 	cartago.invoke_obj("java.lang.Double",parseDouble(CaliValue),X);
		cartago.invoke_obj("java.lang.Double",parseDouble(InitValue),Y);
		-calibrationValue(SensorName,CaliValue,MeasurementUnit,InitValue,SensorType)[source(_)];
		+calibrationValue(SensorName,X,MeasurementUnit,Y,SensorType);
		!convertIndividualBeliefs(T).

+!createSensorArtifacts : calibrationValue(SensorName,CaliValue,MeasurementUnit,InitValue,SensorType) 
	<-	.my_name(Ag);
		.concat(Ag,"_",Tmp);
		.concat(Tmp,SensorName,Art_Name);
		.wait(math.random(442));
		!getSensorTypeVisibility(SensorType);
		?sensorVisible(SensorVisibility);
		+sensoriName(Art_Name,SensorType); 
		.wait(math.random(1042)+401);
		makeArtifact(Art_Name,"tools.SensorConsole",[Art_Name,InitValue,MeasurementUnit,CaliValue,SensorType,SensorVisibility],Art_Id); 
		/*  true/false in Artifact[parameterList] set visibility of sensorArtifacts  */
				-calibrationValue(SensorName,_,_,_,_);  
	 	/* send to symptom agent of body(X) */
	 	?symptomAgent(MySymptomAgent);
	 	.wait(math.random(440)+140);
	 	!!notifySymptomAgent(MySymptomAgent,Art_Name,SensorType);
	 	!!focusAndDisableBrokenSensors(SensorName,Art_Id); //config_file specified SensorName with disableBroken(SensorName) given Belief
	 	!createSensorArtifacts.
+!createSensorArtifacts : true <- .print("all artifacts created").
-!createSensorArtifacts <- .wait(1000); .print("Sensor Artefact -- hang failure"); !createSensorArtifacts .
		//true. //when all sensor calibrationValues are out of the BB --> 
		//calibrationValue is an obligatory belief per sensorAgent for initialisation of sensorArtifacts.
-!createSensorArtifacts : sensoriName(ArN,SensorType) <- .print("ArtifactCreationErrors Occured ", SensorType).

+!getSensorTypeVisibility(SensorType) : sensorTypeVisible(SensorVisibility,SensorType) <- -sensorVisible(_)[source(_)]; +sensorVisible(true).
+!getSensorTypeVisibility(SensorType) <- -sensorVisible(_)[source(_)]; +sensorVisible(false).

+!getSensorVisibilityBelief : sensorVisible(true).
+!getSensorVisibilityBelief : sensorVisible(false).
+!getSensorVisibilityBelief <- +sensorVisible(true).

/* Gui Init delays cause unstable startuptimes, this value is essential for Simulation,
 * Possibly create a loop. Belief is for debugging purposes.
 */
+!notifySymptomAgent(MySymptomAgent,Art_Name,SensorType) : symptomSensorAck(Art_Name,SensorType).
+!notifySymptomAgent(MySymptomAgent,Art_Name,SensorType) 
	<- 	.wait(2000); .send(MySymptomAgent,tell,sensorNameTemp(Art_Name,SensorType));
		//+toldSymptomAgent(MySymptomAgent," of ",Art_Name," and ", SensorType)
		!notifySymptomAgent(MySymptomAgent,Art_Name,SensorType).
-!notifySymptomAgent(MySymptomAgent,Art_Name,SensorType) <- .wait(2000); !notifySymptomAgent(MySymptomAgent,Art_Name,SensorType).
// disableBroken(SensorName) given in .jcm initial beliefs is a config option to disable known bad sensors in a sensor_agent_package
+!focusAndDisableBrokenSensors(SensorName, Art_Id) : disableBroken(SensorName) 
	<- 	.wait(880);
		focus(Art_Id);
		.wait(4000);	//pause for GUI creation delay, before artifact functions can be called.
		setSensorHealth("Nok")[artifact_id(Art_Id)];
		-disableBroken(SensorName)[source(_)].
+!focusAndDisableBrokenSensors(SensorName, Art_Id)
	<-	.wait(880);
		focus(Art_Id). 

//percept change plans
+sensorValue(X)[artifact_name(Id,Name)] 
		<- jia.myTime(Time); 
		!evalValidMeasurement(X,Id,Name,Time).

// Validation of sensor status and measurements		
+!evalValidMeasurement(X,Id,Name,Time) : sensorHealth("Ok")[artifact_name(Id,Name)] & sensorReadings("Ok")[artifact_name(Id,Name)]
		& sensorStatus("Online")[artifact_name(Id,Name)]
		<- 	.wait(66);
			getAlive(Ali)[artifact_id(Id)];
			.wait(66);
			!checkAliveSensor(Id,Ali,Name);
			!updateBeliefBodyAgent(X,Id,Name,Time).
+!evalValidMeasurement(X,Id,Name,Time).
-!evalValidMeasurement(X,Id,Name,Time).

+!checkAliveSensor(Id,A,Name) : A == ("Alive") 
	<- -activeSensor(Name,Id,_)[source(_)];
		+activeSensor(Name,Id,A).
+!checkAliveSensor(Id,A,Name) : true 
	<- 	-activeSensor(Name,Id,_)[source(_)]; 
		+activeSensor(Name,Id,"MissingResult").

+!updateBeliefBodyAgent(X,Id,Name,Time) : myBodyAgent(Ag) & sensorType(SensorType)[artifact_name(Id,Name)] & activeSensor(Name,Id,"Alive")
	<-	.wait(44+math.random(100)); .wait(44+math.random(100));
		.send(Ag,tell,vM(X,Name,Time,Id,SensorType)).
-!updateBeliefBodyAgent(X,Id,Name,Time) <- .print("BodyAgent Unavailable").

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
