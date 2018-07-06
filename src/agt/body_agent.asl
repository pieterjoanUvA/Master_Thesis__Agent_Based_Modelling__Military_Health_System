// Agent body_agent in project military_health

/* Initial beliefs and rules */
wspName("wsp_").

/* Initial goals */
!start.

/* Plans */
+!startTriageMaintenanceGoal <- .wait(4000); !removeStale; .wait(6000); !!doTriage.

+!doTriage : true
	<- 	!removeStale;
		!computeMeanSensorValues; 
		!symptomDiag; //In Included symptom_diag.asl 
		.wait(6000); 
		!doTriage.
-!doTriage <- .wait(12000); !doTriage.

+!start : wspName(Wname) & teamAgent(TeamAg)
	<- 	.my_name(Me);
		.concat(Wname,Me,WorkspaceName);
		createWorkspace(WorkspaceName);
		joinWorkspace(WorkspaceName,Wsp_Id); 
		?bodyName(BName); ?teamName(TName);
		+myCreatedWorkspace(WorkspaceName,Wsp_Id,BName,TName); .wait(1000);
		-wspName(Wname);
		!convertPersonalisedThresholds; 
		!!createThresholdArtifact;
		.wait(400); 
		/* tell sensor of bodyAgentName and Workspace */
		.broadcast(tell,bodyWorkspaceCreated(WorkspaceName, Me,BName,TName)); 
		.send(TeamAg,tell,teamMember(Me));
		!startTriageMaintenanceGoal.

/*  convert personalised thresholds from .jcm file from string to Double, since negative values not allowed */
+!convertPersonalisedThresholds : .findall(personalised(Pname, Pvalue),personalised(Pname,Pvalue),L) 
		& .length(L,Length) & Length >= 1
		<- !convertPersonalised(L).
 +!convertPersonalisedThresholds.
 
 +!convertPersonalised([]).
 +!convertPersonalised([personalised(Pname,Pvalue)|T]) : true
 		<- 	cartago.invoke_obj("java.lang.Double",parseDouble(Pvalue),Y);
 			-personalised(Pname,Pvalue)[source(_)];
 			+personalised(Pname,Y);
 			!convertPersonalised(T).
		
/*  retreives threshold list, then per found threshold an invocation of foundValid Measurements */
+!removeStale : true 
	<- .findall(s(ST,Th),stale(ST,Th),L); .wait(400);
		!removeStalePerType(L).
		 
+!removeStalePerType([]).
+!removeStalePerType([s(ST,Th)|T])
	<-	.findall(vM(V,SN,MTime,SId,ST),vM(V,SN,MTime,SId,ST),ML); 
		jia.myTime(Time);
		!removeStaleEntry(ML,Th,Time);
		!removeStalePerType(T).
		
+!removeStaleEntry([],Th,Time).
+!removeStaleEntry([vM(V,SN,MTime,SId,ST)|T],Th,Time) :  Time >= Th + MTime
	<- -vM(V,SN,MTime,SId,ST)[source(_)];
		!removeStaleEntry(T,Th,Time).
+!removeStaleEntry([vM(V,SN,MTime,SId,ST)|T],Th,Time) :  Time <= Th + MTime
	<- 	!removeStaleEntry(T,Th,Time).
		
+!computeMeanSensorValues : true
	<-	.findall(ST,stale(ST,Th),L);
		.abolish(avSen(_,_,_)); //remove all previous averageSensor values, no stales allowed at each cycle
		.abolish(avSen(_,_,_,_,_)); //also for SBPold and SBPnew
		!computePerType(L).
+!computePerType([]).

/* Temp Sensor outlier demo plans, with minimum allowed variation of 2 degrees or 1.1*std_dev, 
 * with recovery plan when all values do not comply with set parameters
 */
 /* jia.my_std_eval(SensorValue, MeanValue, Desired_std_dev_Threshold, Calculated_std_dev_Value, min_threshold_Value(absolute value), ReturnString) */
+!removeOutliers([],Avg,StdDev,ST) :true <- !recalculateAverage(Avg,StdDev,ST).
+!removeOutliers([V|T],Avg,StdDev,ST) : jia.my_std_eval(V,Avg,1.1,StdDev,2,"positive") 
	<- 	jia.myTime(TimeStamp); .wait(4);
		+evalPositive(V,ST,TimeStamp);
		!removeOutliers(T,Avg,StdDev,ST).
+!removeOutliers([V|T],Avg,StdDev,ST) : jia.my_std_eval(V,Avg,1.1,StdDev,2,"negative")
	<-  !removeOutliers(T,Avg,StdDev,ST).		
+!recalculateAverage(Avg,StdDev,ST) : .findall((V),evalPositive(V,ST,TimeStamp),ReCalcList) & .length(ReCalcList,X) & X > 1
	<-	jia.my_avg(ReCalcList,AvgNew);
		jia.my_std(ReCalcList,StdDevNew);
		+avSen(ST,AvgNew,StdDevNew).
+!recalculateAverage(Avg,StdDev,ST).

/* not suitable for sbp drop because that drop is an outlier, specific for temp sensor demo profile */
+!computePerType([ST|T]) : .findall((V),vM(V,SN,MTime,SId,ST),ML) & outlierRemove(true) & .length(ML,X) & X > 1 & ST == ("temp")
	<- 	jia.my_avg(ML,Avg);
		jia.my_std(ML,StdDev);
		.abolish(evalPositive(_,ST,_));
		!removeOutliers(ML,Avg,StdDev,ST);
		!computePerType(T).
		
+!computePerType([ST|T]) : .findall((V),vM(V,SN,MTime,SId,ST),ML) & ST == ("sbp") & .length(ML,X) & X > 1
	<- 	.findall(v(MT,Valu),vM(Valu,SNm,MT,SIdm,ST),MinMaxL);
		.min(MinMaxL,v(MT,SBPold));
		.findall(b(MTm,Valum),vM(Valum,Snmm,MTm,SIdmm,ST),MaxL);
		.max(MaxL,b(MTm,SBPnew));
		jia.my_avg(ML,Avg);
		jia.my_std(ML,StdDev);
		+avSen(ST,Avg,StdDev);
		+avSen(ST,Avg,StdDev,SBPold,SBPnew);
		!computePerType(T).
+!computePerType([ST|T]) : .findall((V),vM(V,SN,MTime,SId,ST),ML) & ML \== []
	<-	jia.my_avg(ML,Avg);
		jia.my_std(ML,StdDev);
		+avSen(ST,Avg,StdDev);
		!computePerType(T).
/* Plan for Empty set of Valid Measurements*/
+!computePerType([ST|T])  <- !computePerType(T).
		
+bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)] : bodyName(BName).
+bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)] <- -bodyWorkspaceCreated(Wsp_Name,BodyAgent,BName,TName)[source(_)].
/* ThresholdArtifact Initialisation and Symptom Diagnose Plans and Rules */
{ include("threshold_init.asl") }
{ include("symptom_diag.asl") }
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }


