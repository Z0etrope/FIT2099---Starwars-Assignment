package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Train;

public class TrainNeighbours {


	public static AttackInformation trainLocals(SWActor actor, SWWorld world, boolean avoidFriendlies, boolean avoidNonActors) {
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);

		// select the attackable things that are here

		ArrayList<AttackInformation> trainables = new ArrayList<AttackInformation>();
		for (SWEntityInterface e : entities) {
			// Figure out if we should be attacking this entity
			if( e != actor && 
					(e instanceof SWActor && 
							(avoidFriendlies==false || ((SWActor)e).getTeam() == actor.getTeam()) 
					|| (avoidNonActors == false && !(e instanceof SWActor)))) {
				for (Affordance a : e.getAffordances()) {
					if (a instanceof Train) {
						if (((SWActor)e).getForceActor().getForceLevel() < actor.getForceActor().getForceLevel()) {
							trainables.add(new AttackInformation(e, a));
							break;
						}
					}
				}
			}
		}

		// if there's at least one thing we can attack, randomly choose
		// something to attack
		if (trainables.size() > 0) {
			return trainables.get((int) (Math.floor(Math.random() * trainables.size())));
		} else {
			return null;
		}
	}
}
