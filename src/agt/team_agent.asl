// Agent team_agent in project military_health

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("hello world.");
		!createMyWorkspace;
		!createThresholdArtifact;
		!createDiagnoseArtifact;
		/* send my name to ccAgent */
		.my_name(Me); ?ccAgent(Ag);
		.send(Ag,tell,teamAgent(Me)).

/* Deferred Sending When sendingMsg in Artifact is called by Agent */
+cmd("sendToCC",Kind,Status,Values,Flag,AgentName,TeamName,TimeStamp) : ccAgent(Ag)
	<- 	.send(Ag,tell, diagnosed(Kind,Status,Values,Flag,AgentName,TeamName,TimeStamp) ).

{ include("diagnose_init.asl") }
{ include("flag_and_diagnose.asl") }
{ include("threshold_init.asl") }

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
