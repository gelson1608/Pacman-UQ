package pacman.ghost;

//Inky is a ghost that likes to tail close behind the hunter. When not chasing
// the hunter down, Inky likes to hang out in the bottom right corner of the
// board in a blue glow
public class Inky extends Ghost {
    public Inky() {
        super();
    }

    /**
     * Get Inky's colour.
     * @return "#7aa6da".
     */

    public String getColour(){
        return "#7aa6da";
    }

    /**
     * Get Inky's type/name.
     * @return INKY.
     */

    public GhostType getType(){
        return GhostType.INKY;
    }
}
