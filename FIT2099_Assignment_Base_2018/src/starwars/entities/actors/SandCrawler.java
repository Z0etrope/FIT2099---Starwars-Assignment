package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;
import starwars.actions.Damageable;
import starwars.actions.Move;
import starwars.actions.Throw;
import starwars.entities.actors.behaviors.Patrol;

public class SandCrawler extends SWActor {

	private Patrol path;
	private int counter;

	public SandCrawler(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.NEUTRAL, hitpoints, m, world);
		this.name = name;
		// Remove Attack Affordance
		for (Affordance a : this.getAffordances()) {
			if (a instanceof Attack) {
				this.removeAffordance(a);
			}
		}
		this.addAffordance(new Enter(this,m));
	}

	@Override
	public void act(){
		if(isDead()) {
			return;
		}

		SWLocation where = (SWLocation)entityManager.whereIs(this);
		List<SWEntityInterface> entities = em.contents(where);
		if (entities != null) {
			for (SWEntityInterface e : entities) {
				if (e instanceof Droid) {
					e.
				}
			}
			this.counter += 1;
		}

		else if(this.counter != 0){
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);
			scheduler.schedule(myMove, this, 1);
			this.counter = 0;
		}

		else{
			this.counter += 1;
		}
	}

	@Override
	public String getShortDescription() {
		return name + " SandCrawler";
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}
}