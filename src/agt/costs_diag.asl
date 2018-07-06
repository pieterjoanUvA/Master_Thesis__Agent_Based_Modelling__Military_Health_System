// Agent costs_diag in project military_health

+cmd("doUpdateTeamReport")<- !getUpdatedTeamReport.

/* GetUpdatedTeamReport Plan (Gui Button invoked plan) */
@planTeamReport[atomic]
+!getUpdatedTeamReport : totalRescueCosts(TotalRescueCostAmount)& teamMemberHumanLifeValue(TeamMemberValue) & doCountLikelyDeathStatus(DoCountDeath)
	<-	.findall(mostRecentAck(AgentString, TimeStamp, TeamName),mostRecentAck(AgentString, TimeStamp, TeamName),MRL);
		/* TeamReport counters remove and reset */
		.abolish(viableRescueCount(_));
		.abolish(deathCounted(_));
		.abolish(deathCountedThresholdsNotMet(_));
		+viableRescueCount(0);
		+deathCounted(0);
		+deathCountedThresholdsNotMet(0);
		!processDiagnosedPerAgent(MRL);
		?deathCounted(DeathCount);
		?viableRescueCount(RescueCount);
		!calculateRescueCounsel(TotalRescueCostAmount,TeamMemberValue,DoCountDeath,DeathCount,RescueCount).
+!processDiagnosedPerAgent([]).
+!processDiagnosedPerAgent([mostRecentAck(AgentString, TimeStamp, TeamName)|T]) : catSensor(SensorTh) & catDiagnosed(DiagTh) &  catCumulative(CumulativeTh)
	<-	.findall(diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),
			diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),LF);
		/* per Agent ThresholdCounters, abolish and reset */
		.abolish(sensorThScore(_)); 
		.abolish(diagnoseThScore(_));
		.abolish(thReached(_,_));
		-deathFound(_);
		+deathFound(false);
		+sensorThScore(0);
		+diagnoseThScore(0);
		+thReached(0,0);
		!processEachDiagnose(LF);
		?sensorThScore(SensorScore);
		?diagnoseThScore(DiagScore);
		?deathCounted(DeathScore);	
		!checkThresholdsReached(SensorTh,DiagTh,CumulativeTh,SensorScore,DiagScore,DeathScore);
		!processDiagnosedPerAgent(T).
		
+!processEachDiagnose([]).
+!processEachDiagnose([diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp)|T]) : Flag == 3
	<-	!multiplyByWeight(Kind);
		!processEachDiagnose(T).

+!processEachDiagnose([diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp)|T]) 
	<-!processEachDiagnose(T).
/* Sensor Threshold Count Plans */
+!multiplyByWeight(Kind) : Kind == "hr" & hr(X) &sensorThScore(Y) <- Score = 1 * X; -sensorThScore(Y); +sensorThScore(Score+Y).
+!multiplyByWeight(Kind) : Kind == "rr" & rr(X) &sensorThScore(Y) <- Score = 1 * X; -sensorThScore(Y); +sensorThScore(Score+Y).
+!multiplyByWeight(Kind) : Kind == "sbp" & sbp(X) &sensorThScore(Y) <- Score = 1 * X; -sensorThScore(Y); +sensorThScore(Score+Y).
+!multiplyByWeight(Kind) : Kind == "spo2" & spo2(X) &sensorThScore(Y) <- Score = 1 * X; -sensorThScore(Y); +sensorThScore(Score+Y).
+!multiplyByWeight(Kind) : Kind == "temp" & temp(X) &sensorThScore(Y) <- Score = 1 * X; -sensorThScore(Y); +sensorThScore(Score+Y).
/* Diagnose Threshold Count Plans */
+!multiplyByWeight(Kind) : Kind == "death" & death(X) &diagnoseThScore(Y) & deathCounted(Old)
	<- Score = 1 * X; 
		-diagnoseThScore(Y); +diagnoseThScore(Score+Y); 
		-deathCounted(Old); +deathCounted(Old+1); 
		-deathFound(_); +deathFound(true).
