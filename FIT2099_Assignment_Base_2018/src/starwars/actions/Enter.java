package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.Player;

public class Enter extends SWAffordance implements SWActionInterface {

	private SWEntityInterface enterTarget;
	
	public Enter(SWEntityInterface theTarget, SWEntityInterface enterTarget, MessageRenderer m) {
		super(theTarget, m);
		this.enterTarget = enterTarget;
		priority = 1;
	}
	
	@Override
	public int getDuration() {
		return 1;
	}

	@Override
	public boolean canDo(SWActor a) {
		return a.getForceActor().hasForce();
	}

	@Override
	public void act(SWActor a) {
		EntityManager<SWEntityInterface, SWLocation> em = SWWorld.getEntitymanager();
		SWLocation loc = (SWLocation)em.whereIs(enterTarget);
		em.setLocation(a, loc);
		if (a instanceof Player){
			a.changeMap(1);
		}
		a.resetMoveCommands(loc);
	}

	@Override
	public String getDescription() {
		return "Enter " + target.getShortDescription();
	}

}