package starwars;

import java.util.ArrayList;

public class Follow{
	private Droid droid;
	private SWActor owner;

	public void follow(Droid newDroid, SWActor newOwner){
		this.droid = newDroid;
		this.owner = newOwner;
	}
	

	public boolean ownerIsNearby(){

	}

	public Direction ownerDirection(){
		
	}

	public Direction randomDirection(){

		ArrayList<Direction> possibledirections = new ArrayList<Direction>();

			// build a list of available directions
			for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
				if (SWWorld.getEntitymanager().seesExit(this, d)) {
					possibledirections.add(d);
				}
			}
		Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
		
		return heading;
	}

	public void setDroid(Droid newDroid){
		this.droid = newDroid;
	}

	public Droid getDroid(){
		return this.droid;
	}

	public void setOwner(SWActor newOwner){
		this.owner = newOwner;
	}

	public SWActor getOwner(){
		return this.owner;
	}
}