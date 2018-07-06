// Internal action code for project military_health

package jia;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class myTime extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        //ts.getAg().getLogger().info("executing internal action 'jia.myTime'");
        
        long currTimeGotten = System.currentTimeMillis();
        // everything ok, so returns true
        return un.unifies(args[0], new NumberTermImpl(currTimeGotten));
    }
}
