package tools;

public class Diagnosed {
	private String name;
	private String kind, status, values, team;
	private String flag;
	private long timestamp;
//diagnosed(Kind,Status,Values,Flag,AgentString,TName,TimeStamp)
	public Diagnosed(String kind, String status, String values, String flag, String name, String team, long timestamp) {
		this.name = name;
		this.kind = kind;
		this.status = status;
		this.values = values; //values of sensors can be converted back to double for recalculation of diagnoses
		this.flag = flag;
		this.team = team;
		this.timestamp = timestamp;
	}
	
	public Diagnosed(String name) {
		this.name = name;
	}
	
	/**
	 * Overriding equals() method
	 * (C) www.codejava.net
	 */
	public boolean equals(Object obj) {
	    if (obj instanceof Diagnosed) {
	        Diagnosed another = (Diagnosed) obj;
	        if (this.name.equals(another.name)) {
	                return true;
	        }
	    }
	 
	    return false;
	}
	public String getTeam() {
		return team;
	}
	public String getValues() {
		return values;
	}
	public String getStatus() {
		return status;
	}
	public String getKind() {
		return kind;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long age) {
		this.timestamp = age;
	}


	public String toString() {
		Long currenttime = System.currentTimeMillis();
    	Long age = currenttime - timestamp;
    	/* System.out.println in JaCaMo prints in MAS Console */
    	//System.out.println("curr "+currenttime+" timestamp "+timestamp);
    	age = age / 1000;
    	Integer mytimestampy = age.intValue();
		return ("<html><p style=\"color:"+flag+"\">"+ name +
    			" DiagFlag : "+ flag + " age: "+mytimestampy+" sec<br/>");
	}
}
