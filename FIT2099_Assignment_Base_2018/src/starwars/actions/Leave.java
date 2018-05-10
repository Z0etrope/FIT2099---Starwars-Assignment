/**
 * 
 */
package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;

/**
 * @author Kevin L
 *
 */
public class Leave extends SWAffordance {

	/**
	 * @param theTarget
	 * @param m
	 */
	public Leave(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}

	/* (non-Javadoc)
	 * @see starwars.SWActionInterface#canDo(starwars.SWActor)
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return a.getItemCarried() != null;
	}

	/* (non-Javadoc)
	 * @see starwars.SWActionInterface#act(starwars.SWActor)
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		if (target instanceof SWEntityInterface) {
			SWEntityInterface theItem = (SWEntityInterface) target;
			a.setItemCarried(null);
			SWLocation loc = (SWLocation)SWAction.getEntitymanager().whereIs(a);
			SWAction.getEntitymanager().setLocation(theItem, loc);//add the target to the entity manager on the location of SWActor
			
			//remove the leave affordance and add take affordance
			target.removeAffordance(this);
			target.addAffordance(new Take(theItem, this.messageRenderer));
		}
	}

	/* (non-Javadoc)
	 * @see edu.monash.fit2099.simulator.matter.Action#getDescription()
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "leave " + target.getShortDescription();
	}

}
