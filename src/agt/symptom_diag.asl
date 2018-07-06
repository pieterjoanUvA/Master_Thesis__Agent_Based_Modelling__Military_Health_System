// Agent symptom_diag in project military_health

/* atomic function to ensure timely processing and without interruption */
@p1[atomic]
+!symptomDiag : teamAgent(TeamAg) & teamName(TName)
	<-	/* Sensor Symptom Evaluation Plans */
		!checktemp;
		!checkrr;
		!checkhr;
		!checkspo2;
		!checksbp;
		/* Possible Diagnosed Conditions */
		!diagDeath;
		!diagShot;
		!diagShock;
		!diagUndercooled;
		!diagHeat;
		!diagResp;
		!diagMental;
		!diagConsciousness;
		!sendToTeamAgent.

/* Consciousness: unconsciousness */
capabilities_unconsciousness(SV) :- unconsciousness(X) & SV <= X.

+!diagConsciousness : avSen("spo2",SV,SD) & capabilities_unconsciousness(SV)
	<- 	.concat("spo2: ",SV," ",Values);
		+diagnosed("unconsciousness"," -=Yes=- ",Values, 3).
+!diagConsciousness : avSen("spo2",SV,SD)
	<- 	.concat("spo2: ",SV," ",Values);
		+diagnosed("unconsciousness"," -=No=- ",Values, 1).
+!diagConsciousness.
		
/* impaired_mental */
capabilities_impaired_mental(SV) :- impaired_mental(X) & SV <= X.

+!diagMental : avSen("spo2",SV,SD) & capabilities_impaired_mental(SV)
	<- 	.concat("spo2: ",SV," ",Values);
		+diagnosed("impaired_mental"," -=Yes=- ",Values, 3).
+!diagMental : avSen("spo2",SV,SD) 
	<- 	.concat("spo2: ",SV," ",Values);
		+diagnosed("impaired_mental"," -=No=- ",Values, 1). 
+!diagMental.

/* resp:  apnea, bradypnea, tachypnea_hypervent */		
resp_apnea(SV) :- apnea(X) & SV <= X.
resp_bradypnea(SV) :- apnea(X) & bradypnea(Y) & SV > X & SV < Y.
resp_tachypnea_hypervent(SV) :- tachypnea_hypervent(X) & SV >= X.

+!diagResp : avSen("rr",SV,SD) & resp_apnea(SV)
	<- 	.concat("rr: ",SV," ",Values);
		+diagnosed("resp"," apnea ",Values, 3).
+!diagResp : avSen("rr",SV,SD) & resp_bradypnea(SV)
	<- 	.concat("rr: ",SV," ",Values);
		+diagnosed("resp"," bradypnea ",Values, 3).
+!diagResp : avSen("rr",SV,SD) & resp_tachypnea_hypervent(SV)
	<- 	.concat("rr: ",SV," ",Values);
		+diagnosed("resp"," tachypnea_hypervent ",Values, 3).
+!diagResp : avSen("rr",SV,SD) 
	<- 	.concat("rr: ",SV," ",Values);
		+diagnosed("resp"," -=No=- ",Values, 1).
+!diagResp.
		
/* Heat Illnesses, hyperthermia, hyperpyrexia */	
heat_hyperthermia(ST, SV) :- hyperthermia(X) & SV >= X.
heat_hyperpyrexia(ST, SV) :- hyperpyrexia(X) & SV >= X.

+!diagHeat :  avSen("temp",SV,SD) & heat_hyperpyrexia(ST, SV)
		<- 	.concat("temp: ",SV," ",Values);
		+diagnosed("heat"," hyperpyrexia ",Values, 3).
+!diagHeat :  avSen("temp",SV,SD) & heat_hyperthermia(ST, SV)
		<- 	.concat("temp: ",SV," ",Values);
		+diagnosed("heat"," hyperthermia ",Values, 2).
+!diagHeat :  avSen("temp",SV,SD) 
		<- 	.concat("temp: ",SV," ",Values);
		+diagnosed("heat"," -=No=- ",Values, 1).
+!diagHeat.
		
/* Undercooled */
/* hypothermia */
undercooled(ST, SV) :- hypothermia(X) & SV < X.

+!diagUndercooled : avSen("temp",SV,SD) & undercooled("temp", SV)
	<- 	.concat("temp: ",SV," ",Values);
		+diagnosed("undercooled"," hypothermia ",Values, 3).	
+!diagUndercooled : avSen("temp",SV,SD) 
	<- 	.concat("temp: ",SV," ",Values);
		+diagnosed("undercooled"," -=No=- ",Values, 1).
+!diagUndercooled.

/* shockSi */
inShock(HR,SBP) :- shockSi(SiX) &  HR / SBP >= SiX.

