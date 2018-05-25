package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class Damageable extends SWAffordance implements SWActionInterface {

	/**
	 * Constructor for the <code>Damageable</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Damageable</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being attacked
	 * @param m message renderer to display messages
	 */
	public Damageable(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);	
		priority = 1;
	}

	/**
	 * Returns the time is takes to perform this <code>Damageable</code> action.
	 * 
	 * @return The duration of the Damage action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}
	
	/**
	 * Determine whether a particular <code>SWActor a</code> can attack the target.
	 * 
	 * @author 	Kevin L
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	false any <code>SWActor</code> cannot directly damage an entity,
	 * 			they need to either attack or throw an entity
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
