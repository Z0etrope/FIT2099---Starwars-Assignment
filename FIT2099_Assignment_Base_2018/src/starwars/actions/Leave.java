package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> leave an object they are holding on the location they are standing on.
 * 
 * @author Kevin L
 */
public class Leave extends SWAffordance {

	/**
	 * Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being left
	 * @param m the message renderer to display messages
	 */
	public Leave(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}

	/**
	 * Returns if or not this <code>Leave</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying an item.
	 *  
	 * @author 	Kevin L
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can leave this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a.getItemCarried() != null;
	}

	/**
	 * Perform the <code>Leave</code> action by setting the item carried by the <code>SWActor</code> to null
	 * and adding the item carried back to <code>EntityManager</code> on the location of <code>SWActor a</code>
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	Kevin L
	 * @param 	a the <code>SWActor</code> that is leaving the target
	 * @see 	{@link #theTarget}
	 */
	@Override
	public void act(SWActor a) {
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

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author Kevin L
	 * @return String comprising "leave " and the short description of the target of this <code>Leave</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "leave " + target.getShortDescription();
	}

}
