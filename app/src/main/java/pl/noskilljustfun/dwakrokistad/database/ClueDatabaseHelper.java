package pl.noskilljustfun.dwakrokistad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import pl.noskilljustfun.dwakrokistad.model.Clue;
import pl.noskilljustfun.dwakrokistad.model.Relationship;

/**
 * Created by Bartosz on 11.05.2016.
 */
public class ClueDatabaseHelper extends SQLiteOpenHelper {


    private static ClueDatabaseHelper sInstance=null;

    /**
     * Table Clues is storing clues data
     * Clues is related with Relationships
     * Relationships is storing ID of next clues
     * Type of Table relationship between Clues and Relationships is one-to-many
     */


    //******* Clues
    public final static String CLUE_TABLE_NAME = "CLUES";

    public final static String COLUMN_ID ="_id";    //INTEGER
    public final static String COLUMN_NAME="NAME";  //STRING
    public final static String COLUMN_DESCRIPTION="DESCRIPTION"; //STRING
    public final static String COLUMN_POSITION_X="POSITION_X"; //FLOAT
    public final static String COLUMN_POSITION_Y="POSITION_Y"; //FLOAT
    public final static String COLUMN_STATUS="STATUS"; //INTEGER (LOGIC VALUE 1 OR 0)
    public final static String COLUMN_ID_START_POINT="ID_START_POINT"; // INTEGER
    public final static String COLUMN_RADIUS ="RADIUS"; // FLOAT
    public final static String COLUMN_TARGET="TARGET"; // STRING
    public final static String COLUMN_STORY="STORY"; // STRING
    public final static String COLUMN_SURVEY_CLUE="SURVEY_CLUE"; //STRING
    public final static String COLUMN_ID_GAME="ID_GAME";  //INTEGER
    public final static String COLUMN_TYPE="TYPE";  //INTEGER

    private String[] columns={COLUMN_ID
            ,COLUMN_NAME
            ,COLUMN_DESCRIPTION
            ,COLUMN_POSITION_X
            ,COLUMN_POSITION_Y
            ,COLUMN_STATUS
            ,COLUMN_ID_START_POINT
            ,COLUMN_RADIUS
            ,COLUMN_TARGET
            ,COLUMN_STORY
            ,COLUMN_SURVEY_CLUE
            ,COLUMN_ID_GAME
            ,COLUMN_TYPE};


    public final static String DATABASE_CREATE ="create table "+CLUE_TABLE_NAME+" ("+
            COLUMN_ID+" integer primary key autoincrement,"+
            COLUMN_NAME+" text,"+
            COLUMN_DESCRIPTION+" text,"+
            COLUMN_POSITION_X+" real,"+
            COLUMN_POSITION_Y+" real,"+
            COLUMN_STATUS+" integer,"+
            COLUMN_ID_START_POINT+" integer,"+
            COLUMN_RADIUS+" real,"+
            COLUMN_TARGET+" text,"+
            COLUMN_STORY+" text,"+
            COLUMN_SURVEY_CLUE+" text,"+
            COLUMN_ID_GAME+" integer,"+
            COLUMN_TYPE+ " integer);";


    //***** Relationships
    public final  static String RELATIONSHIPS_TABLE_NAME = "RELATIONSHIPS";

    public final static String COLUMN_RELATIONSHIP_ID= "_id";
    public final static String COLUMN_CURRENT_CLUE="CURRENT_CLUE";
    public final static String COLUMN_NEXT_CLUE="NEXT_CLUE";

    private String[] relationships_columns= {
            COLUMN_RELATIONSHIP_ID
            ,COLUMN_CURRENT_CLUE
            ,COLUMN_NEXT_CLUE
    };

    public final static String RELATIONSHIPS_CREATE ="CREATE TABLE "+RELATIONSHIPS_TABLE_NAME
            +" ("+
            COLUMN_RELATIONSHIP_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_CURRENT_CLUE+" INTEGER, "+
            COLUMN_NEXT_CLUE+" INTEGER); ";

    public final static String DATABASE_NAME="dwakrokistad.db";
    public final static int DATABASE_VERSION=1;





    private ClueDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static ClueDatabaseHelper getInstance(Context context)
    {
        if(sInstance==null)
        return sInstance=new ClueDatabaseHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
        else
            return sInstance;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);
        db.execSQL(RELATIONSHIPS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(ClueDatabaseHelper.class.getName()
                , "Baza danych zostala uaktualniona z wersji " + oldVersion
                + " do wersji" + newVersion + ",wszystkie stare dane zostaly usuniete.");

        db.execSQL("DROP TABLE " + DATABASE_NAME);
        db.execSQL("DROP TABLE " + RELATIONSHIPS_TABLE_NAME);
        onCreate(db);
    }

