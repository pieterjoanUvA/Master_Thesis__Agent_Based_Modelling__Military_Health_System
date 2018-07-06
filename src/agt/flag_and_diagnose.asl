// Agent flag_and_diagnose in project military_health
/* To ensure that each agent has only one 'mostRecent' belief 
 * Flag Triage colour setting according to highest found flag */
 

+mostRecent(AgentString, TimeStamp, TeamName)
	<-	!flagDiagnoses(AgentString, TimeStamp, TeamName).

+!flagDiagnoses(AgentString, TimeStamp, TeamName) 
	<-	/* remove Ack's indifferent of age or event */
		-mostRecent(AgentString, TimeStamp, TeamName)[source(_)]; //remove perceived event
		.abolish(mostRecentAck(AgentString,_,_));
		+mostRecentAck(AgentString, TimeStamp, TeamName);
		/* store Acks to db */
		!storeMostRecentAckInDb(AgentString, TimeStamp, TeamName);
		.findall(diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),
			diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),LF);
		/* .abolish with non-existend arity/number_of_arguments causes delays in plans */
		.abolish(agentFlag(AgentString,_,_,_)); // agentFlag(AgentString,FinalFlag,TempFlag,TimeStamp)
		+agentFlag(AgentString,"No_Flag",0,TimeStamp);
		!!saveForDeferredSending(LF, AgentString, TeamName, TimeStamp);
		!agentFlagHighest(LF);// updates agentFlag(<belief>)
		!setFlagColour(AgentString);
		/* find all diagnoses of this agent older/other than timestamp and prune BB */
		.findall(diagnosed(Kind,Status,Values,Flag,AgentString,TName,MyTimeStamp),
			diagnosed(Kind,Status,Values,Flag,AgentString,TName,MyTimeStamp),L);
		!!pruneDiagnoses(L,TimeStamp);
		?agentFlag(AgentString,FlagColour,FlagInt,TimeStamp);
		jia.myTime(CurrTime);
		/* display this Agent in AgentList (left side) with flag and age_of_diag */
		printMsg(AgentString,FlagColour, TimeStamp, CurrTime).

/* plans for handling a single simulation */		
+mostRecentSim(AgentString, TimeStamp, TeamName)
	<-	!flagDiagnoseSim(AgentString, TimeStamp, TeamName).

@simFlag[atomic]
+!flagDiagnoseSim(AgentString, TimeStamp, TeamName) 
	<-	-mostRecentSim(AgentString, TimeStamp, TeamName)[source(_)];
		.abolish(mostRecentAck(AgentString,_,_));
		+mostRecentAck(AgentString, TimeStamp, TeamName);
		.findall(diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),
			diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),LF);
		.abolish(agentFlag(AgentString,_,_,_));
		+agentFlag(AgentString,"No_Flag",0,TimeStamp);
		!agentFlagHighest(LF);
		!setFlagColour(AgentString);
		?agentFlag(AgentString,FlagColour,FlagInt,TimeStamp);
		jia.myTime(CurrTime);
		printMsg(AgentString,FlagColour, TimeStamp, CurrTime).

/* save for deferred sending in Artifact */
+!saveForDeferredSending([], AgentString, TeamName, TimeStamp)  : isCcAgent(false) & radioSilence(true).
+!saveForDeferredSending([], AgentString, TeamName, TimeStamp)  : isCcAgent(false) & radioSilence(false) <-  !startDeferredSendingSingle(AgentString, TimeStamp, TeamName).
+!saveForDeferredSending([], AgentString, TeamName, TimeStamp) : isCcAgent(true).
+!saveForDeferredSending([diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp)|T], AgentString, TeamName, TimeStamp) : isCcAgent(false)
	<- 	saveMsg(Kind, Status, Values, Flag,AgentString, TName, TimeStamp);
		!saveForDeferredSending(T, AgentString, TeamName, TimeStamp).	

/* stores every diagnose AND mostrecentacks_ in hsql_db */
+!saveForDeferredSending([diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp)|T], AgentString, TeamName, TimeStamp) : isCcAgent(true) 
	<- 	storeDiagnose(Kind,Status,Values,Flag,AgentString,TName,TimeStamp);
		!saveForDeferredSending(T, AgentString, TeamName, TimeStamp).

+!storeMostRecentAckInDb(AgentString, TimeStamp, TeamName) : missionId(MissionId) & isCcAgent(true)
	<- 	storeAck(AgentString, TimeStamp, TeamName, MissionId).	
+!storeMostRecentAckInDb(AgentString, TimeStamp, TeamName).

+radioSilence(true) : isCcAgent(false) 	<- !startDeferredSending.
+radioSilence(true) : isCcAgent(true) 	<- !keepUpdatingGui.

