// Agent diagnose_init in project military_health

+!createMyWorkspace : role(Role) & Role == ("ccAgent")
	<-	createWorkspace(Role);
		joinWorkspace(Role,Wsp_Id).
-!createMyWorkspace [_,_,_] : role(Role)
	<- 	joinWorkspace(Role,Wsp_Id).
	
+!createMyWorkspace <- .my_name(Me);
		.concat(Me,"_",WorkspaceName);
		createWorkspace(WorkspaceName);
		joinWorkspace(WorkspaceName,Wsp_Id);
		+myCreatedWorkspace(WorkspaceName,Wsp_Id).
		
+!createDiagnoseArtifact <-
	.my_name(Me);
	!checkCcAgent; //for costs visibility
	!checkDiagnoseVisibility;
	!checkSimAgent;
	!checkDbAgent;
	?dbAgent(DbAgentValue)
	?isSimAgent(Simulation)
	?teamName(TName);
	?isCcAgent(CostsVisibility);
	?diagnoseVisible(DiagVisible);
	.concat(Me," ", TName,"_DiagnoseConsole",DiagTitle);
	makeArtifact(DiagTitle,"tools.DiagnoseConsole",[DiagTitle,CostsVisibility,DiagVisible,Simulation,DbAgentValue],MyDiagId);
	focus(MyDiagId);
	.print("Diagnose GuiArtifact created").
	
+!checkCcAgent : role(X) & X == ("ccAgent") <- +isCcAgent(true).
+!checkCcAgent <- +isCcAgent(false).	
+!checkSimAgent : isSimAgent(X) & X == (true).
+!checkSimAgent <- +isSimAgent(false).
+!checkDbAgent : dbAgent(true).
+!checkDbAgent <- +dbAgent(false).

+!checkDiagnoseVisibility : diagnoseVisible(false) <- true.
+!checkDiagnoseVisibility <- +diagnoseVisible(true).



+cmd("setRadioSilence", Bool) : isCcAgent(X) & X == (true)
	<- ?teamAgent(Ag);
		.send(Ag,tell,cmd("notifyRadioSilence", Bool)).
+cmd("setRadioSilence", Bool) : isCcAgent(X) & X == (false)
	<- ?ccAgent(Ag);
		.send(Ag,tell,cmd("notifyRadioSilence", Bool)).
+cmd("notifyRadioSilence", Bool) <- toggleStatusWithoutNotify(Bool);
	-cmd("notifyRadioSilence", Bool)[source(_)].

/* Retreive Diagnosed Symptoms and Ailments for GuiArtifact display (right side, agent specific */
+cmd("getDiags", AgentString) :  mostRecentAck(AgentString, TimeStamp, TeamName) 
<- 	jia.myTime(CurrTime);
	printDiagTimeStamp(TimeStamp, CurrTime); /* prints age in GUI */
	/* get results search based on mostRecentAck */
	.findall(diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),
			diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),L) ;
	!setDiagnoseArtifact(L).

	
/* failure handling for agent with no mostRecentAck */	
+cmd("getDiags", AgentName).
/* setting each individual diagnose for Agent X in Gui from list(L) */
+!setDiagnoseArtifact([]).
+!setDiagnoseArtifact([diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp)|T]) 
	<- printDiag(Kind,Status,Values,Flag);
	!setDiagnoseArtifact(T).

/* dbAgent putDiags Gui plans ==> this command is initiated from MyDatabaseConsole !!!! */
/* other db_console signal_event plans are in db_init.asl */
+cmd("putDiags", AgentString, TimeStamp, TeamName, MissionId) : dbAgent(true)
	<-	printDiagnoseHeader(AgentString, TimeStamp, TeamName, MissionId);
		.findall(diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),
			diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),L) ;
		!setDiagnoseArtifact(L).
+cmd("putDiags", AgentString, TimeStamp, TeamName, MissionId) : dbAgent(false).