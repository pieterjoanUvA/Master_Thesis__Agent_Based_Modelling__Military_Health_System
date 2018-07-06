// Agent symptom_agent in project military_health

/* Initial beliefs and rules */
sim_agent(simulation_agent).
/* Initial goals */

!start.

/* Plans */

+!start :  bodyName(BName) & teamName(TName) <- 
			.print("hello world.");
			.wait(4000);
			!!waitForBodyArtifact.

+!waitForBodyArtifact : bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName) & bodyName(BName) & teamName(TName)
	<- 	+myBodyAgent(BodyAgent);
		joinWorkspace(Wsp_Name,Wsp_Id);
		!!registerWithSimulationAgent;
		.wait(1000);
		!checkArtifactVisible;
		!createSymptomArtifacts;
		+setCurrentStage(0).

+!waitForBodyArtifact : bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName) 
		<- 	-bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)];
			!waitForBodyArtifact.
+!waitForBodyArtifact <- .wait(1000);  !waitForBodyArtifact.
-!waitForBodyArtifact <- .wait(1000);  !waitForBodyArtifact.

+!checkArtifactVisible	: 	symptomVisible(X).
+!checkArtifactVisible <- 	+symptomVisible(false).

+bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)] : bodyName(BName).
+bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)] <- -bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)].
+!createSymptomArtifacts : symptomVisible(ArtVisible)
	<-	.my_name(Ag);
		.concat(Ag,"_Symptom",Art_Name);
		+sensoriName(Art_Name); .wait(400);
		makeArtifact(Art_Name,"tools.SymptomConsole",[Art_Name,Ag,ArtVisible],Art_Id);
		focus(Art_Id);
	 	.print("created: ",Art_Name).
-!createSymptomArtifacts : sensoriName(Art_Name) <- .print("ArtifactCreationErrors Occured ", Art_Name).

+!registerWithSimulationAgent : sim_agent(MySimAg) & mySimArtifact(SimulationArtifact_Name,Sim_Wsp_Name).

+!registerWithSimulationAgent : .my_name(Me) & sim_agent(MySimAg)
	<- .send(MySimAg,tell,symptomAgent(Me))
		.wait(1000);
		!registerWithSimulationAgent.

+cmd("setValue", SensorType , SensorValue)
	<- 	.findall(sensorName(Name,SensorType),sensorName(Name,SensorType)[source(Ag)],L);
		!setSensorValues( L, SensorValue).
		
+sensorNameTemp(Art_Name,SensorType)[source(Sender)] 
	<- 	.send(Sender,tell,symptomSensorAck(Art_Name,SensorType));
		-sensorNameTemp(Art_Name,SensorType)[source(_)];
		-sensorName(Art_Name,SensorType)[source(_)]; 
		+sensorName(Art_Name,SensorType).
		
/* for simulation agent message handling */
									/* for startup issues check. */
+!setValue(SensorType,SensorValue) : bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName) & bodyName(BName) & teamName(TName)
	<- 	.findall(sensorName(Name,SensorType),sensorName(Name,SensorType)[source(Ag)],L);
		!!setSensorValues( L, SensorValue). //throw on stack try
	
+!setSensorValues([],_). 
+!setSensorValues([sensorName(Name,Type)|T],SensorValue) 
	<- 	lookupArtifact(Name,ArtId); 
		setSensorValue(SensorValue)[artifact_id(ArtId)]; 
		!setSensorValues(T,SensorValue);.
+!setSensorValues([sensorName(Name,Type)|T],SensorValue).
-!setSensorValues([sensorName(Name,Type)|T],SensorValue).

{ include("symptom_sim_profiles.asl") }	

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
