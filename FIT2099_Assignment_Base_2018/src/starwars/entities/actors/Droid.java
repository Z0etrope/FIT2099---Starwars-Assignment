/**
 * Class for a Droid.
 * Droid must have an owner, or else it will not move
 * Droids will follow the owner if it is nearby (1 location around it)
 * If droid and owner is separated, droid will move at a random direction
 * until it hits a wall, then randoms again
 * 
 * Droids take 10 damage in Badlands
 * At this point, droids cannot attack nor be attacked
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
		if (isImmobile()) {
			return;
		}
		this.direction = path.followOwner(this, this.owner, this.world);
		say(getShortDescription() + "is heading " + this.direction + " next.");
		Move myMove = new Move(this.direction, messageRenderer, world);
		scheduler.schedule(myMove, this, 1);
		
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
	
	public String evaluateLocation(){
		SWLocation location = this.world.getEntityManager().whereIs(this);
		if (location.getSymbol() == 'b'){
			this.takeDamage(10);
			return(getShortDescription() + " takes 10 damages in Badlands");
		}
		else {
			return(this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription());
		}
	}

	public void setAutoPilot(boolean ap){
		this.autoPilot = ap;
	}

	public boolean getAutoPilot(){
		return this.autoPilot;
	}

	public void setOwner(SWActor newOwner){
		this.owner = newOwner;
	}

	public SWActor getOwner(){
		return this.owner;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return this.direction;
	}

	public boolean isImmobile(){
		return isDead() || this.owner == null;
	}

}