+!multiplyByWeight(Kind) : Kind == "shot" & shot(X) &diagnoseThScore(Y) <- Score = 1 * X; -diagnoseThScore(Y); +diagnoseThScore(Score+Y).
+!multiplyByWeight(Kind) : Kind == "shock" & shock(X) &diagnoseThScore(Y) <- Score = 1 * X; -diagnoseThScore(Y); +diagnoseThScore(Score+Y).
+!multiplyByWeight(Kind) : Kind == "undercooled" & undercooled(X) &diagnoseThScore(Y) <- Score = 1 * X; -diagnoseThScore(Y); +diagnoseThScore(Score+Y).
+!multiplyByWeight(Kind) : Kind == "heat" & heat(X) &diagnoseThScore(Y) <- Score = 1 * X; -diagnoseThScore(Y); +diagnoseThScore(Score+Y).
+!multiplyByWeight(Kind) : Kind == "resp" & resp(X) &diagnoseThScore(Y) <- Score = 1 * X; -diagnoseThScore(Y); +diagnoseThScore(Score+Y).
+!multiplyByWeight(Kind) : Kind == "impaired_mental" & impaired_mental_costs(X) &diagnoseThScore(Y) <- Score = 1 * X; -diagnoseThScore(Y); +diagnoseThScore(Score+Y).
+!multiplyByWeight(Kind) : Kind == "unconsciousness" & unconsciousness_costs(X) &diagnoseThScore(Y) <- Score = 1 * X; -diagnoseThScore(Y); +diagnoseThScore(Score+Y).

/* Threshold Validation Plans according to Costs_Thresholds Artifact */
+!checkThresholdsReached(SensorTh,DiagTh,CumulativeTh,SensorScore,DiagScore,DeathScore)
		<- 	!checkSensorThReached(SensorTh,SensorScore);
			!checkDiagnoseThReached(DiagTh,DiagScore);
			?thReached(SensorReached,DiagnoseReached);
			SensorReached + DiagnoseReached = CumulativeScore;
			!checkCumulativeThReached(CumulativeTh,CumulativeScore).
+!checkSensorThReached(SensorTh,SensorScore) : SensorScore >= SensorTh & thReached(Old,X) 
		<- -thReached(Old,X); +thReached(1,X).
+!checkSensorThReached(SensorTh,SensorScore).

+!checkDiagnoseThReached(DiagTh,DiagScore) : DiagScore >= DiagTh & thReached(X,Old) 
		<- -thReached(X,Old); + thReached(X,1).
+!checkDiagnoseThReached(DiagTh,DiagScore).

+!checkCumulativeThReached(CumulativeTh,CumulativeScore) : CumulativeScore >= CumulativeTh & viableRescueCount(Old)
	<- -viableRescueCount(Old); +viableRescueCount(Old+1).
+!checkCumulativeThReached(CumulativeTh,CumulativeScore) : CumulativeScore < CumulativeTh & deathCounted(Old) & deathFound(true) & deathCountedThresholdsNotMet(OldX)
	<- 	-deathCounted(Old); +deathCounted(Old - 1); 
		-deathCountedThresholdsNotMet(OldX); +deathCountedThresholdsNotMet(OldX + 1).
+!checkCumulativeThReached(CumulativeTh,CumulativeScore).

/* Final rescue counsel processing */
rescueThresholdReached(TotalRescueCostAmount,TeamMemberValue,FinalCount) :-  totalRescueCosts(Amount) &Amount > TeamMemberValue.

+!calculateRescueCounsel(TotalRescueCostAmount,TeamMemberValue,DoCountDeath,DeathCount,RescueCount) : DoCountDeath == false  
		<-	TeamMemberValue * RescueCount  = TeamMemberRescueCosts;
			!rescueCostsEvaluation(TeamMemberRescueCosts,TotalRescueCostAmount).
+!calculateRescueCounsel(TotalRescueCostAmount,TeamMemberValue,DoCountDeath,DeathCount,RescueCount) : DoCountDeath == true  	 
		<-	RescueCount - DeathCount = FinalCount ;
			TeamMemberValue * FinalCount  = TeamMemberRescueCosts;
			!rescueCostsEvaluation(TeamMemberRescueCosts,TotalRescueCostAmount).

+!rescueCostsEvaluation(TeamMemberRescueCosts,TotalRescueCostAmount) : TeamMemberRescueCosts >= TotalRescueCostAmount 
				& doCountLikelyDeathStatus(DoCountDeath) & deathCounted(X) & viableRescueCount(Y) & deathCountedThresholdsNotMet(Z)
		<- 	printCounsel(true,DoCountDeath,X,Y,Z).
+!rescueCostsEvaluation(TeamMemberRescueCosts,TotalRescueCostAmount) : TeamMemberRescueCosts < TotalRescueCostAmount 
				& doCountLikelyDeathStatus(DoCountDeath) & deathCounted(X) & viableRescueCount(Y) & deathCountedThresholdsNotMet(Z)
		<- 	printCounsel(false,DoCountDeath,X,Y,Z).