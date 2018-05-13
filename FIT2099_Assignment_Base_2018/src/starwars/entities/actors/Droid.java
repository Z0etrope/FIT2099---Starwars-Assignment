/**
 * Class for a Droid.
 * Droid must have an owner, or else it will not move
 * Droids will follow the owner if it is nearby (1 location around it)
 * If droid and owner is separated, droid will move at a random direction
 * until it hits a wall, then randoms again
 * 
 * Droids take 10 damage in Badlands
 * At this point, droids cannot attack nor be attacked
 * WILL FOLLOW YOU LITERALLY EVERYWHERE!
 * 
 * @author Kevin L 
 * @author Jason Setiawan 
 */
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
import starwars.actions.Move;
import starwars.entities.actors.behaviors.Follow;

	/**
	 * Constructor for the <code>Droid</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Droid</code></li>
	 * 	<li>Initialize the world for this <code>Droid</code></li>
	 *  <li>Initialize the <code>Team</code> for this <code>Droid</code></li>
	 * 	<li>Initialize the hit points for this <code>Droid</code></li>
	 * 	<li>initialize <code>Follow</code> class for the <code>Droid</code></li>
	 * </ul>
	 * 
	 * @param team the <code>Team</code> to which the this <code>Player</code> belongs to
	 * @param hitpoints the hit points of this <code>Player</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>Player</code> belongs to
	 * 
	 */
public class Droid extends SWActor {
	private boolean autoPilot;
	private SWActor owner;
	private Follow path;
	private Direction direction;
	private String name;

	public Droid(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.NEUTRAL, hitpoints, m, world);
		this.owner = null;
		this.autoPilot = false;
		this.direction = null;
		this.name = name;
		
		for (Affordance a : this.getAffordances()) {
			if (a instanceof Attack) {
				this.removeAffordance(a);
			}
		}
		
		path = new Follow();
	}
	
	public Droid(int hitpoints, String name, MessageRenderer m, SWWorld world, SWActor owner) {
		super(Team.NEUTRAL, hitpoints, m, world);
		this.owner = owner;
		this.autoPilot = false;
		this.direction = null;
		this.name = name;
		
		for (Affordance a : this.getAffordances()) {
			if (a instanceof Attack) {
				this.removeAffordance(a);
			}
		}
		
		path = new Follow();
	}

	@Override
	public void act(){
		if (isImmobile()) {// if it is immobile() it does not do anything.
			return;
		}
		this.direction = path.followOwner(this, this.owner, this.world);
		say(getShortDescription() + "is heading " + this.direction + " next.");
		Move myMove = new Move(this.direction, messageRenderer, world);//move to the direction created
		scheduler.schedule(myMove, this, 1);//move to myMove
		
		say(evaluateLocation());
	}

	@Override
	public String getShortDescription() {
		return name + " the Droid";
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}
	
	public String evaluateLocation(){// this method is used to check where the position of this Droid at the map
		SWLocation location = this.world.getEntityManager().whereIs(this);
		if (location.getSymbol() == 'b'){//if Droid is on Badlands it will take 10 damage
			this.takeDamage(10);
			return(getShortDescription() + " takes 10 damages in Badlands");
		}
		else {
			return(this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription());
		}
	}

	/**
	 * Sets the <code>autoPilot</code> of this <code>Droid</code>
	 * 
	 * @param 	ap value of the <code>autoPilot</code> of <code>Droid</code>.
	 * @pre 	the <code>ap</code> should be a boolean.
	 */
	public void setAutoPilot(boolean ap){
		this.autoPilot = ap;
	}

	/**
	 * Returns the autoPilot of <code>Droid</code> value.
	 * 
	 * @return The boolean value of <code>autoPilot</code>.
	 */
	public boolean getAutoPilot(){
		return this.autoPilot;
	}

	/**
	 * Sets the <code>owner</code> of this <code>Droid</code>
	 * 
	 * @param 	newOwner of the <code>Droid</code>.
	 * @pre 	the <code>newOwner</code> should be an SWActor object.
	 */
	public void setOwner(SWActor newOwner){
		this.owner = newOwner;
	}

	/**
	 * Returns the owner of this <code>Droid</code>.
	 * 
	 * @return The owner of <code>Droid</code>.
	 */
	public SWActor getOwner(){
		return this.owner;
	}
	
	/**
	 * Sets the <code>direction</code> of this <code>Droid</code> to the new <code>direction</code>
	 * 
	 * @param 	direction that should be taken by <code>Droid</code>
	 * @pre 	the new <code>direction</code> should be an object of Direction Class.
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/**
	 * Returns the direction of this <code>Droid</code>.
	 * 
	 * @return The <code>direction</code> of this <Droid>.
	 */
	public Direction getDirection() {
		return this.direction;
	}

	public boolean isImmobile(){//return true if this Droid does not have an owner or has 0 HP.
		return isDead() || this.owner == null;
	}

}
