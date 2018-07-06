// Agent cost_sim_agent in project military_health

/* Initial beliefs and rules */
role("ccAgent").
teamName("team1").
thresholdVisible(false).
costsVisible(true).
diagnoseVisible(true).
isSimAgent(true).

/* Initial goals */

!start.

/* Plans */

+!start : true 
	<- 	.print("hello world.");
		!createMyWorkspace;
		!createCostArtifact;
		!createDiagnoseArtifact.

+received_hello[source(Sender)] : .my_name(Me) <- .print("received from ", Sender);-received_hello[source(Sender)]; .send(Sender,tell,cost_simAg_Ack(Me)).

{ include("diagnose_init.asl") }
{ include("flag_and_diagnose.asl") }
{ include("costs_init.asl") }
{ include("costs_diag.asl") }

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
