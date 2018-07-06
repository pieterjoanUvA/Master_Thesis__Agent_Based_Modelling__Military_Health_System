// Agent symptom_sim_profiles in project military_health

/*  simulation symptomAgentName, profile, stage, team */
/* Initial beliefs and rules */

/* mySensorTypeValues mySTV */
mySTV("hr", 92).
mySTV("rr", 21).
mySTV("spo2", 99).
mySTV("temp", 36.4).
mySTV("sbp", 120).
mySTV("acc", 0.1).
mySTV("gyro", 0.1).
/* .jcm mySim("profile_name") should be configured */

/* Plans */
+setCurrentStage(X) <- -setCurrentStage(X)[source(_)]; -currentStage(_)[source(_)]; +currentStage(X).
+currentStage(0) <- !resetToInitialValues; .drop_intention(keepValuesRefreshed); !!keepValuesRefreshed.
+currentStage(X) <- .drop_intention(keepValuesRefreshed); !!keepValuesRefreshed.

+!keepValuesRefreshed : currentStage(0) <- !setStageValues; .wait(math.random(2000)+12000); !keepValuesRefreshed.
+!keepValuesRefreshed : currentStage(X) & X > 0 & X <= 4 & mySim("outlier_demo") <- !setSimulation(X); .wait(math.random(2000)+8400); !keepValuesRefreshed.
+!keepValuesRefreshed : currentStage(X) & X > 0 & X <= 4 <- !setSimulation(X); .wait(math.random(2000)+10000); !keepValuesRefreshed.

+!setStageValues : true
	<-	.findall(mySTV(SensorType,SensorValue),mySTV(SensorType,SensorValue),L);
		!setMySensorsSimulatedValues(L).

+!resetToInitialValues 
	<- 	-mySTV("hr",_); +mySTV("hr", 92); 
		-mySTV("rr",_); +mySTV("rr", 21); 
		-mySTV("spo2",_); +mySTV("spo2", 99); 
		-mySTV("temp",_); +mySTV("temp", 36.4); 
		-mySTV("sbp",_); +mySTV("sbp", 120);
		-mySTV("acc",_); +mySTV("acc", 0.1); 
		-mySTV("gyro",_); +mySTV("gyr0", 0.1); 
		!toggleMyTempSensorsLinearRegression;
		!setStageValues.
		
+!setSimulation(X) : mySim("none") <- !setStageValues.

+!setSimulation(X) : mySim("outlier_demo") & currentStage(3)
	<-  -mySTV("temp",_); +mySTV("temp", 36.8); !setStageValues; .wait(2400);
		-mySTV("temp",_); +mySTV("temp", 26.8); !setStageValues; .wait(2400);
		-mySTV("temp",_); +mySTV("temp", 36.8); !setStageValues; .wait(2400);
		-mySTV("temp",_); +mySTV("temp", 36.6); !setStageValues; .wait(2400);
		-mySTV("temp",_); +mySTV("temp", 42.6); !setStageValues; .wait(2400);
		-mySTV("temp",_); +mySTV("temp", 36.7); !setStageValues; .wait(2400);
		!setStageValues.
+!setSimulation(X) : mySim("outlier_demo") & currentStage(4)
	<-  !toggleMyTempSensorsLinearRegression;
		-mySTV("temp",_); +mySTV("temp", 36.8); !setStageValues; .wait(800);
		-mySTV("temp",_); +mySTV("temp", 26.4); !setStageValues; .wait(800);
		-mySTV("temp",_); +mySTV("temp", 36.8); !setStageValues; .wait(800);
		-mySTV("temp",_); +mySTV("temp", 36.7); !setStageValues; .wait(800);
		!setStageValues.

+!setSimulation(X) : mySim("hr_exercising") & currentStage(1)
	<-	-mySTV("hr",_); +mySTV("hr", 144); -mySTV("sbp",_); +mySTV("sbp", 110); 
		-mySTV("rr",_); +mySTV("rr", 26);  -mySTV("acc",_); +mySTV("acc", 4.2); !setStageValues. 
+!setSimulation(X) : mySim("hr_over_exercising") & currentStage(1) 
	<-	-mySTV("hr",_); +mySTV("hr", 165); -mySTV("sbp",_); +mySTV("sbp", 120); 
		-mySTV("rr",_); +mySTV("rr", 28); -mySTV("temp",_); +mySTV("temp", 38.8); 
		-mySTV("acc",_); +mySTV("acc", 3.1); !setStageValues.
