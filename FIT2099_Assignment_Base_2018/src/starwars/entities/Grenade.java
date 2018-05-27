package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Take;
import starwars.actions.Throw;

/**
 * An entity that has the <code>THROWABLE</code> capability and so can
 * be used to <code>Throw</code> and deal area damage to others
 * 
 * @author 	Kevin L
 * @see 	{@link starwars.actions.Throw}
 */
public class Grenade extends SWEntity {

	private int radius; //how big the damage will spread
	
	/**
	 * Constructor for the <code>Grenade</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Grenade</code></li>
	 * 	<li>Set the short description of this <code>Grenade</code> to "a grenade"</li>
	 * 	<li>Set the long description of this <code>Grenade</code> to "A grenade that can explode"</li>
	 * 	<li>Set the hit points of this <code>Grenade</code> to 20</li>
	 *  <li>Set the radius of this <code>Grenade</code> to 2</li>
	 * 	<li>Add a <code>Take</code> affordance to this <code>Grenade</code> so it can be taken</li> 
	 *  <li>Add a <code>Throw</code> affordance to this <code>Grenade</code> so it can be thrown</li> 
	 *	<li>Add a <code>THROWABLE Capability</code> to this <code>Grenade</code> so it can be used to <code>Throw</code></li>
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.Take}
	 * @see {@link starwars.actions.Throw}
	 * @see {@link starwars.Capability}
	 */
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
	
	/**
	 * A symbol that is used to represent the <code>Grenade</code> on a text based user interface
	 * 
	 * @return 	Single Character string "g"
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return "g"; 
	}
	
	/**
	 * Returns the radius (area of spread) of <code>Grenade</code> when thrown
	 * 
	 * @return 	the radius (area of spread) of <code>Grenade</code> when thrown
	 */
	public int getRadius() {
		return this.radius;
	}

}
