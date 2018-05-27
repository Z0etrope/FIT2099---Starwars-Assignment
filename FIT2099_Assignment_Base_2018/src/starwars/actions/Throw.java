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

/**
 * Command to throw entities to deal damage to an area around them.
 * <p>
 * The location for the throw is the location of the entity doing the action
 * and the damage will spread as far as the specified radius of the throwable entity
 * 
 * This affordance is attached to all throwable entities
 * 
 * @author Kevin L
 */
public class Throw extends SWAffordance implements SWActionInterface {

	/**
	 * Constructor for the <code>Throw</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Throw</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being thrown
	 * @param m message renderer to display messages
	 */
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
	
	/**
	 * Determine whether a particular <code>SWActor a</code> can throw the target.
	 * <p>
	 * The target should be carried by the actor and should have <code>THROWABLE</code>
	 * capability
	 * 
	 * @author 	Kevin L
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if <code>SWActor</code> carries the target and target has <code>THROWABLE</code>
	 * 			capability, otherwise return false
	 */
	@Override
	public boolean canDo(SWActor a) {
		SWEntityInterface item = a.getItemCarried();
		if ((item!= null) && (item == this.target)) { //target must be the same with item carried by actor
			return item.hasCapability(Capability.THROWABLE); //item should have THROWABLE capability
		}
		return false;
	}

	/**
	 * Perform the <code>Throw</code> command on an entity.
	 * <p>
	 * This method will get the <code>Location</code> of the <code>SWActor a</code>, get the
	 * <code>radius</code> of the target item, and deal damage to all <code>SWEntity</code> according to
	 * their distance from the <code>Location</code> of <code>SWActor a</code>.
	 * <p>
	 * Damage done to each entities will be calculated by counting the target's hitpoints divided by 
	 * 2^(distance from <code>SWActor a</code> <code>Location</code>, rounded down.
	 * <p>
	 * Then, it would tire the <code>SWActor a</code> and remove the item carried by <code>SWActor a</code>.
	 * 
	 * @author 	Kevin L
	 * @param 	a the <code>SWActor</code> who is throwing its item carried
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @pre		this method can only be called if the item carried by <code>SWActor a</code> has <code>THROWABLE</code> capability
	 * @pre		an <code>Throw</code> must not deal damage on <code>SWEntities</code> with 0 or less hitpoints
	 * @post	if a <code>SWActor</code>dies in an <code>Attack</code> their <code>Attack</code> affordance would be removed
	 * @see		starwars.entities.Grenade
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof Grenade) {
			Grenade theItem = (Grenade) target;
			int hitpoints = theItem.getHitpoints(); //maximum damage that can be dealt
			int radius = theItem.getRadius(); //distance for the area damage
			EntityManager<SWEntityInterface, SWLocation> em = SWAction.getEntitymanager();
			SWLocation loc = (SWLocation)em.whereIs(a); //gets the location of actor
			
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

	/**
	 * The method that resolves the damage calculation for <code>Throw</code> action.
	 * <p>
	 * Damage done to each entities will be calculated by counting the target's hitpoints divided by 
	 * 2^(distance from <code>SWActor a</code> <code>Location</code>, rounded down.
	 * 
	 * @author 	Kevin L
	 * @param 	hitpoints the maximum damage that can be dealt to any entities
	 * @param  	step the distance from <code>SWActor a</code> <code>Location</code>
	 * @param	entities the list of entities that will take damage
	 * @param 	a the <code>SWActor</code> who is throwing its item carried
	 * @pre		an <code>Throw</code> must not deal damage on <code>SWEntities</code> with 0 or less hitpoints
	 * @post	if a <code>SWActor</code>dies in an <code>Attack</code> their <code>Attack</code> affordance would be removed
	 * @see		starwars.entities.Grenade
	 */
	public void dealDamage(int hitpoints, int step, List<SWEntityInterface> entities, SWActor a) {
		if (entities == null) return;
		for (SWEntityInterface e : entities) {
			// Actor cannot damage itself
			if( e != a ) {
				if (e.getHitpoints() > 0) {
					int damage = (int)Math.round(hitpoints/Math.pow(2,step));
					e.takeDamage(damage);
					a.say("\t" + e.getShortDescription() + " takes " + Integer.toString(damage) + " damage!");
				}
				if (e.getHitpoints() <= 0) {  // can't use isDead(), as we don't know that the target is an actor
					e.setLongDescription(e.getLongDescription() + ", died by the explosion of " + target.getShortDescription());
					//remove the attack affordance of the dead actor so it can no longer be attacked
					for (Affordance aff : e.getAffordances()) {
						if (aff instanceof Attack) {
							e.removeAffordance(aff);
							break;
						}
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
