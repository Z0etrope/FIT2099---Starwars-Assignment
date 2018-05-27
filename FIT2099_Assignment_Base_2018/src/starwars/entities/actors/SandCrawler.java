package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;

import starwars.actions.Enter;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.Patrol;

public class SandCrawler extends SWActor {

	private Patrol path;
	private int counter;
	private String name;
	private SWEntity exitDoor;


	public SandCrawler(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.NEUTRAL, hitpoints, m, world);
		this.name = name;
		// Remove Attack Affordance
		for (Affordance a : this.getAffordances()) {
			if (a instanceof Attack) {
				this.removeAffordance(a);
			}
		}
		this.addAffordance(new Enter(this,exitDoor,m));
	}
	
	public void setExitDoor(SWEntity newExit) {
		this.exitDoor = newExit;
	}
	@Override
	public void act(){

		SWLocation loc;

		if(isDead()) {
			return;
		}

		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		SWLocation where = (SWLocation)em.whereIs(this);
		List<SWEntityInterface> entities = em.contents(where);
		
		if (entities != null) {
			for (SWEntityInterface e : entities) {
				if (e instanceof Droid) {
					loc = this.world.getSandCrawlerGrid().getLocationByCoordinates(0,0);
					em.setLocation(e,loc);
					this.say(e.getShortDescription() + "is taken inside " + this.getShortDescription());
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