+!keepUpdatingGui : radioSilence(true) <- updateListTimes; .wait(4000); !keepUpdatingGui.
+!keepUpdatingGui : radioSilence(false).

/* Maintenance Task for Deferred Sending (Always in Batches with 10 seconds in between) */
/* mostRecentAck is used for primary pruning, (+possibly save to db), then only flagging most recent results in ccAgent */
+!startDeferredSending : radioSilence(X) & X == (false)
	<- 	.findall(mostRecentAck(AgentString, TimeStamp, TeamName),mostRecentAck(AgentString, TimeStamp, TeamName),L)
		sendingMsg(true); //Emtpy Artifact Buffer (ArrayList)
		.wait(1400);
		!sendingMostRecentAck(L).
+!startDeferredSending : radioSilence(X) & X == (true).

+!startDeferredSendingSingle(AgentString, TimeStamp, TeamName) :  ccAgent(Ag)
	<-	sendingMsg(true); //Emtpy Artifact Buffer (ArrayList)
		.wait(2400);
		.send(Ag,tell,mostRecent(AgentString, TimeStamp, TeamName)).

+!sendingMostRecentAck([]).
+!sendingMostRecentAck([mostRecentAck(AgentString, TimeStamp, TeamName)|T]) : ccAgent(Ag)
	<- 	.wait(1400);
		.send(Ag,tell,mostRecent(AgentString, TimeStamp, TeamName));
		!sendingMostRecentAck(T).

/* get highest Triage Flag from newest diagnose set of agent X */
+!agentFlagHighest([]).
+!agentFlagHighest([diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp)|T]) : 
	 			agentFlag(AgentString,HighestFlag,FlagInteger,TimeStamp) & Flag > FlagInteger
	<- 	-agentFlag(AgentString,_,_,_)[source(_)];	+agentFlag(AgentString,"No_Final_Flag",Flag,TimeStamp);
		!agentFlagHighest(T).
+!agentFlagHighest([diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp)|T]) : 
	 			agentFlag(AgentString,HighestFlag,FlagInteger,TimeStamp) & Flag == FlagInteger
	<- 	-agentFlag(AgentString,_,_,_)[source(_)];	+agentFlag(AgentString,"No_Final_Flag",Flag,TimeStamp);
		!agentFlagHighest(T).
+!agentFlagHighest([diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp)|T]) : 
	 			agentFlag(AgentString,HighestFlag,FlagInteger,TimeStamp) & Flag < FlagInteger
	<- 	-agentFlag(AgentString,_,_,_)[source(_)];	+agentFlag(AgentString,"No_Final_Flag",FlagInteger,TimeStamp);
		!agentFlagHighest(T).
	
/* Set TriageFlag Colour for GUI highlighting */
+!setFlagColour(AgentString) : agentFlag(AgentString,FlagColour,FlagInt,TimeStamp) & FlagInt == (3)
	<-  -agentFlag(AgentString,FlagColour,FlagInt,TimeStamp)[source(_)];
		SetColour = "red";
		+agentFlag(AgentString,SetColour,FlagInt,TimeStamp).
+!setFlagColour(AgentString) : agentFlag(AgentString,FlagColour,FlagInt,TimeStamp) & FlagInt == (2)
	<-  -agentFlag(AgentString,FlagColour,FlagInt,TimeStamp)[source(_)];
		SetColour = "orange";
		+agentFlag(AgentString,SetColour,FlagInt,TimeStamp).
+!setFlagColour(AgentString) : agentFlag(AgentString,FlagColour,FlagInt,TimeStamp) & FlagInt == (1)
	<-  -agentFlag(AgentString,FlagColour,FlagInt,TimeStamp)[source(_)];
		SetColour = "green";
		+agentFlag(AgentString,SetColour,FlagInt,TimeStamp).
+!setFlagColour(AgentString) : agentFlag(AgentString,FlagColour,FlagInt,TimeStamp) & FlagInt == (0)
	<-  -agentFlag(AgentString,FlagColour,FlagInt,TimeStamp)[source(_)];
		SetColour = "nope";
		+agentFlag(AgentString,SetColour,FlagInt,TimeStamp).
		
/* prune old diagnoses of Agent X */
+!pruneDiagnoses([],_).
+!pruneDiagnoses([diagnosed(Kind,Status,Values,Flag,AgentString,TName,MyTimeStamp)|T],TimeStamp) :
		MyTimeStamp >= TimeStamp <- !pruneDiagnoses(T,TimeStamp).
+!pruneDiagnoses([diagnosed(Kind,Status,Values,Flag,AgentString,TName,MyTimeStamp)|T],TimeStamp) :
		MyTimeStamp < TimeStamp 
	<- 	-diagnosed(Kind,Status,Values,Flag,AgentString,TName,MyTimeStamp)[source(_)];
		!pruneDiagnoses(T,TimeStamp).
		