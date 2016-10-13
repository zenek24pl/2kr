package pl.noskilljustfun.dwakrokistad.interfaces;

/**
 * Created by Bartosz on 03.05.2016.
 */
public interface OnActivityClickListener {
    void navigateToGame(boolean solved,int p);
    void navigateToTrip();
    void navigateToRules();
    void navigateToClueDetail(int id);
    void navigateToSolveClue(int id);
    void navigateToMap(int id);

}
