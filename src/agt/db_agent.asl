// Agent db_agent in project military_health

/* Initial beliefs and rules */
role("ccAgent").
teamName("team1").
thresholdVisible(false).
costsVisible(true).
diagnoseVisible(true).
dbAgent(true).
/* Initial goals */

!start.

/* Plans */

+!start : true 
	<- .print("hello world.");
		joinWorkspace("ccAgent",Wsp_Id); 
		!createDiagnoseArtifact;
		lookupArtifact("MyDbConsole",Art_Id);
		focus(Art_Id).

{ include("diagnose_init.asl") }
{ include("flag_and_diagnose.asl") }
/* needs db_init for receiving signals from db_artefact for displaying in own DiagnoseGui */
{ include("db_init.asl") }

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
