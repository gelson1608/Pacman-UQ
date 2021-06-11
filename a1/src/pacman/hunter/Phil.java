package pacman.hunter;

import pacman.ghost.Ghost;
import pacman.util.Direction;
import pacman.util.Position;

//A Phil hunter that has no special ability.
public class Phil extends Hunter {

    /**
     * Creates a Phil Hunter.
     */

    public Phil() {
        super();
        //no special ability
    }

    /**
     * Creates a Phil hunter by copying the internal state of another hunter.
     * @param original hunter to copy from.
     */

    public Phil(Hunter original){
        super(original);
    }

    /**
     * Phil does not have a special.
     * @return false.
     */

    @Override
    public boolean isSpecialActive(){
        return false;
    }
}
