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
	private SWEntityInterface exitDoor;
/**
	 * Constructor for the <code>SandCrawler</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Droid</code></li>
	 * 	<li>Initialize the world for this <code>Droid</code></li>
	 *  <li>Initialize the <code>Team</code> for this <code>Droid</code></li>
	 * 	<li>Initialize the hit points for this <code>Droid</code></li>
	 * 	<li>initialize <code>exitDoor</code> class for the <code>SandCrawler</code></li>
	 * </ul>
	 * 
	 * @param team the <code>Team</code> to which the this <code>Player</code> belongs to
	 * @param hitpoints the hit points of this <code>Player</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>Player</code> belongs to
	 * @param exitDoor to define which SWEntity is hte exit door.
	 *@author Jason Setiawan
	 */

	public SandCrawler(int hitpoints, String name, SWEntityInterface exitDoor, MessageRenderer m, SWWorld world,Direction[] moves) {
		super(Team.NEUTRAL, hitpoints, m, world);
		this.name = name;
		this.counter = 0;
		// Remove Attack Affordance
		for (Affordance a : this.getAffordances()) {
			if (a instanceof Attack) {
				this.removeAffordance(a);
			}
		}
		path = new Patrol(moves);//Patrol from Ben Kenobi Behavior
		this.exitDoor = exitDoor;
		this.addAffordance(new Enter(this,this.exitDoor,m));// add an Enter affordance so the actor can enter the Interior
	}
	//set an Exit door for SandCrawler
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
				if (e instanceof Droid) {// if there is Droid between the entity
					loc = this.world.getSandCrawlerGrid().getLocationByCoordinates(0,0);// set the Droid's Location to the interior of SandCrawler
					em.setLocation(e,loc);
					this.say(e.getShortDescription() + "is taken inside " + this.getShortDescription());
				}
			}
			
		}

		if (this.counter%2 == 0){// SandCrawler only move each 2 turns
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);
			scheduler.schedule(myMove, this, 1);
			this.counter = 0;
		}

		
		this.counter += 1;
		
		
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