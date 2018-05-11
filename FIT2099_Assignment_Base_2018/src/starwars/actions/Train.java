/**
 * 
 */
package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Force;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.Team;

/**
 * @author Kevin L
 *
 */
public class Train extends SWAffordance implements SWActionInterface {

	/**
	 * @param theTarget
	 * @param m
	 */
	public Train(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see starwars.SWActionInterface#canDo(starwars.SWActor)
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return (a.getTeam() != Team.NEUTRAL && a.getTeam() != Team.TUSKEN);
	}

	/* (non-Javadoc)
	 * @see starwars.SWActionInterface#act(starwars.SWActor)
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

	/* (non-Javadoc)
	 * @see edu.monash.fit2099.simulator.matter.Action#getDescription()
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "train " + this.target.getShortDescription();
	}

}
