package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.Droid;

public class Follow {

	
	public Direction followOwner(Droid droid, SWActor owner, SWWorld world) {
		SWLocation droidLocation = world.getEntityManager().whereIs(droid);
		SWLocation ownerLocation = world.getEntityManager().whereIs(owner);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		if (droidLocation == ownerLocation) { //when droid in the same location with owner, do nothing
			return null;
		}
		//use droid location and get neighbour location for all possible directions from compassbearing
		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
			SWLocation neighbourLoc = (SWLocation)droidLocation.getNeighbour(d);
			List<SWEntityInterface> entities = em.contents(neighbourLoc);
			if (entities != null) {
				for (SWEntityInterface e : entities) { //check if at each location owner is present
					if (e == owner) {
						droid.setAutoPilot(false);
						return d; //if present, return that direction
					}
				}
			}
		}
		//if not, return randomDirection
		return randomDirection(droid);
	}

	public Direction randomDirection(Droid droid){
		//check for autopilot
		//autopilot means it already has a random direction
		//if autopilot is on, get the direction of droid
		//check if the going that direction is possible, if it is then return that direction
		//if not possible, then random
		//if no autopilot, set autopilot on first then random
		if (droid.getAutoPilot()) {
			if (SWWorld.getEntitymanager().seesExit(droid, droid.getDirection())) {
				return droid.getDirection();
			}
		} else {
			droid.setAutoPilot(true);
		}

		ArrayList<Direction> possibledirections = new ArrayList<Direction>();

		// build a list of available directions
		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
			if (SWWorld.getEntitymanager().seesExit(droid, d)) {
				possibledirections.add(d);
			}
		}
		Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
		
		return heading;
	}
}