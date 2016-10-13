package pl.noskilljustfun.dwakrokistad.model;

/**
 * Created by Bartosz on 04.07.2016.
 */
public class Relationship {

    private int _id;
    private int current_clue;
    private int next_clue;

    public Relationship(int _id, int current_clue, int next_clue) {
        this._id = _id;
        this.current_clue = current_clue;
        this.next_clue = next_clue;
    }

    public Relationship() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getCurrent_clue() {
        return current_clue;
    }

    public void setCurrent_clue(int current_clue) {
        this.current_clue = current_clue;
    }

    public int getNext_clue() {
        return next_clue;
    }

    public void setNext_clue(int next_clue) {
        this.next_clue = next_clue;
    }
}
