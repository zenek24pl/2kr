package pl.noskilljustfun.dwakrokistad.model;



import com.google.gson.annotations.SerializedName;

/**
 * Created by Bartosz on 29.04.2016.
 */
public class Clue extends Place {

    @SerializedName("status")
    private int status;
    @SerializedName("app_id")
    private int idStartPoint;
    @SerializedName("radius")
    private double radius;

    @SerializedName("target")
    private String target;
    @SerializedName("story")
    private String story;
    @SerializedName("survey_clue")
    private String surveyClue;
    @SerializedName("game_id")
    private int idGame;
    @SerializedName("type")
    private int type;

    public String getSurveyClue() {
        return surveyClue;
    }

    public void setSurveyClue(String surveyClue) {
        this.surveyClue = surveyClue;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public Clue() {
        super();

    }
    public Clue(int id)
    {
     super();
        this.id=id;
    }

    public Clue(int id
            , String name, String description, float positionX, float positionY, int status
            , int idStartPoint, double radius, String target, String story,String surveyClue
            , int idGame,int type) {
        super(id, name, description, positionX, positionY);

        this.status=status;
    //    this.idStartPoint = idStartPoint;
        this.radius = radius;
        this.target = target;
        this.story = story;
        this.surveyClue = surveyClue;
        this.idGame = idGame;
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdStartPoint() {
        return idStartPoint;
    }

    public void setIdStartPoint(int idStartPoint) {
        this.idStartPoint = idStartPoint;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
