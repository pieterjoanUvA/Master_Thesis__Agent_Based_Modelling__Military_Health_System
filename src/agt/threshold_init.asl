// Agent threshold_init <capability> in project military_health

/* ThresholdArtifact Initialisation parameter -- full specification */

+!createThresholdArtifact : .my_name(Me)
	<- Sbp_lower_th_highrisk = 60.0;
		Sbp_lower_th_risk = 80.0;
		Sbp_high_th_risk = 160.0;
		Sbp_high_th_highrisk = 200.0;
		Spo2_lower_th_highrisk = 80.0 ;
		Spo2_lower_th_risk = 92.0  ;
		Hr_lower_th_highrisk = 45.0 ; 
		Hr_lower_th_risk = 50.0 ; 
		Hr_high_th_risk = 120.0 ; 
		Hr_high_th_highrisk = 180.0 ; 
		Rr_lower_th_highrisk = 10.0 ; 
		Rr_high_th_highrisk = 29.0 ; 
		 Temp_lower_th_highrisk = 35.0  ; 
		 Temp_high_th_risk = 38.3 ; 
		 Temp_high_th_highrisk = 40.0 ; 
		 DeathTemp = 28.0 ; 
		 DeathSbp = 50.0 ; 
		 DeathRr = 1.0 ; 
		 DeathAcc = 0.4 ; 
		 DeathHr = 0.0 ; 
		 ShotSbp = 85.0 ; 
		 ShotSd = 2.0 ; 
		 SockSi = 0.9 ; 
		 Hypothermia = 35.0 ; 
		 Hyperthermia = 38.3 ; 
		 Hyperpyrexia = 40.0 ; 
		 Apnea =  0.0 ; 
		 Bradypnea =  12.0 ; 
		 Tachypnea_hypervent = 29.0 ; 
		Impaired_mental = 65.0 ; 
		Unconsciousness = 55.0 ;
	.concat(Me,"",Title);
	!getThresholdVisibilityBelief;.wait(400);
	?thresholdVisible(ThresholdVisibility); .wait(20);
	!getAllowedPropagate;
	?allowedThPropagate(PropagateVisibility);
	makeArtifact(Title,"tools.ThreshConsole",[Title,
		Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
		Spo2_lower_th_highrisk,	Spo2_lower_th_risk,
		Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
		Rr_lower_th_highrisk, Rr_high_th_highrisk, 
		Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr,
		ShotSbp,  ShotSd, 
		SockSi, 
		Hypothermia,
		Hyperthermia, Hyperpyrexia,
		Apnea, Bradypnea, Tachypnea_hypervent,
		Impaired_mental, Unconsciousness,
		ThresholdVisibility, PropagateVisibility
	],MyThresholdId);
	focus(MyThresholdId);
	!setPersonalisedThresholds.

+!getThresholdVisibilityBelief : thresholdVisible(true).
+!getThresholdVisibilityBelief : thresholdVisible(false).
+!getThresholdVisibilityBelief <- +thresholdVisible(false).

+!getAllowedPropagate : role(X) & X == ("teamAgent")
		<- +allowedThPropagate(true).
+!getAllowedPropagate : role(X) & X == ("ccAgent")
		<- +allowedThPropagate(true).
+!getAllowedPropagate <- +allowedThPropagate(false).
 
/* ccAgent received event for propagate threshold values */  
+cmd("propagateThresholdValues",	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk,  Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd, SockSi, Hypothermia,Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness ) 
	: role(X) & X == ("ccAgent") & teamAgent(TeamAg)
	<- 	.send(TeamAg,tell,setThValues(	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk,  Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd, SockSi, Hypothermia, Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness)  );. // propagate to team agent
/* Allowed Team Agents update propagation */
+cmd("propagateThresholdValues",	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk,  Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd, SockSi, Hypothermia,Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness ) 
	: role("teamAgent") &  ccAgent(CcAg)
	<- .findall((Ag),teamMember(Ag),L); 
		!sendUpdated(L,Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk, 	 Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd,  SockSi,  Hypothermia, Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness);
		.send(CcAg,tell,setThValues(	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk,  Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd,  SockSi, Hypothermia,
		 	Hyperthermia, Hyperpyrexia, Apnea, Bradypnea, Tachypnea_hypervent,Impaired_mental, Unconsciousness ) ).

