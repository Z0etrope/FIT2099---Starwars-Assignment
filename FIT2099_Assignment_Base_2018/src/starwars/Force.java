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
 	public void setForceLevel(int newForceLevel) {
 		this.forceLevel = newForceLevel;
 	}
 }