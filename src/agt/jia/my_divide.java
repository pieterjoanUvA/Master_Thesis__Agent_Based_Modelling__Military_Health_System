// Internal action code for project military_health

package jia;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class my_divide extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
       // ts.getAg().getLogger().info("executing internal action 'jia.my_avg'");
       // if (true) { // just to show how to throw another kind of exception
       //     throw new JasonException("not implemented!");
       // }
       // if (args[0].isList()) {
        	
        	final double first = (double)((NumberTerm)args[0]).solve();
            final double second = (double)((NumberTerm)args[1]).solve();
            double d = first / second ;
            // new rounding method
            int scale = (int) Math.pow(10, 1);
            d =  (double) Math.round(d * scale) / scale;

            return un.unifies(args[2], new NumberTermImpl(d));
            /* For 2 return variables -=taken from ag_pos.java
             *         return un.unifies(terms[1], new NumberTermImpl(l.x)) &&
                       un.unifies(terms[2], new NumberTermImpl(l.y));
             * 
             */
      //  }
       // throw new JasonException(getName()+" is not implemented for type '"+args[0]+"'.");
        // everything ok, so returns true
        //return true;
    }
}