//shock validated
+!diagShock : avSen("hr",HR,HRdev) & avSen("sbp",SBP,SBPdev) & inShock(HR,SBP)
	<- 	//HR / SBP = SI;	
		jia.my_divide(HR,SBP,SI);
		.concat(" hr: ",HR," sbp: ",SBP," ShockIndex SI: ", SI," ",Values);
		+diagnosed("shock","in_shock",Values,3).
+!diagShock : avSen("hr",HR,HRdev) & avSen("sbp",SBP,SBPdev) 
	<- 	jia.my_divide(HR,SBP,SI);
		.concat(" hr: ",HR," sbp: ",SBP," ShockIndex SI: ", SI," ",Values);
		+diagnosed("shock"," -=No=- ",Values,1).
+!diagShock.

/* shotSbp, shotSd */
isShot(ST, SBP) :- shotSbp(SX) & SBP < SX & ST == ("sbp"). //or do with 2 measurements with an in between time of 20000ms
isShot(ST, SBPold, SBPnew) :- ( SBPold - SBPnew )  > 0 & ST == ("sbp"). //only in combination with rule below
isShot(ST, SBP, SD, SBPold, SBPnew) :- SD \== 0 & shotSd(SdX) & jia.my_multiply(SD,SdX,Super) & Super <= ( SBPold - SBPnew ) & isShot(ST, SBPold,SBPnew).

+!diagShot : avSen(ST,SV,SD,SBPold,SBPnew) & ST == ("sbp") & isShot(ST, SV, SD, SBPold, SBPnew)
	<-  SBPold - SBPnew = Drop;
		.concat(" sbp: ", SV," drop_sbp(old-new): ",Drop," std_dev:  ",SD," ",Values);
		+diagnosed("shot","likely_shot",Values,3).
+!diagShot : avSen(ST,SV,SD,SBPold,SBPnew) & ST == ("sbp") & isShot(ST, SV)
	<- 	 //multiple values found thus an SBPold/new is calculated, diff with single sensor
		.concat(" sbp: ", SV," std_dev: ",SD," ",Values);
		+diagnosed("shot","likely_shot",Values,3).	 
+!diagShot : avSen(ST,SV,SD) & ST == ("sbp") & isShot(ST, SV)
	<- 	.concat(" only_singlevalue_sbp: ", SV," ",Values); //No StandardDeviation because of only one value
		+diagnosed("shot","likely_shot",Values,3).
+!diagShot : avSen(ST,SV,SD) & ST == ("sbp")
	<- 	.concat(" sbp: ", SV," ",Values); //No StandardDeviation because of only one value
		+diagnosed("shot"," -=No=- ",Values,1).
+!diagShot.		

		/* deathTemp, deathSbp, deathRr, deathAcc, deathHr
		 * /* ProLog Rules for Ailment Reasoning */
likelyDead_sbp(SBP) :- deathSbp(SX) &  SBP <= SX.
likelyDead_temp(Temp) :- deathTemp(TX) & Temp <= TX.
likelyDead(HR, RR) :- deathHr(HX) & HR <= HX & deathRr(RX) & RR <= RX. //because of reliable electro and mems measuring
likelyDead_sr(HR, SBP, RR) :- likelyDead(HR, RR) & deathSbp(SX) & SBP < SX. //No SPO2 because Oxi rests in veins when dead
likelyDead_st(HR, SBP, Temp) :- deathHr(HX) & HR <= HX & likelyDead_sbp(SBP) & likelyDead_temp(Temp).
likelyDead_rt(HR, RR, Temp) :- likelyDead(HR, RR) & likelyDead_temp(Temp).
likelyDead(HR, SBP, RR, Temp) :- likelyDead_sr(HR, SBP, RR) &likelyDead_sbp(SBP)&likelyDead_temp(Temp). //run only without vM of Acc/Gyro
likelyDead(HR, SBP, RR, Temp, Acc) :- likelyDead(HR, SBP, RR, Temp) & deathAcc(AX) & Acc < AX.

somethingTerriblyWrongButNotDead(HR, SBP, RR, Temp, Acc) :- likelyDead(HR, SBP, RR, Temp) & deathAcc(AX) & Acc >= AX.


/* All sensors according to parameters, but still movement perceived, quiteAbnormal readings */
 
+!diagDeath : avSen("hr",HR,HRdev) & avSen("sbp",SBP,SBPdev) & avSen("rr",RR,RRdev) & avSen("temp", Temp,Tdev)
		& avSen("acc",Acc,AccDev) & somethingTerriblyWrongButNotDead(HR, SBP, RR, Temp, Acc) 
	 <- .concat(" hr: ",HR," sbp: ",SBP," rr: ",RR," temp: ",Temp," acc: ",Acc," ",Values);
		+diagnosed("death","Still_Moving/quite_abnormal",Values,2).
		
