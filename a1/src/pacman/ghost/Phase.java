package pacman.ghost;

//Phase Defines the different phases a ghost can be in. The phases are defined as
//"CHASE", "SCATTER" and "FRIGHTENED".
//Chase has a duration of 20.
//Scatter has a duration of 10.
//Frightened has a duration of 30.
public enum Phase {
    CHASE,      //Phase where the ghosts chase the hunter.
    FRIGHTENED, //Phase where the ghosts are frightened and confused.
    SCATTER;    //Phase where the ghosts run home.

    private int phaseDuration = 0;

    /**
     * Gets the duration of the phase.
     * @return duration of the phase.
     */

    public int getDuration() {
        switch (this) {
            case CHASE:
                phaseDuration = 20;
                break;
            case SCATTER:
                phaseDuration = 10;
                break;
            case FRIGHTENED:
                phaseDuration = 30;
        }
        return phaseDuration;
    }
}