+!setSimulation(X) : mySim("hr_exploding") & currentStage(1)
	<-	-mySTV("hr",_); +mySTV("hr", 201); -mySTV("sbp",_); +mySTV("sbp", 140); 
		-mySTV("rr",_); +mySTV("rr", 32); -mySTV("temp",_); +mySTV("temp", 39.8); 
		-mySTV("acc",_); +mySTV("acc", 4.3); !setStageValues.
+!setSimulation(X) : mySim("hr_sleeping") & currentStage(1)
	<-	-mySTV("hr",_); +mySTV("hr", 46); -mySTV("sbp",_); +mySTV("sbp", 84); 
		-mySTV("rr",_); +mySTV("rr", 14);  -mySTV("acc",_); +mySTV("acc", 0.2); !setStageValues. 


+!setSimulation(X) : mySim("death_all_sensors") & currentStage(1)
	<-	-mySTV("hr",_); +mySTV("hr", 4); -mySTV("sbp",_); +mySTV("sbp", 40); 
		-mySTV("rr",_); +mySTV("rr", 6); -mySTV("temp",_); +mySTV("temp", 32); 
		-mySTV("acc",_); +mySTV("acc", 0.2); !setStageValues. 
+!setSimulation(X) : mySim("death_all_sensors") & currentStage(2)
	<-	-mySTV("hr",_); +mySTV("hr", 0); -mySTV("sbp",_); +mySTV("sbp", 40); 
		-mySTV("rr",_); +mySTV("rr", 0); -mySTV("temp",_); +mySTV("temp", 26); 
		-mySTV("acc",_); +mySTV("acc", 0.1); !setStageValues.
+!setSimulation(X) : mySim("death_quite_abnormal") & currentStage(1)
	<-	-mySTV("hr",_); +mySTV("hr", 4); -mySTV("sbp",_); +mySTV("sbp", 40); 
		-mySTV("rr",_); +mySTV("rr", 6); -mySTV("temp",_); +mySTV("temp", 32); 
		-mySTV("acc",_); +mySTV("acc", 0.6); !setStageValues. 
+!setSimulation(X) : mySim("death_quite_abnormal") & currentStage(2)
	<-	-mySTV("hr",_); +mySTV("hr", 0); -mySTV("sbp",_); +mySTV("sbp", 40); 
		-mySTV("rr",_); +mySTV("rr", 0); -mySTV("temp",_); +mySTV("temp", 26); 
		-mySTV("acc",_); +mySTV("acc", 0.6); !setStageValues.
	
+!setSimulation(X) : mySim("death_2sensors") & currentStage(1)
	<-	-mySTV("hr",_); +mySTV("hr", 4);  -mySTV("rr",_); +mySTV("rr", 6); !setStageValues; 
		!disableOthersDeathScenario;
		. 
+!setSimulation(X) : mySim("death_2sensors") & currentStage(2)
	<-	-mySTV("hr",_); +mySTV("hr", 0); -mySTV("rr",_); +mySTV("rr", 0);  !setStageValues; 
		!disableOthersDeathScenario;
		.


+!setSimulation(X) : mySim("shot") & currentStage(1)
	<-	-mySTV("sbp",_); +mySTV("sbp", 54); -mySTV("hr",_); +mySTV("hr", 47); !setStageValues. 

+!setSimulation(X) : mySim("shot_shock") & currentStage(1)
	<-	-mySTV("sbp",_); +mySTV("sbp", 86); -mySTV("hr",_); +mySTV("hr", 100); !setStageValues. 
+!setSimulation(X) : mySim("shot_shock") & currentStage(2) 
	<-	-mySTV("sbp",_); +mySTV("sbp", 80); -mySTV("hr",_); +mySTV("hr", 74); !setStageValues.


+!setSimulation(X) : mySim("hyperventilation") & currentStage(1) 
	<- 	-mySTV("rr",_); +mySTV("rr",30); !setStageValues.
+!setSimulation(X) : mySim("bradypnea") & currentStage(1) 
	<- 	-mySTV("rr",_); +mySTV("rr", 9); !setStageValues.
+!setSimulation(X) : mySim("bradypnea") & currentStage(2)
	<-	-mySTV("rr",_); +mySTV("rr", 4); !setStageValues.
+!setSimulation(X) : mySim("bradypnea_apnea") & currentStage(1)
	<-	-mySTV("rr",_); +mySTV("rr", 9); !setStageValues.
+!setSimulation(X) : mySim("bradypnea_apnea") & currentStage(2)
	<-	-mySTV("rr",_); +mySTV("rr", 0); !setStageValues.


