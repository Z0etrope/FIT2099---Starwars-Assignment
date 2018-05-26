package starwars.actions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.entities.Grenade;

public class Throw extends SWAffordance implements SWActionInterface {

	public Throw(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}

	/**
	 * Returns the time is takes to perform this <code>Throw</code> action.
	 * 
	 * @return The duration of the Throw action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}
	
	@Override
	public boolean canDo(SWActor a) {
		SWEntityInterface item = a.getItemCarried();
		if ((item!= null) && (item == this.target)) {
			return item.hasCapability(Capability.THROWABLE);
		}
		return false;
	}

	@Override
	public void act(SWActor a) {
		if (target instanceof Grenade) {
			Grenade theItem = (Grenade) target;
			int hitpoints = theItem.getHitpoints();
			int radius = theItem.getRadius();
			EntityManager<SWEntityInterface, SWLocation> em = SWAction.getEntitymanager();
			SWLocation loc = (SWLocation)em.whereIs(a);
			
			a.say(a.getShortDescription() + " throws " + target.getShortDescription() + " at " + loc.getShortDescription());
			
			//locationMap stores the locations that are inside queue along with the step 
				//count from the actor location
			//locationQueue stores the locations that will be checked
			Map<SWLocation,Integer> locationMap = new HashMap<SWLocation,Integer>();
			Queue<SWLocation> locationQueue = new LinkedList<>();
			
			locationMap.put(loc, 0); //initialize Map and Queue to have actor location
			locationQueue.add(loc);
			
			//loops until no more location is checked (queue is empty)
			while (locationQueue.peek() != null) {
				SWLocation nextLoc = locationQueue.remove(); //get the next location from queue
				int step = locationMap.get(nextLoc); //gets the step for the location
				if (step < radius) { //when step is less than radius, this means there are more
										//locations to check
					//Add all locations around it to locationMap and Queue
					for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
						SWLocation neighbourLoc = (SWLocation)nextLoc.getNeighbour(d);
						//add when location is accessible and not inside queue yet
						if ((neighbourLoc != null) && (!locationMap.containsKey(neighbourLoc))) {
							locationMap.put(neighbourLoc,step+1);
							locationQueue.add(neighbourLoc);
						}
					}
				}
				
				List<SWEntityInterface> entities = em.contents(nextLoc);
				dealDamage(hitpoints,step,entities,a); //deal damage to all entites on location
			}
			
			//actors use energy to throw items
			//throwing needs more energy than to attack with weapon so it deals 2 damage
			//because when attacking with weapon, it uses 1 energy
			a.takeDamage(2); 
			a.setItemCarried(null); //remove grenade for good
		}
	}

	public void dealDamage(int hitpoints, int step, List<SWEntityInterface> entities, SWActor a) {
		if (entities == null) return;
		for (SWEntityInterface e : entities) {
			// Actor cannot damage itself
			if( e != a ) {
				for (Affordance aff : e.getAffordances()) {
					if (aff instanceof Damageable) {
						int damage = (int)Math.round(hitpoints/Math.pow(2,step));
						e.takeDamage(damage);
						a.say("\t" + e.getShortDescription() + " takes " + Integer.toString(damage) + " damage!");
						if (e.getHitpoints() <= 0) {
							//remove Damageable affordance if entity is dead so it cannot be damaged again
							e.removeAffordance(aff); 
						}
						break;
					}
				}
			}
		}
	}
	
	/**
	 * A String describing what this <code>Throw</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "throw " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "throw " + this.target.getShortDescription();
	}

}
