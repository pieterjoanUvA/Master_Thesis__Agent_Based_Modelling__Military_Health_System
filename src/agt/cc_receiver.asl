// Agent cc_receiver in project military_health

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : teamName(TeamName) <- .print("hello world.");
		!createMyWorkspace;
		!createDatabaseArtifact;	
		!createThresholdArtifact;
		!createCostArtifact;
		!createDiagnoseArtifact;
		!!getMissionId(TeamName);.

{ include("diagnose_init.asl") }	
{ include("flag_and_diagnose.asl") }
{ include("threshold_init.asl") }
{ include("costs_init.asl") }
{ include("costs_diag.asl") }
{ include("costs_sim_init.asl") }
{ include("db_init.asl") }
	
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

+bodyWorkspaceCreated(Wsp_Name,Ag,_,_)[source(_)]<- -bodyWorkspaceCreated(Wsp_Name,Ag,_,_)[source(_)].
// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
