// CArtAgO artifact code for project military_health

package tools;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import org.hsqldb.server.Server;
import java.sql.Statement;
import java.sql.ResultSet;
import org.hsqldb.persist.HsqlProperties;

import cartago.*;
import cartago.tools.*;

public class MyDatabaseConsole extends GUIArtifact {
	private MyDatabaseDisplay display ;
	Server myDbServer;
	Connection dbConn = null;
	final String user_home = System.getProperty("user.home") ;
	final String dbLocationPrefix = user_home+ "/eclipse-workspace/military_health/";
	final String dbLocation = dbLocationPrefix + "hsqldb/";
	boolean dbRunning = false;
	boolean dbAgentInitiated = false;
	//gui_database_pass_on_variables
	String agent;
	String team = "team1";
	Integer mission;
	long timestamp;

	void init(String title) {
		display = new MyDatabaseDisplay(title);
		
		defineObsProperty("dbRunning", dbRunning);
		defineObsProperty("dbConnection", "");
		
		linkActionEventToOp(display.startDb, "startDbButton");
		linkActionEventToOp(display.stopDb, "stopDbButton");
		linkActionEventToOp(display.startDiagnoseGui, "startDiagnoseGuiButton");
		linkActionEventToOp(display.flushDb, "flushDbButton");
		
		linkActionEventToOp(display.getTeams, "myTeamBox");
		linkActionEventToOp(display.getMissions, "myMissionBox");
		linkActionEventToOp(display.getAgents, "myAgentBox");
		linkActionEventToOp(display.getTimeStamps, "myTimeStampBox");
		linkActionEventToOp(display.getResults, "myGetResults");
		
		display.setLocation(40, 40);
		display.setVisible(true);
		this.init();
	}
	@INTERNAL_OPERATION void myTeamBox(ActionEvent ev) {
		display.myTeamBox.removeAllItems();
		display.myMissionBox.removeAllItems();
		display.myAgentBox.removeAllItems();
		display.myTimeStampBox.removeAllItems();
		getTeams();	
	}
	@INTERNAL_OPERATION void myMissionBox(ActionEvent ev) {
		display.myMissionBox.removeAllItems();
		display.myAgentBox.removeAllItems();
		display.myTimeStampBox.removeAllItems();
		team = display.myTeamBox.getSelectedItem().toString();
		getMissions(team);
	}
	@INTERNAL_OPERATION void myAgentBox(ActionEvent ev) {
		display.myAgentBox.removeAllItems();
		display.myTimeStampBox.removeAllItems();
		mission = Integer.parseInt(display.myMissionBox.getSelectedItem().toString());
		getAgentsInMission(team,mission);	
	}
	@INTERNAL_OPERATION void myTimeStampBox(ActionEvent ev) {
		display.myTimeStampBox.removeAllItems();
		agent = display.myAgentBox.getSelectedItem().toString();
		getAck(team, mission, agent);
	}
	@INTERNAL_OPERATION void myGetResults(ActionEvent ev) {
		timestamp = Long.parseLong(display.myTimeStampBox.getSelectedItem().toString());
		signal("abolish_diagnoses");
		getDiagnoses(team, mission, agent, timestamp);
		signal("cmd", "putDiags", agent, timestamp, team, mission);
	}
	@INTERNAL_OPERATION void startDbButton(ActionEvent ev) {
		startDb();
	}
	@INTERNAL_OPERATION void stopDbButton(ActionEvent ev) {
		stopDbServer();
	}
	@INTERNAL_OPERATION void startDiagnoseGuiButton(ActionEvent ev) {
		// to be implemented
		if ( dbAgentInitiated == false) {
			signal("cmd","diag_eval_gui");
			dbAgentInitiated = true;
			display.startDiagnoseGui.setText("Show Gui");
		} else if (dbAgentInitiated == true) {
			signal("cmd","diag_eval_gui_visible");
		}
		getTeams();
	}
	@INTERNAL_OPERATION void flushDbButton(ActionEvent ev) {
		flushDb();
	}
	@OPERATION void startDb() {
		HsqlProperties props = new HsqlProperties();
		props.setProperty("server.database.0", "file:" + dbLocation + "militarydb;");
		props.setProperty("server.dbname.0", "militarydb");
		myDbServer = new org.hsqldb.Server();
	    try {
	    	myDbServer.setProperties(props);
	    } catch (Exception e) {
	        return;
	    }
	    myDbServer.start();	
	    Connection dbConn = getDBConn();
	    
	    if (myDbServer.isNotRunning() == false) {
	    	dbRunning = true;
	    }
	    display.dbStatusLabel.setText("dbRunning: true ");
	    display.dbConnLabel.setText("Connection: "+dbConn.toString());
	    getObsProperty("dbRunning").updateValue(dbRunning);
	    getObsProperty("dbConnection").updateValue(dbConn.toString());
	    try { 
	    	dbConn.setAutoCommit(true); //should be default for new connections
	    	Statement stmt = dbConn.createStatement();
	    	stmt.executeQuery("CREATE TABLE IF NOT EXISTS diagnoses "
	    			+ "( kind VARCHAR(40), status VARCHAR(40), dvalues VARCHAR(80), "
	    			+ "flag INT, agent VARCHAR(40), team VARCHAR(20), timestamp BIGINT)");
	    	stmt.executeQuery("CREATE TABLE IF NOT EXISTS mostrecentacks "
	    			+ "( agent VARCHAR(40), timestamp BIGINT, team VARCHAR(20), mission INT)");
	    } 
	    catch (Exception e) 
	    {
	         e.printStackTrace(System.out);
	    }
	    
	}
	@OPERATION void stopDbServer() {
		try {
			Statement stmt = dbConn.createStatement();
			stmt.executeQuery("SHUTDOWN");
		} catch (Exception e) {
			e.printStackTrace();
		}
		myDbServer.shutdown();
	    display.dbStatusLabel.setText("dbRunning: false ");
	    display.dbConnLabel.setText("Connection: "+"No Connection");
	    dbRunning = false;
	    getObsProperty("dbRunning").updateValue(dbRunning);
	    getObsProperty("dbConnection").updateValue(dbConn.toString());
	}

