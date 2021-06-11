package pacman.ghost;

//Pinky is a cunning ghost that tries to ambush the hunter. When not chasing the
//hunter down, Pinky likes to hang out in the top left corner of the board in a
//pink glow.
public class Pinky extends Ghost {
    public Pinky() {
        super();
    }

    /**
     * Get Pinky colour.
     * @return "#c397d8".
     */

    public String getColour(){
        return "#c397d8";
    }

    /**
     * Get Pinkys type/name.
     * @return PINKY.
     */

    public GhostType getType(){
        return GhostType.PINKY;
    }

}
