package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.Player;

public class Exit extends SWAffordance implements SWActionInterface {

	private SWEntityInterface exitTarget;
	
	public Exit(SWEntityInterface theTarget, SWEntityInterface exitTarget, MessageRenderer m) {
		super(theTarget, m);
		this.exitTarget = exitTarget;
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
		SWLocation loc = em.whereIs(exitTarget);
		em.setLocation(a, loc);
		if (a instanceof Player){
			a.changeMap(0);
		}
		a.resetMoveCommands(loc);
	}

	@Override
	public String getDescription() {
		return "exit " + target.getShortDescription();
	}

}