/* Team Agent  Propagate 'new received' threshold values */		 
+setThValues(	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk, 	 Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
			DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd, SockSi, Hypothermia, Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness ) 
	: role(X) & X == ("teamAgent")
	<-	.findall((Ag),teamMember(Ag),L);
		setValues(	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk,  Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd,  SockSi, Hypothermia,
		 	Hyperthermia, Hyperpyrexia, Apnea, Bradypnea, Tachypnea_hypervent,Impaired_mental, Unconsciousness)   
		!sendUpdated(L,Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk, 	 Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd,  SockSi,  Hypothermia, Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness);
		-setThValues(	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk, 	 Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd, SockSi, Hypothermia, Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness )[source(_)];
		. // propagate to team members 
/* Body Agent Apply new received threshold values */ 
+setThValues(	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk, 	 Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd, SockSi, Hypothermia, Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness )
	: true //thus the agent is a body agent
	<- 	setValues(	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk,  Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd,  SockSi, Hypothermia,
		 	Hyperthermia, Hyperpyrexia, Apnea, Bradypnea, Tachypnea_hypervent,Impaired_mental, Unconsciousness);
		-setThValues(	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk, 	 Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd, SockSi, Hypothermia, Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness )[source(_)];
	/* implement .findall(personlisedValue(Sensor,Threshold,Adjustment, L) to adust according to personalised values */
	/* and a plan to walk through this list, if any.... */
			!setPersonalisedThresholds;
		 	.	
		
+!sendUpdated([],Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk, 	 Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd,  SockSi,  Hypothermia, Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness). 
+!sendUpdated([MemberAgent|T],Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk, 	 Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd,  SockSi,  Hypothermia, Hyperthermia, Hyperpyrexia,
		 	Apnea, Bradypnea, Tachypnea_hypervent,	Impaired_mental, Unconsciousness)
	<- .send(MemberAgent,tell,setThValues(	Sbp_lower_th_highrisk,	Sbp_lower_th_risk,	Sbp_high_th_risk,	Sbp_high_th_highrisk,
			Spo2_lower_th_highrisk,	Spo2_lower_th_risk,	Hr_lower_th_highrisk,	Hr_lower_th_risk, Hr_high_th_risk, Hr_high_th_highrisk,
			Rr_lower_th_highrisk, Rr_high_th_highrisk,  Temp_lower_th_highrisk, Temp_high_th_risk, Temp_high_th_highrisk, 
		 	DeathTemp,	 DeathSbp,  DeathRr,  DeathAcc,  DeathHr, ShotSbp,  ShotSd,  SockSi, Hypothermia,
		 	Hyperthermia, Hyperpyrexia, Apnea, Bradypnea, Tachypnea_hypervent,Impaired_mental, Unconsciousness ) ).
		 
+!setPersonalisedThresholds : .findall(personalised(Pname, Pvalue),personalised(Pname,Pvalue),L) 
								& .length(L,Length) & Length >= 1
		<- !setIndividualPersonalisedThresholds(L).
+!setPersonalisedThresholds.

+!setIndividualPersonalisedThresholds([]).
+!setIndividualPersonalisedThresholds([personalised(Pname,Pvalue)|T]) : true
 		<- 	personaliseThreshold(Pname,Pvalue);
 			!setIndividualPersonalisedThresholds(T).
 			
/*  for debugging threshold changes in Gui and ObsProperty's 
	!!testChange; */
/* not needed, maybe for sim purposes */
/* 
+!testChange <- setValues(2.44,3.0,4.0,5.0,
	6.0,7.0,
	8.0,9.0,0.0,1.0,
	2.0,3.0,
	4.0,5.0,6.0,
	7.0,8.0,9.0,0.0,1.0,
	2.0,3.0,
	4.0,
	5.0,
	6.0,7.0,
	8.0,9.0,0.0,
	1.0, 4.4
);.wait(2000);
//getValue(X); .print("getValue returns: ", X);
//getValue2(Y); .print("getValue2 returns: ", Y); //.wait(2000);
.
 
*/

		