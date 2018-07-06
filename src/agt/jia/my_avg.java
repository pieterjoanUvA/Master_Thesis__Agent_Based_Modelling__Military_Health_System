// Internal action code for project military_health

package jia;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;
import jason.JasonException;
import jason.asSemantics.DefaultArithFunction;
import jason.asSemantics.TransitionSystem;
import jason.asSyntax.ListTerm;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Term;

public class my_avg extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
       // ts.getAg().getLogger().info("executing internal action 'jia.my_avg'");
       // if (true) { // just to show how to throw another kind of exception
       //     throw new JasonException("not implemented!");
       // }
        if (args[0].isList()) {
        	
            double sum = 0;
            int    n = 0;
            for (Term t: (ListTerm)args[0])
                if (t.isNumeric()) {
                    sum += ((NumberTerm)t).solve();
                    n++;
                   // String mysum = Double.toString(sum);
                   // ts.getAg().getLogger().info(mysum);
                }
            double d = sum / n ;
            // new rounding method
            int scale = (int) Math.pow(10, 1);
            d =  (double) Math.round(d * scale) / scale;
            //String myresult = Double.toString(d);
            //ts.getAg().getLogger().info(myresult);
            return un.unifies(args[1], new NumberTermImpl(d));
           // return d;
        }
       // throw new JasonException(getName()+" is not implemented for type '"+args[0]+"'.");
        // everything ok, so returns true
        return true;
    }
}
