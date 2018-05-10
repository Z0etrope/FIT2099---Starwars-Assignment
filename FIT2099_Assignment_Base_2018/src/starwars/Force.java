package starwars;

/*
  @author Jason Setiawan
 */ 

 public class Force {
 	private boolean forceInIt;
 	private int forceLevel;

 	public Force(boolean force, int newForceLevel){
 		this.forceInIt = force;
 		this.forceLevel = newForceLevel;
 	}

 	public boolean hasForce(){
 		return this.forceInIt;
 	}

 	public void setForce(boolean force){
 		this.forceInIt = force;
 	}

 	public void incForceLevel(int incLevel){
 		this.forceLevel += incLevel;
 	}

 	public int getForceLevel() {
 		return this.forceLevel;
 	}
 }