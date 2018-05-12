package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Force;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.Team;

/**
 * Command to train entities (get their force level higher).
 * 
 * This affordance is attached only to those chosen by the Force
 * 
 * @author Kevin L
 */
public class Train extends SWAffordance implements SWActionInterface {

	/**
	 * Constructor for the <code>Train</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Train</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being trained
	 * @param m message renderer to display messages
	 */
	public Train(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}

	/**
	 * Determine whether a particular <code>SWActor a</code> can train the target.
	 * 
	 * @author 	Kevin L
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if <code>SWActor</code> is not in <code>NEUTRAL Team</code> or <code>TUSKEN Team</code>
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return (a.getTeam() != Team.NEUTRAL && a.getTeam() != Team.TUSKEN);
	}

	/**
	 * Perform the <code>Train</code> command on an entity.
	 * <p>
	 * This method does not raise the force of the target (train) if,
	 * <ul>
	 * 	<li>The target of the <code>Train</code> and the <code>SWActor a</code> are in different <code>Team</code></li>
	 * 	<li>The <code>Force</code> force level of <code>SWActor a</code> is less than the <code>Force</code>
	 * 		force level of the target of the <code>Train</code></li>
	 * </ul>
	 * <p>
	 * 
	 * TODO : compare the <code>Force</code> force level of <code>SWActor a</code>and the <code>Force</code>
	 * 		force level of the target of the <code>Train</code> before training.
	 * 
	 * @author 	Kevin L
	 * @param 	a the <code>SWActor</code> who is training
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @pre		<code>Train</code> must not be performed on a dead <code>SWActor</code>
	 * @see		starwars.SWActor#isDead()
	 * @see 	starwars.Team
	 * @see		starwars.Force
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
		
		if (targetIsActor && (a.getTeam() != targetActor.getTeam())) { //don't train SWActors in different team
			a.say("\t" + a.getShortDescription() + " says: I cannot train " + target.getShortDescription() + ". We're enemies!");
		}
		else if (targetIsActor && (a.getTeam() == targetActor.getTeam())){// actors can only train those in same team
			a.say(a.getShortDescription() + " is training " + target.getShortDescription() + "!");
			
			//not updated yet
			//need way to access Force and Force input in each actors
			//get Force for actor and target
			Force actorForce = a.getForceActor();
			Force targetForce = targetActor.getForceActor();
			//activate Force when actor Force is greater than target force
			if (actorForce.getForceLevel() > targetForce.getForceLevel()) {
				targetForce.setForce(true);
				targetForce.incForceLevel(1);
				a.say("\t" + a.getShortDescription() + " says: You've completed my training, " + target.getShortDescription()
								+ ". Now go out there, and may the Force be with you!");
			} else { //else prompt something
				a.say("\t" + a.getShortDescription() + " says: There is nothing more I can teach you, " + target.getShortDescription());
			}
			
			
		} // not game player and different teams
	}

	/**
	 * A String describing what this <code>Train</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "train " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "train " + this.target.getShortDescription();
	}

}
