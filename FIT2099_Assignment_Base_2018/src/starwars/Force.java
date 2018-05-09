package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
/*
  @author Jason Setiawan
 */ 

 public class Force{
 	boolean forceInIt;
 	int forceLevel;

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

 }