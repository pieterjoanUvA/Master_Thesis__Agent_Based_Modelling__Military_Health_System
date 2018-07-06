package tools;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class AgentDiagChainedComparator implements Comparator<AgentDiag>{
	private List<Comparator<AgentDiag>> listComparators;
	
	@SafeVarargs
	public AgentDiagChainedComparator(Comparator<AgentDiag>...comparators ) {
		this.listComparators = Arrays.asList(comparators);
	}
	
	public int compare(AgentDiag name1, AgentDiag name2) {
		for (Comparator<AgentDiag> comparator : listComparators) {
			int result = comparator.compare(name1, name2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}




