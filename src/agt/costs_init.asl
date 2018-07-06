// Agent costs_init in project military_health

+!createCostArtifact : .my_name(Me) & teamName(TName)
	<- 	Sbp = 1.0;  
		Spo2 = 1.0;  
		Hr = 1.0;  
		Rr = 1.0;  
		Temp = 1.0; 
		
		 Death = 1.0;  
		 Shot = 1.0;  
		 Shock = 1.0;  
		 Heat = 1.0;  
		 Undercooled = 1.0;  
		 Resp = 1.0;  
		 Impaired_mental = 1.0;  
		 Unconsciousness = 1.0; 
		 
		 CatSensor = 1.0;  
		 CatDiagnosed = 1.0;  
		 CatCumulative = 2.0;  
		 RescueEquipmentCost = 80000.0;  
		 RescuePersonnelCount = 10.0;  
		 RescuePersonnelCost = 1400.0; 
		 TeamMemberHumanLifeValue = 100000.0;  
		 DoCountLikelyDeathStatus = true;  

	.concat(Me,"_",TName,"_CostsConsole",Title);
	!getCostsVisibilityBelief;.wait(400);
	?costsVisible(CostsVisibility); .wait(20);

	makeArtifact(Title,"tools.CostsConsole",[Title, Sbp,Spo2,Hr,Rr,Temp,Death,Shot,  
		 Shock,Heat,Undercooled,Resp,Impaired_mental,Unconsciousness,CatSensor,CatDiagnosed, 
		 CatCumulative,RescueEquipmentCost,RescuePersonnelCount,RescuePersonnelCost,
		 TeamMemberHumanLifeValue,DoCountLikelyDeathStatus,CostsVisibility], MyCostId);
	focus(MyCostId).
	
+!getCostsVisibilityBelief : costsVisible(true).
+!getCostsVisibilityBelief : costsVisible(false).
+!getCostsVisibilityBelief <- +costsVisible(true).