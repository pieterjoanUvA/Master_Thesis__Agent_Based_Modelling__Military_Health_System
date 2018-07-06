// Internal action code for project military_health

package jia;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class my_multiply extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
    	final double first = (double)((NumberTerm)args[0]).solve();
        final double second = (double)((NumberTerm)args[1]).solve();
        double d = first * second ;
        // new rounding method
        int scale = (int) Math.pow(10, 1);
        d =  (double) Math.round(d * scale) / scale;
        //String myresult = Double.toString(d);
        //ts.getAg().getLogger().info(myresult);

       // return d;
        return un.unifies(args[2], new NumberTermImpl(d));
    }
}