+!setSimulation(X) : mySim("hyperthermia") & currentStage(1) 
	<-	-mySTV("temp",_); +mySTV("temp", 34); !setStageValues.
+!setSimulation(X) : mySim("hyperthermia_hyperpyrexia") & currentStage(1)
	<-	-mySTV("temp",_); +mySTV("temp", 39.2); !setStageValues.
+!setSimulation(X) : mySim("hyperthermia_hyperpyrexia") & currentStage(2)
	<-	-mySTV("temp",_); +mySTV("temp", 41.7); !setStageValues.

+!setSimulation(X) : mySim("impaired_mental_unconsciousness") & currentStage(1)
	<-	-mySTV("spo2",_); +mySTV("spo2", 64); !setStageValues.
+!setSimulation(X) : mySim("impaired_mental_unconsciousness") & currentStage(2)
	<-	-mySTV("spo2",_); +mySTV("spo2", 50); !setStageValues.
+!setSimulation(X) : mySim("impaired_mental_unconsciousness") & currentStage(3)
	<-	-mySTV("spo2",_); +mySTV("spo2", 77); !setStageValues.
+!setSimulation(X) : mySim("impaired_mental_unconsciousness") & currentStage(4)
	<-	-mySTV("spo2",_); +mySTV("spo2", 91); !setStageValues.

/* catch all when no profile + stage is Unified */
+!setSimulation(X) <- !setStageValues.
 
/* mySensorTypeValues BeliefBase General Set plan, that calls !setValue() in main .asl, that is also for GUI used */
+!setMySensorsSimulatedValues([]).
+!setMySensorsSimulatedValues([mySTV(SensorType,SensorValue)|T]) 
	<-	!setValue(SensorType,SensorValue); 
		!setMySensorsSimulatedValues(T).


+activateSimulation(SimulationType,Stage) 
	<- 	-activateSimulation(SimulationType,Stage)[source(_)];
		-currentStage(_)[source(_)];
		+currentStage(Stage);
		!setSimulation(SimulationType,Stage).

+!setSimulation(SimulationType,Stage).

+!toggleMyTempSensorsLinearRegression : currentStage(0) & .findall(sensorName(Name,SensorType),sensorName(Name,SensorType)[source(Ag)],L) 
	<- 	-toggledOnce(true); 
		+toggledOnce(false);
		!toggleIndividualSensors(L).
+!toggleMyTempSensorsLinearRegression : currentStage(4) & toggledOnce(true).
+!toggleMyTempSensorsLinearRegression : currentStage(4) & .findall(sensorName(Name,SensorType),sensorName(Name,SensorType)[source(Ag)],L) 
	<- 	+toggledOnce(true);
		!toggleIndividualSensors(L).
+!toggleIndividualSensors([]).
+!toggleIndividualSensors([sensorName(Name,Type)|T]) : Type == ("temp")
	<-	lookupArtifact(Name,Art_Id);
		toggleRegression[artifact_id(Art_Id)];
		.wait(math.random(400));
		!toggleIndividualSensors(T).
+!toggleIndividualSensors([sensorName(Name,Type)|T]) <- !toggleIndividualSensors(T). 
			
/* Scenario Specific SensorDisable plans, with separate lists, because lists are consumed at un_stacking */
+!disableOthersDeathScenario : 	.findall(sensorName(Name,SensorType),sensorName(Name,SensorType)[source(Ag)],L) &
								.findall(sensorName(Name,SensorType),sensorName(Name,SensorType)[source(Ag)],LF) &
								.findall(sensorName(Name,SensorType),sensorName(Name,SensorType)[source(Ag)],LFA) &
								.findall(sensorName(Name,SensorType),sensorName(Name,SensorType)[source(Ag)],LFB)
	<-  !!setSensorOffline(L, "spo2");
		!!setSensorOffline(LF, "temp");
		!!setSensorOffline(LFA, "sbp");
		!!setSensorOffline(LFB, "acc").
		
+!setSensorOffline([],_). 
+!setSensorOffline([sensorName(Name,Type)|T],ST) : Type == ST
	<- 	lookupArtifact(Name,Art_Id); 
		setSensorStatus("Offline")[artifact_id(Art_Id)]; 
		!setSensorOffline(T,ST).
+!setSensorOffline([sensorName(Name,Type)|T],SensorType) 
	<- 	!setSensorOffline(T,SensorType).
-!setSensorOffline([sensorName(Name,Type)|T],SensorType).