	@OPERATION void storeDiagnose(String kind, String status, Object values_i, Object flag_i, String agent, String team, long timestamp) {
		String values = values_i.toString();
		Integer flag = Integer.parseInt(flag_i.toString());
	    try {
	        Statement stmt = dbConn.createStatement();
	        stmt.executeQuery("INSERT INTO diagnoses VALUES ('"+kind+"','"+status+"','"+values+"',"+flag+",'"+agent+"','"+team+"',"+timestamp+")");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	@OPERATION void storeAck(String agent, long timestamp, String team, Object mission_i) {
		Integer mission = Integer.parseInt(mission_i.toString());
		try {
			Statement stmt = dbConn.createStatement();
			stmt.executeQuery( "INSERT INTO mostrecentacks VALUES('"+agent+"',"+timestamp+",'"+team+"',"+mission+")" );
		} catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@OPERATION void getTeams() {
		if(display.myTeamBox.getItemCount() > 0) {
			display.myTeamBox.removeAllItems();
		}
	    try {
	        Statement stmt = dbConn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT DISTINCT(team) FROM mostrecentacks ");
	        while (rs.next()) {
	        	display.myTeamBox.addItem(rs.getString("team"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	@OPERATION void getMissions(String team) {
		display.myMissionBox.removeAllItems();
	    try {
	        Statement stmt = dbConn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT DISTINCT mission FROM mostrecentacks WHERE team = '"+team+"'");
	        while (rs.next()) {
	            display.myMissionBox.addItem(rs.getInt("mission"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	@OPERATION void getAgentsInMission(String team, Object mission_i) {
		display.myAgentBox.removeAllItems();
		Integer mission = Integer.parseInt(mission_i.toString());
	    try {
	        Statement stmt = dbConn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT DISTINCT agent FROM mostrecentacks WHERE team = '"+team+"' AND mission = "+mission+ " ");
	        while (rs.next()) {
	        	display.myAgentBox.addItem(rs.getString("agent"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	@OPERATION void getAck(String team, Object mission_i, String agent) {
		Integer mission = Integer.parseInt(mission_i.toString());
	    try {
	        Statement stmt = dbConn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT DISTINCT timestamp FROM mostrecentacks WHERE team = '"+team+"' AND mission = "+mission+ " "
	        		+ "AND agent = '"+agent+"'" );
	        while (rs.next()) {
	        	display.myTimeStampBox.addItem(rs.getLong("timestamp"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@OPERATION void getDiagnoses(String team, Object mission_i, String agent, long timestamp) {
	    try {
	        Statement stmt = dbConn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT kind, status, dvalues, flag, agent, team, timestamp  FROM diagnoses WHERE team = '"+team+"' "
	        		+ "AND timestamp = "+timestamp+"AND agent = '"+agent+"'" );
	        while (rs.next()) {
	        	String kind = rs.getString("kind");
	        	String status = rs.getString("status");
	        	String values = rs.getString("dvalues");
	        	String agentname = rs.getString("agent");
	        	Integer flag = rs.getInt("flag");
	        	String teamname = rs.getString("team");
	        	long timestampresult = rs.getLong("timestamp");
	        	signal("cmd", "diagnoses", kind, status, values, flag, agentname, teamname, timestampresult );
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}	
	@OPERATION void getMaxMissionId(String team) {
		Integer maxMission = 0;
		try {
	        Statement stmt = dbConn.createStatement(); //SELECT MAX(mission) FROM "PUBLIC"."MOSTRECENTACKS" WHERE "TEAM" = 'team1' 
	        ResultSet rs = stmt.executeQuery("SELECT MAX(mission) mission FROM mostrecentacks WHERE team = '"+team+"'" );
	        while (rs.next()) {
	        	maxMission = rs.getInt("mission");
	        	maxMission++;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    if(maxMission == 0 | maxMission.equals(null)) {
	    	maxMission = 0;
	    }
    	signal("cmd","missionId",maxMission);
	}
	@OPERATION void flushDb() {
		 try {
	         Statement stmt = dbConn.createStatement();
	         stmt.executeQuery("DROP TABLE diagnoses");
	         stmt.executeQuery("DROP TABLE mostrecentacks");
	      }catch (Exception e) {
	         e.printStackTrace(System.out);
	      }
	}
	public Connection getDBConn() {
	    try {
	        Class.forName("org.hsqldb.jdbcDriver");
	        dbConn = DriverManager.getConnection(
	                "jdbc:hsqldb:hsql://localhost/militarydb", "SA", "");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dbConn;
	}
}

