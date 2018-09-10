package com.cynosurecreations.feras;

/**
 * Created by Shiva on 23/07/16.
 */
public class Config {
    //URL to our login.php file
    public static final String LOGIN_URL = "http://ferasindia.16mb.com/login.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "id";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String TYPE_SHARED_PREF = "type";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    //Address of our scripts of the CRUD
    public static final String URL_ADD_PLAYER="http://ferasindia.16mb.com/addPlayer.php";
    public static final String URL_ADD_TEAM="http://ferasindia.16mb.com/addTeam.php";
    public static final String URL_GET_TEAM = "http://ferasindia.16mb.com/getTeam.php?id=";
    public static final String URL_GET_NEARBY_TEAMS="http://ferasindia.16mb.com/getNearbyTeams.php?latitude=";

    public static final String URL_GET_STD_OUTPASSES="http://ferasindia.16mb.com/getStdOutpasses.php?regno=";
    public static final String URL_GET_NEARBY_PLAYERS="http://ferasindia.16mb.com/getNearbyPlayers.php?latitude=";
    public static final String URL_GET_CITIES="http://ferasindia.16mb.com/getCities.php?state=";
    public static final String URL_GET_RC_OUTPASSES="http://ferasindia.16mb.com/getRcOutpasses.php?rcid=";
    public static final String URL_GET_DENIED_OUTPASSES="http://ferasindia.16mb.com/getDeniedOutpasses.php?rcID=";
    public static final String URL_GET_APP_OUTPASSES="http://ferasindia.16mb.com/getAppOutpasses.php?rcid=";
    public static final String URL_GET_ALL = "http://ferasindia.16mb.com/getAllEmp.php";
    public static final String URL_GET_OUTPASS = "http://ferasindia.16mb.com/getOutpass.php?opid=";
    public static final String URL_APPROVE_OUTPASS = "http://ferasindia.16mb.com/approveOutpass.php?opid=";
    public static final String URL_DENY_OUTPASS = "http://ferasindia.16mb.com/denyOutpass.php?opid=";
    public static final String URL_GET_PLAYER = "http://ferasindia.16mb.com/getPlayer.php?id=";
    public static final String URL_UPDATE_PLAYER = "http://ferasindia.16mb.com/updatePlayer.php";
    public static final String URL_DELETE_OUTPASS = "http://ferasindia.16mb.com/deleteOutpass.php?opid=";

    public static final String URL_GET_STD = "http://ferasindia.16mb.com/getStud.php?id=";
    public static final String URL_GET_BLOCKS = "http://ferasindia.16mb.com/getBlocks.php";
    public static final String URL_GET_RCS = "http://ferasindia.16mb.com/getAllRcs.php?block=";
    public static final String URL_GET_ABS = "http://ferasindia.16mb.com/getAbs.php?blockno=";

    //Keys that will be used to send the request to php scripts

    public static final String KEY_OUT_SNAME = "name";
    public static final String KEY_OUT_SREGNO = "regno";
    public static final String KEY_OUT_BRANCH = "branch";
    public static final String KEY_OUT_COURSE = "course";
    public static final String KEY_OUT_BLOCKNO = "block";
    public static final String KEY_OUT_VISITDT = "visitdate";
    public static final String KEY_OUT_RETURNDT = "returndate";
    public static final String KEY_OUT_TIMEOUT = "timeout";
    public static final String KEY_OUT_RCID = "rcid";
    public static final String KEY_OUT_ROOM = "room";
    public static final String KEY_OUT_RETURNTIME = "returntime";
    public static final String KEY_OUT_ADDRESS = "placeofvisit";
    public static final String KEY_OUT_NOD = "noofdays";
    public static final String KEY_OUT_CONTACTNO = "contact";

    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAME = "name";
    public static final String KEY_EMP_STATE = "state";
    public static final String KEY_EMP_CONTACT = "contact";
    public static final String KEY_EMP_CITY = "city";
    public static final String KEY_EMP_PINCODE = "pincode";
    public static final String KEY_EMP_EMAIL = "email";
    public static final String KEY_EMP_PASSWD = "passwd";
    public static final String KEY_EMP_GENDER = "gender";
    public static final String KEY_EMP_SKYPEID = "skypeid";
    public static final String KEY_EMP_LATITUDE = "latitude";
    public static final String KEY_EMP_LONGITUDE = "longitude";
    public static final String KEY_EMP_SPORT = "sport";

    public static final String KEY_TEAM_LATITUDE = "latitude";
    public static final String KEY_TEAM_LONGITUDE = "longitude";
    public static final String KEY_TEAM_STATE = "state";
    public static final String KEY_TEAM_CITY = "city";
    public static final String KEY_TEAM_TEAMID = "teamid";
    public static final String KEY_TEAM_TEAMNAME = "teamname";
    public static final String KEY_TEAM_SPORT = "sport";
    public static final String KEY_TEAM_DESCRIPTION = "description";
    public static final String KEY_TEAM_LEADERID = "leaderid";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";

    public static final String TAG_ID = "id";
    public static final String TAG_SPORT = "sport";
    public static final String TAG_DIST = "distance";
    public static final String TAG_NAME = "name";
    public static final String TAG_FACID = "id";
    public static final String TAG_FACNAME = "name";
    public static final String TAG_STATE = "state";
    public static final String TAG_CONTACT = "contact";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_PASSWD = "passwd";
    public static final String TAG_GENDER = "gender";
    public static final String TAG_CITY = "city";
    public static final String TAG_AWAY = "away";
    public static final String TAG_IMGURL = "imgurl";
    public static final String TAG_SKYPEID = "skypeid";
    public static final String TAG_PINCODE = "pincode";
    public static final String TAG_BLOCKNAME = "blockname";
    public static final String TAG_BLOCKNO = "blockname";
    public static final String TAG_DESG = "blockname";
    public static final String TAG_CITYNAME = "cityname";
    public static final String TAG_LATITUDE = "latitude";
    public static final String TAG_LONGITUDE = "longitude";


    public static final String TAG_OUT_ID = "id";
    public static final String TAG_OUT_VISITDATE = "visitdate";
    public static final String TAG_OUT_APPROVAL = "approval";
    public static final String TAG_OUT_NAME = "name";
    public static final String TAG_OUT_BRANCH = "branch";
    public static final String TAG_OUT_BLOCK = "block";
    public static final String TAG_OUT_COURSE = "course";
    public static final String TAG_OUT_TIMEOUT = "timeout";
    public static final String TAG_OUT_RETURNTIME = "returntime";
    public static final String TAG_OUT_RETURNDATE = "returndate";
    public static final String TAG_OUT_NOD = "nod";
    public static final String TAG_OUT_PLACEOFVISIT = "placeofvisit";
    public static final String TAG_OUT_ROOM = "room";
    public static final String TAG_OUT_CONTACT = "contact";
    public static final String TAG_OUT_RCID = "rcid";
    public static final String TAG_OUT_REGNO = "regno";

    public static final String TAG_STD_NAME = "name";
    public static final String TAG_STD_EMAIL = "email";
    public static final String TAG_STD_SLNO = "slno";
    public static final String TAG_CLASSID = "classid";

}
