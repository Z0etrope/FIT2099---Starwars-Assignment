package starwars;

/**
 * "Power to make Impossible possible"
 * A class to determine whether SWActor can do a certain action or not
 *
 *MAY THE FORCE BE WITH YOU
 * @see Attack
 * @see Train
 * @author Jason Setiawan
 */ 

 public class Force {
 	private boolean forceInIt;
 	private int forceLevel;

 	/**
	 * Constructor for the <code>Force</code> class. 
	 * 
	 * 
	 * @param force check whether you have force or not
	 * @param newForceLevel level of your force
	 */
 	public Force(boolean force, int newForceLevel){
 		this.forceInIt = force;
 		this.forceLevel = newForceLevel;
 	}

 	/**
	 * Returns the availability of <code>Force</code> power.
	 * 
	 * @return The availability of the Force power.
	 */
 	public boolean hasForce(){
 		return this.forceInIt;
 	}

 	/**
	 * Sets the <code>forceInIt</code> of this <code>Force</code> to the new <code>forceInIt</code>
	 * 
	 * @param 	force the new boolean of this <code>Force</code>
	 * @pre 	the new <code>forceInIt</code> should be a boolean value
	 */
 	public void setForce(boolean force){
 		this.forceInIt = force;
 	}

 	public void incForceLevel(int incLevel){
 		this.forceLevel += incLevel;
 	}
 	/**
	 * Returns the power level of <code>Force</code> power.
	 * 
	 * @return The power level of the Force power.
	 */
 	public int getForceLevel() {
 		return this.forceLevel;
 	}

 	/**
	 * Sets the <code>forceLevel</code> of this <code>Force</code> to the new <code>forceLevel</code>
	 * 
	 * @param 	VALUE OF FORCE LEVEL <code>Force</code>
	 * @pre 	the new <code>forceLevel</code> should be an integer
	 */
 	public void setForceLevel(int newForceLevel) {
 		this.forceLevel = newForceLevel;
 	}
 }