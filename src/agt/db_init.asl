// Agent db_init in project military_health

+cmd("diag_eval_gui") : dbAgent(true).
+cmd("diag_eval_gui") : dbAgent(false)
	<-	.create_agent(db_agent,"db_agent.asl").
+cmd("diag_eval_gui_visible") :dbAgent(false).	
+cmd("diag_eval_gui_visible") :dbAgent(true)
	<- 	showGuiAgain.

+!createDatabaseArtifact : true
	<-	makeArtifact("MyDbConsole","tools.MyDatabaseConsole",["DatabaseGUI "],Art_Id);
		focus(Art_Id); .wait(2000);
		!checkRunningAndStartDb.
//check if this actually works, the [_,_,_] variant might be better ....
-!createDatabaseArtifact [makeArtifactFailure("artifact_already_present",_)]
	<- 	lookupArtifact("MyDbConsole", Art_Id);
		focus(Art_Id); .wait(2000);
		!checkRunningAndStartDb.

+!checkRunningAndStartDb : dbRunning(false)
	<- 	.wait(2000);
		startDb.
+!checkRunningAndStartDb : dbRunning(true).
		
+!getMissionId(TeamName) <- .wait(2000); .print("gettingMissionId ...."); getMaxMissionId(TeamName).

+cmd("missionId",MissionId) <- +missionId(MissionId).

/* signal_plans for handling db_console events, +cmd("putDiags") is handled in diagnose_init for code_duplication reasons */
+cmd("abolish_diagnoses") : dbAgent(true)
	<- .abolish(diagnosed(_,_,_,_,_,_,_)).
+cmd("abolish_diagnoses").

+cmd("diagnoses", Kind, Status, Values, Flag, AgentString, TName, TimeStamp)  : dbAgent(true)
	<- +diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp).
+cmd("diagnoses", Kind, Status, Values, Flag, AgentString, TName, TimeStamp)  : dbAgent(false).

