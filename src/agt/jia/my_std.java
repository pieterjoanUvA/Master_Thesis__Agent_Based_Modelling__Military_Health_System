// Internal action code for project military_health

package jia;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class my_std extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
       // ts.getAg().getLogger().info("executing internal action 'jia.my_std'");
       // if (true) { // just to show how to throw another kind of exception
        //    throw new JasonException("not implemented!");
        //}
        if (args[0].isList()) {
            double sum = 0, squareSum = 0, num;
            int    n = 0;
            for (Term t: (ListTerm)args[0])
                if (t.isNumeric()) {
                    if (t.isNumeric()) {
                        num = ((NumberTerm)t).solve();
                        sum += num;
                        n++;
                    }
                }
            double mean = sum  / n;
            for (Term t: (ListTerm)args[0])
                if (t.isNumeric()) {
                    num = ((NumberTerm)t).solve();
                    squareSum += (num - mean) * (num - mean);
                }
            double d = Math.sqrt( squareSum/(n - 1) );
            
            // new rounding method
            int scale = (int) Math.pow(10, 1);
            d =  (double) Math.round(d * scale) / scale;
            
            return un.unifies(args[1], new NumberTermImpl(d));
            /* For 2 return variables -=taken from ag_pos.java
             *         return un.unifies(terms[1], new NumberTermImpl(l.x)) &&
                       un.unifies(terms[2], new NumberTermImpl(l.y));
             * 
             */
        }
        // everything ok, so returns true
        return true;
        //return d;
    }
}
