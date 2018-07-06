// Agent costs_sim_init in project military_health
createdAgents(0).

+cmd("costs_sim") : createdAgents(X)
	<- 	.print("creating agent");
		.concat(cost_sim,X,Ag)
		.create_agent(Ag, "cost_sim_agent.asl");
		+myCostSimAgent(Ag);
		-createdAgents(X);
		+createdAgents(X+1);
		!sendHelloWaitForAck(Ag).

+!sendHelloWaitForAck(Ag) : cost_simAg_Ack(AgId) & .concat(AgId,"",AgentName) & Ag == AgentName <- !sendHelloAckReceived(Ag).
+!sendHelloWaitForAck(Ag) <- .send(Ag,tell, received_hello); .wait(1000); !sendHelloWaitForAck(Ag).

@simulation[atomic]
+!sendHelloAckReceived(Ag) : true
	<- .print("ack received");
		.findall(diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),
			diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp),LF);
		!sendAllDiagnoses(LF,Ag);
		.findall(mostRecentSim(AgentString, TimeStamp, TeamName),mostRecentAck(AgentString, TimeStamp, TeamName),LFAck);
		!sendAllAcks(LFAck,Ag).
		
+!sendAllDiagnoses([],Ag).
+!sendAllDiagnoses([diagnosed(Kind,Status,Values,Flag,AgentName,TeamName,TimeStamp)|T],Ag) 
	<- 	.send(Ag,tell, diagnosed(Kind,Status,Values,Flag,AgentName,TeamName,TimeStamp) );
		!sendAllDiagnoses(T,Ag).
		
+!sendAllAcks([],Ag).
+!sendAllAcks([mostRecentSim(AgentString, TimeStamp, TeamName)|T],Ag)
	<- .send(Ag,tell, mostRecentSim(AgentString, TimeStamp, TeamName));
		!sendAllAcks(T,Ag).

