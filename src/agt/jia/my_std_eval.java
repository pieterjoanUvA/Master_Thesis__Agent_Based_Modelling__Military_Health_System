// Internal action code for project military_health

package jia;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class my_std_eval extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
    	final double value = (double)((NumberTerm)args[0]).solve();
        final double meanvalue = (double)((NumberTerm)args[1]).solve();
        final double desired_std_th = (double)((NumberTerm)args[2]).solve();
        final double std_dev_value = (double)((NumberTerm)args[3]).solve();
        final double min_threshold = (double)((NumberTerm)args[4]).solve();
        
        double difference = meanvalue - value;
        if (difference == 0 | difference > 0 ) {
        	// nothing to do
        } else if (difference <= 0) {
        	difference = difference * -1;
        }
        if (std_dev_value == 0) {
        	return un.unifies(args[5], new StringTermImpl("positive"));
        }
        if (difference < (min_threshold) | difference < (std_dev_value * desired_std_th) ) {
        	return un.unifies(args[5], new StringTermImpl("positive"));
        } else if (difference > (std_dev_value * desired_std_th) ) {
        	return un.unifies(args[5], new StringTermImpl("negative"));
        }
        return un.unifies(args[5], new StringTermImpl("negative"));

    }
}
