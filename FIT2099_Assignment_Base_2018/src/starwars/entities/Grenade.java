package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Take;
import starwars.actions.Throw;

public class Grenade extends SWEntity {

	private int radius;
	
	public Grenade(MessageRenderer m) {
		super(m);
		this.shortDescription = "a grenade";
		this.longDescription = "A grenade that can explode";
		this.hitpoints = 20; // always set grenade to have 20 hp
		this.radius = 2;
		
		this.addAffordance(new Take(this, m));//add the Take affordance so that the blaster can be picked up
		this.addAffordance(new Throw(this,m));
		
													//the blaster has capabilities 
		this.capabilities.add(Capability.THROWABLE);   // and WEAPON so that it can be used to attack
	}
	
	@Override
	public String getSymbol() {
		return "g"; 
	}
	
	public int getRadius() {
		return this.radius;
	}

}
