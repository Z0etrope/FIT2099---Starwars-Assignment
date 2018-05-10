/**
 * 
 */
package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
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
			SWEntityInterface itemCarried = a.getItemCarried();
			if (itemCarried != null) {//if the actor is carrying an item 
				if (itemCarried.hasCapability(Capability.WEAPON)) {
					target.takeDamage(itemCarried.getHitpoints() + 1); // blunt weapon won't do much, but it will still do some damage
					itemCarried.takeDamage(1); // weapon gets blunt
					a.takeDamage(energyForAttackWithWeapon); // actor uses energy to attack
				}
				else {//an attack with a none weapon
					if (targetIsActor) {
						targetActor.say("\t" + targetActor.getShortDescription()
								+ " is amused by " + a.getShortDescription()
								+ "'s attempted attack with "
								+ itemCarried.getShortDescription());
					}
				} 
			}
			else { // attack with bare hands
				target.takeDamage((a.getHitpoints()/20) + 1); // a bare-handed attack doesn't do much damage.
				a.takeDamage(2*energyForAttackWithWeapon); // actor uses energy. It's twice as tiring as using a weapon
			}
			
			
			
			//After the attack
			
			if (a.isDead()) {//the actor who attacked is dead after the attack
							
				a.setLongDescription(a.getLongDescription() + ", that died of exhaustion while attacking someone");
				
				//remove the attack affordance of the dead actor so it can no longer be attacked
				a.removeAffordance(this);
				
				
			}
			if (this.getTarget().getHitpoints() <= 0) {  // can't use isDead(), as we don't know that the target is an actor
				target.setLongDescription(target.getLongDescription() + ", that was killed in a fight");
							
				//remove the attack affordance of the dead actor so it can no longer be attacked
				targetActor.removeAffordance(this);

				
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
