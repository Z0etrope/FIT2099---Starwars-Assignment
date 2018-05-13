package starwars;

import java.util.ArrayList;


public class Droid extends SWActor {
	private boolean follow;
	private boolean autoPilot;
	private SWActor owner;
	private Follow path;
	private Direction direction;

	public Droid(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.NEUTRAL, 50, m, world);
		this.owner = null;
		this.autoPilot = true;
		this.direction = null;
		path = new Follow(this, this.owner);
	}

	@Override
	public void act(){
		if (isImmobile()) {
			return;
		}
		say(evaluateLocation());
		move();

	}

	@Override
	public String getShortDescription() {
		return name + " the Droid";
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	public void toggleAutoPilot(){
		this.autoPilot = !this.autoPilot;
	}

	public String evaluateLocation(){
		SWLocation location = this.world.getEntityManager().whereIs(this);
		if (location.getSymbol().equals("b")){
			this.hitpoints -= 10;
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
		this.path.setOwner = newOwner;
	}

	public SWActor getOwner(){
		return this.owner;
	}

	public boolean isImmobile(){
		return isDead() || this.owner == null;
	}

	public void move(){
		if (this.direction != null && this.direction.seesExit(this, direction)){
			say(getShortDescription() + "is heading " + this.direction + " next.");
			Move myMove = new Move(this.direction, messageRenderer, world);
			scheduler.schedule(direction, this, 1);
		}
		else{
			this.direction = path.randomDirection();
			say(getShortDescription() + "is heading " + this.direction + " next.");
			Move myMove = new Move(this.direction, messageRenderer, world);
			scheduler.schedule(this.direction, this, 1);
		}
	}
}