    public void updateSolvedClue( int id) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_STATUS, 1);
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.update(CLUE_TABLE_NAME, cv, "_id=" + id, null);
    }

    public Clue createClue(String name,String desc,float posx,float posy,int status
            , int idStartPoint, double radius, String target, String story,String surveyClue
            , int idGame,int type,List<Integer> relationships) {

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(ClueDatabaseHelper.COLUMN_NAME,name);
        values.put(ClueDatabaseHelper.COLUMN_DESCRIPTION,desc);
        values.put(ClueDatabaseHelper.COLUMN_POSITION_X,posx);
        values.put(ClueDatabaseHelper.COLUMN_POSITION_Y,posy);
        values.put(ClueDatabaseHelper.COLUMN_STATUS,status);
        values.put(ClueDatabaseHelper.COLUMN_ID_START_POINT,idStartPoint);
        values.put(ClueDatabaseHelper.COLUMN_RADIUS,radius);
        values.put(ClueDatabaseHelper.COLUMN_TARGET,target);
        values.put(ClueDatabaseHelper.COLUMN_STORY,story);
        values.put(ClueDatabaseHelper.COLUMN_SURVEY_CLUE,surveyClue);
        values.put(ClueDatabaseHelper.COLUMN_ID_GAME,idGame);
        values.put(ClueDatabaseHelper.COLUMN_TYPE, type);

        long insertID=sqLiteDatabase.insert(ClueDatabaseHelper.CLUE_TABLE_NAME
                ,null
                ,values);
        Cursor cursor =sqLiteDatabase.query(ClueDatabaseHelper.CLUE_TABLE_NAME
                ,columns
                , ClueDatabaseHelper.COLUMN_ID+"="+insertID
                ,null
                ,null
                ,null
                ,null);
        cursor.moveToFirst();
        Clue clue= cursorToClue(cursor);
        cursor.close();

        for(Integer r:relationships)
        createRelationship((int)insertID,r);


        return clue;
    }

    private Clue cursorToClue(Cursor cursor)
    {
        Clue clue = new Clue();
        clue.setId((int) cursor.getLong(0));
        clue.setName(cursor.getString(1));
        clue.setDescription(cursor.getString(2));
        clue.setPositionX(cursor.getFloat(3));
        clue.setPositionY(cursor.getFloat(4));
        clue.setStatus(cursor.getInt(5));
        clue.setIdStartPoint(cursor.getInt(6));
        clue.setRadius(cursor.getFloat(7));
        clue.setTarget(cursor.getString(8));
        clue.setStory(cursor.getString(9));
        clue.setSurveyClue(cursor.getString(10));
        clue.setIdGame(cursor.getInt(11));
        clue.setType(cursor.getInt(12));
        return clue;
    }


    public void deleteClue(int id) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(CLUE_TABLE_NAME, COLUMN_ID + " = " + id, null);
    }

    public List<Clue> getAllClues() {


        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        List<Clue> allClues= new ArrayList<Clue>();
        Cursor cursor = sqLiteDatabase.query(CLUE_TABLE_NAME
                                ,columns,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Clue clue= cursorToClue(cursor);
            allClues.add(clue);
            cursor.moveToNext();
        }
        cursor.close();
        return  allClues;
    }

    private Relationship cursorToRelationship(Cursor cursor)
    {
        Relationship relationship = new Relationship();
        relationship.set_id(cursor.getInt(0));
        relationship.setCurrent_clue(cursor.getInt(1));
        relationship.setNext_clue(cursor.getInt(2));

        return relationship;
    }

    public Relationship createRelationship(int current_clue, int next_clue)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_CURRENT_CLUE,current_clue);
        values.put(COLUMN_NEXT_CLUE, next_clue);

        long insertID=sqLiteDatabase.insert(ClueDatabaseHelper.RELATIONSHIPS_TABLE_NAME
                ,null
                ,values);

        Cursor cursor =sqLiteDatabase.query(ClueDatabaseHelper.RELATIONSHIPS_TABLE_NAME
                ,relationships_columns
                , ClueDatabaseHelper.COLUMN_RELATIONSHIP_ID+"="+insertID
                ,null
                ,null
                ,null
                ,null);

        cursor.moveToFirst();
        Relationship relationship= cursorToRelationship(cursor);
        cursor.close();
        return relationship;
    }

    public List<Relationship> getAllRelationships(int curr_id)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
       List<Relationship> relationships = new ArrayList<>();
        String whereClause = relationships_columns[1]+"=?";
        String whereArgs[] = new String[]{String.valueOf(curr_id)};
        Cursor cursor = sqLiteDatabase.query(RELATIONSHIPS_TABLE_NAME
                ,relationships_columns
                ,whereClause
                ,whereArgs
                ,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Relationship relationship= cursorToRelationship(cursor);
            relationships.add(relationship);
            cursor.moveToNext();
        }
        cursor.close();
        return  relationships;
    }

}