/* obliged to not believe "Still_Moving/quite_bbNormal" diagnose		 */
//+!diagDeath : avSen("acc",Acc,AccDev) & deathAcc(AX) & Acc >= AX. //exclusively not likelyDead, AbNormal possible, discuss
+!diagDeath : avSen("hr",HR,HRdev) & avSen("sbp",SBP,SBPdev) & avSen("rr",RR,RRdev) & avSen("temp", Temp,Tdev)
		& avSen("acc",Acc,AccDev) & likelyDead(HR, SBP, RR, Temp, Acc) 
	<- 	.concat(" hr: ",HR," sbp: ",SBP," rr: ",RR," temp: ",Temp," acc: ",Acc," ",Values);
		+diagnosed("death","likely_Dead",Values,3).
+!diagDeath : avSen("hr",HR,HRdev) & avSen("sbp",SBP,SBPdev) & avSen("rr",RR,RRdev) & avSen("temp", Temp,Tdev)
		& likelyDead(HR, SBP, RR, Temp) 
	<- 	.concat(" hr: ",HR," sbp: ",SBP," rr: ",RR," temp: ",Temp," ",Values);
		+diagnosed("death","likely_Dead",Values,3).	
+!diagDeath : avSen("hr",HR,HRdev) & avSen("rr",RR,RRdev) & avSen("temp", Temp,Tdev)
		& likelyDead_rt(HR, RR, Temp) 
	<- 	.concat(" hr: ",HR," rr: ", RR," temp: ",Temp," ",Values);
		+diagnosed("death","likely_Dead",Values,3).
+!diagDeath : avSen("hr",HR,HRdev) & avSen("sbp",SBP,SBPdev) & avSen("temp", Temp,Tdev) 
		& likelyDead_st(HR, SBP, Temp)
	<- 	.concat(" hr: ",HR," sbp: ",SBP," temp: ",Temp," ",Values);
		+diagnosed("death","likely_Dead",Values,3).
+!diagDeath : avSen("hr",HR,HRdev) & avSen("sbp",SBP,SBPdev) & avSen("rr",RR,RRdev)
		& likelyDead_sr(HR, SBP, RR) 
	<- 	.concat(" hr: ",HR," sbp: ",SBP," rr: ",RR," ",Values);
		+diagnosed("death","likely_Dead",Values,3).
+!diagDeath : avSen("hr",HR,HRdev) & avSen("rr",RR,RRdev) & likelyDead(HR, RR) 
	<- 	.concat(" hr: ",HR," rr: ",RR," ", Values);
		+diagnosed("death","likely_Dead",Values,3).
+!diagDeath : avSen("hr",HR,HRdev) & avSen("rr",RR,RRdev)
	<- 	.concat(" hr: ",HR," rr: ",RR," ", Values);
		+diagnosed("death"," -=No=- ",Values,1).
+!diagDeath.

		/* temp_lower_th_highrisk, temp_high_th_risk, temp_high_th_highrisk */
+!checktemp : avSen("temp", Value, SD) & temp_lower_th_highrisk(X) & Value < X
	<- +diagnosed("temp", "Low_Temperature_highrisk", Value, 3).
+!checktemp : avSen("temp", Value, SD) & temp_lower_th_highrisk(X) & Value >= X & temp_high_th_risk(Y) & Value < Y
	<- +diagnosed("temp", "deviant_normal_zone", Value, 1).
+!checktemp : avSen("temp", Value, SD) & temp_high_th_risk(X) & Value >= X & temp_high_th_highrisk(Y) & Value < Y
	<- +diagnosed("temp", "High_Temperature_risk", Value, 2).
+!checktemp : avSen("temp", Value, SD) & temp_high_th_highrisk(X) & Value >= X
	<- +diagnosed("temp", "High_Temperature_highrisk", Value, 3).
+!checktemp.
	
		/* rr_lower_th_highrisk, rr_high_th_highrisk */
+!checkrr : avSen("rr", Value, SD) & rr_lower_th_highrisk(X) & Value < X
	<- +diagnosed("rr", "Low_RespiratoryRate_highrisk", Value, 3).
+!checkrr : avSen("rr", Value, SD) & rr_lower_th_highrisk(X) & Value >= X & rr_high_th_highrisk(Y) & Value < Y
	<- +diagnosed("rr", "deviant_normal_zone", Value, 1).
+!checkrr : avSen("rr", Value, SD) & rr_high_th_highrisk(X) & Value > X
	<- +diagnosed("rr", "High_RespiratoryRate_highrisk", Value, 3).
