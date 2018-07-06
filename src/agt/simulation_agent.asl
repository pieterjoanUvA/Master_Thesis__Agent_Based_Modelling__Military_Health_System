// Agent simulation_agent in project military_health

/* Initial beliefs and rules */
simulationWorkspace("Workspace").
/* Initial goals */

!start.

/* Plans */

+!start : simulationWorkspace(WorkspaceName) & .my_name(Me)
	<- 	.print("hello world.");
		.concat(Me,"_",WorkspaceName, WspName)
		createWorkspace(WspName); .wait(400);
		joinWorkspace(WspName,Wsp_Id); 
		+myCreatedWorkspace(WspName,Wsp_Id);
		!createSimulationArtifact.
		
+!createSimulationArtifact : myCreatedWorkspace(WorkspaceName,Wsp_Id) & teamName(TName) & .my_name(Me)
	<-	.concat(Me,"_",TName, Art_Name);
		makeArtifact(Art_Name,"tools.SimulationConsole",[TName,0,true],Art_Id); .wait(400);
		focus(Art_Id);
		+mySimArtifact(Art_Name,WorkspaceName).

+symptomAgent(Ag) : notifiedSymptomAgent(Ag) <- -symptomAgent(Ag)[source(Ag)].
+symptomAgent(Ag) <- !notifySymptomAgent(Ag).

+currentStage(Stage) 
	<-	.findall(Ag,notifiedSymptomAgent(Ag),L);
		!signalAllSymptomAgents(L,Stage).

+!notifySymptomAgent(Ag) : mySimArtifact(SimulationArtifact_Name,Sim_Wsp_Name)
	<- .send(Ag,tell,mySimArtifact(SimulationArtifact_Name,Sim_Wsp_Name));
		-symptomAgent(Ag)[source(_)];
		+notifiedSymptomAgent(Ag).
+!notifySymptomAgent(Ag) <- .wait(1000); !notifySymptomAgent.

+!signalAllSymptomAgents([],_).
+!signalAllSymptomAgents([Ag|T],Stage)
	<- 	.send(Ag,tell,setCurrentStage(Stage));
		!signalAllSymptomAgents(T,Stage).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
