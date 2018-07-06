package tools;

public class AgentDiag {
	private String name;
	private String flag;
	private long timestamp;

	public AgentDiag(String name, String flag, long timestamp) {
		this.name = name;
		this.flag = flag;
		this.timestamp = timestamp;
	}
	
	public AgentDiag(String name) {
		this.name = name;
	}
	
	/**
	 * Overriding equals() method
	 * (C) www.codejava.net
	 */
	public boolean equals(Object obj) {
	    if (obj instanceof AgentDiag) {
	        AgentDiag another = (AgentDiag) obj;
	        if (this.name.equals(another.name)) {
	                return true;
	        }
	    }
	 
	    return false;
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

	public void setFlag(String jobTitle) {
		this.flag = jobTitle;
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
    	String time;
    	age = age / 1000;
    	if ( age > 60 ) {
    		age = age / 60;
    		time = age.intValue() +" min";
    	} else {
        	Integer mytimestampy = age.intValue();
        	time = mytimestampy + " sec";
    	}
		return ("<html><p style=\"color:"+flag+"\">"+ name +
    			" DiagFlag : "+ flag + " age: "+ time +"<br/>");
	}
}