+!checkrr.
		
		/* hr_lower_th_highrisk, hr_lower_th_risk, hr_high_th_risk, hr_high_th_highrisk */
+!checkhr : avSen("hr",Value,SD) & hr_lower_th_highrisk(X) & Value < X
	<- +diagnosed("hr", "Low_HartRate_highrisk", Value, 3).
+!checkhr : avSen("hr",Value,SD) & hr_lower_th_highrisk(X) & Value >= X & hr_lower_th_risk(Y) & Value < Y
	<- +diagnosed("hr", "Low_HartRate_risk", Value, 2).
+!checkhr : avSen("hr",Value,SD) & hr_lower_th_risk(X) & Value >= X & hr_high_th_risk(Y) & Value < Y
	<- +diagnosed("hr", "deviant_normal_zone", Value, 1).
+!checkhr : avSen("hr",Value,SD) &  hr_high_th_risk(X) & Value >= X & hr_high_th_highrisk(Y) & Value < Y
	<- +diagnosed("hr", "High_HartRate_risk", Value, 2).
+!checkhr : avSen("hr",Value,SD) &  hr_high_th_highrisk(X) & Value >= X
	<- +diagnosed("hr", "High_HartRate_highrisk", Value, 3).
+!checkhr.
	
		/* spo2_lower_th_highrisk, spo2_lower_th_risk, */
+!checkspo2 : avSen("spo2",Value,SD) & spo2_lower_th_highrisk(X) & Value < X
	<- +diagnosed("spo2","Low_Oxigination_highrisk", Value, 3).
+!checkspo2 : avSen("spo2",Value,SD) & spo2_lower_th_highrisk(X) & Value >= X & spo2_lower_th_risk(Y) & Value < Y
	<- +diagnosed("spo2","Low_Oxigination_risk", Value, 2).
+!checkspo2 : avSen("spo2",Value,SD) & spo2_lower_th_risk(X) & Value >= X
	<- +diagnosed("spo2","Sufficient_Oxigination_", Value, 1).
+!checkspo2.
		
		/* sbp_lower_th_highrisk, sbp_lower_th_risk, sbp_high_th_risk, sbp_high_th_highrisk, */
		/*Object kind, Object status, Object values, Object flag */
		// 60 80 160 200
+!checksbp : avSen("sbp",SBP,SBPdev) & sbp_lower_th_highrisk(X) & SBP < X
	<- 	+diagnosed("sbp","hypotension_highrisk",SBP,3).
+!checksbp : avSen("sbp",SBP,SBPdev) & sbp_lower_th_highrisk(Y) & SBP >= Y & sbp_lower_th_risk(X) & SBP < X  
	<-	+diagnosed("sbp","hypotension_risk",SBP,2).
+!checksbp : avSen("sbp",SBP,SBPdev) & sbp_lower_th_risk(X) & SBP >= X & sbp_high_th_risk(Y) & SBP < Y 
	<- 	+diagnosed("sbp","deviant_normal_zone",SBP,1).
+!checksbp : avSen("sbp",SBP,SBPdev) & sbp_high_th_risk(X) & SBP >= X & sbp_high_th_highrisk(Y) & SBP < Y
	<- 	+diagnosed("sbp","hypertension_risk",SBP,2).
+!checksbp : avSen("sbp",SBP,SBPdev) & sbp_high_th_highrisk(X) & SBP > X
	<- 	+diagnosed("sbp","hypertension_highrisk",SBP,3).
+!checksbp.
		
		

/* Diagnose Sending Plans */		
+!sendToTeamAgent : teamAgent(TeamAg) & teamName(TName)
	<- 	jia.myTime(CurrTime);
		.my_name(Me); 
		.concat(Me,"",MeString); //because GUIArtifacts strings cannot be converted back to AgentIdentifiers
		/* diagnosed aggregation */
		.findall(diagnosed(Kind,Status,Values,Flag,MeString,TName,CurrTime),diagnosed(Kind,Status,Values,Flag),L);
		!sendDiagnosed(L,TeamAg);
		/* sent mostRecent after ack_sending all diagnoses due to timing problems in team_agent */
		.send(TeamAg,tell,mostRecent(MeString, CurrTime, TName) );
		/* diagnosed cleanup */
		.abolish(diagnosed(_,_,_,_)).
		
+!sendDiagnosed([],_).  
+!sendDiagnosed([diagnosed(Kind,Status,Values,Flag,MeString,TName,CurrTime)|T],TeamAg) 
	<- 	.send(TeamAg,tell,diagnosed(Kind,Status,Values,Flag,MeString,TName,CurrTime) );
		!sendDiagnosed(T,TeamAg).
