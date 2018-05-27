package starwars.entities;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.actions.Attack;
import starwars.actions.Dip;

/**
 * Class to represent a water reservoir.  <code>Reservoirs</code> are currently pretty passive.
 * They can be dipped into to fill fillable entities (such as <code>Canteens</code>.  They
 * are assumed to have infinite capacity.
 * 
 * @author 	ram
 * @see 	{@link starwars.entities.Canteen}
 * @see {@link starwars.entites.Fillable}
 * @see {@link starwars.actions.Fill} 
 */
public class Reservoir extends SWEntity {

	/**
	 * Constructor for the <code>Reservoir</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Reservoir</code></li>
	 * 	<li>Set the short description of this <code>Reservoir</code> to "a water reservoir</li>
	 * 	<li>Set the long description of this <code>Reservoir</code> to "a water reservoir..."</li>
	 * 	<li>Add a <code>Dip</code> affordance to this <code>Reservoir</code> so it can be taken</li> 
	 *	<li>Set the symbol of this <code>Reservoir</code> to "T"</li>
	 * </ul>
	 * 
	 * @param 	m <code>MessageRenderer</code> to display messages.
	 * @see 	{@link starwars.actions.Dip} 
	 */
	public Reservoir(MessageRenderer m) {
		super(m);
		this.hitpoints = 40; //add hp to reservoir, always start at 40 hp
		
		SWAffordance dip = new Dip(this, m);
		this.addAffordance(dip);	
		
		this.setLongDescription("a water reservoir.");
		this.setShortDescription("a water reservoir, full of cool, clear, refreshing water");
		this.setSymbol("W");
	}

	@Override 
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}
	
	/**
	 * Method insists damage on this <code>Reservoir</code> by reducing a certain 
	 * amount of <code>damage</code> from this <code>Swords</code> <code>hitpoints</code>
	 * <p>
	 * This method will also change this <code>Blaster</code>s <code>longDescription</code> to
	 * "A broken sword that was once gleaming"  and this <code>Blaster</code>s <code>shortDescription</code> to
	 * "a broken sword" if the <code>hitpoints</code> after taking the damage is zero or less.
	 * <p>
	 * If the <code>hitpoints</code> after taking the damage is zero or less, this method will remove the 
	 * <code>CHOPPER</code> and <code>WEAPON</code> capabilities from this <code>Blaster</code> since a broken sword 
	 * cannot be used to <code>Chop</code> or <code>Attack</code>.
	 * <p>
	 * 
	 * @author 	Asel
	 * @param 	damage the amount of <code>hitpoints</code> to be reduced
	 * @see 	{@link starwars.actions.Attack}
	 */
	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		
		if (this.hitpoints<=0) {
			this.shortDescription = "the wreckage of a water reservoir";
			this.longDescription  = "the wreckage of a water reservoir surrounded by slightly damp soil";
			this.setSymbol("X");
			
			//surrounded by slightly damp soil means no more water, so cannot fill water here anymore
			//therefore remove dip affordance
			for (Affordance a : this.getAffordances()) {
				if (a instanceof Dip) {
					this.removeAffordance(a);
				}
			}
			
		} else if (this.hitpoints<20) {
			this.shortDescription = "a damaged water reservoir";
			this.longDescription  = "a damaged water reservoir, leaking slowly";
			this.setSymbol("V");
		}
	}
	
}
