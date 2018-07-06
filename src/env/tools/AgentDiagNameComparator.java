package tools;

import java.util.Comparator;

public class AgentDiagNameComparator implements Comparator<AgentDiag> {
	
	public int compare(AgentDiag name1, AgentDiag name2) {
		return name1.getName().compareTo(name2.getName());
	}

}


