package pacman.ghost;

//Blinky is a ghost that behaves in a very aggressive manner towards a hunter.
// When not chasing the hunter down, Blinky likes to hang out in the top right
// corner of the board in a red glow.
public class Blinky extends Ghost {
    public Blinky(){
        super();
    }

    /**
     * Get Blinkys colour.
     * @return "#d54e53"
     */

    public String getColour(){
        return "#d54e53";
    }

    /**
     * Get Blinkys type/name.
     * @return BLINKY.
     */

    public GhostType getType(){
        return GhostType.BLINKY;
    }
}
