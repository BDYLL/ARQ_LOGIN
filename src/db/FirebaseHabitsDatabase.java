package db;

import com.google.firebase.database.*;
import users.BaseUser;
import users.FullUser;
import users.UserPass;

import javax.json.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FirebaseHabitsDatabase implements HabitsDatabase {


    private static final String PATH_TO_FIREBASE_KEY ="keys/arquilogin-186308-firebase-adminsdk-lsw90-3f4fe7f26f.json";
    private static final String FIREBASE_URL="https://arquilogin-186308.firebaseio.com";
    private static final String USERS_PATH="/users";
    private static final String JSON_FORMAT=".json";

    private static final String SIGN_UP_URL ="https://www.googleapis.com/identitytoolkit/v3/relyingparty/signupNewUser?key=";

    private static final String SIGN_IN_URL="https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=";

    private static final String API_KEY="AIzaSyDHxxcIInplDxI1LSDGbjcVM3H_eVhhoP0";


    private FirebaseDatabase db;

    public FirebaseHabitsDatabase(){}

    @Override
    public String addUser(FullUser newUser) {
        String id=newUser.getId();

        if(this.getUserByID(id)!=null){
            return null;
        }

        String json=newUser.toString();
        String response=this.putJSONtoFirebase(USERS_PATH+"/"+id,json);
        return response;
    }

    @Override
    public FullUser getUserByID(String id) {

        String response=this.getJSONStringFromFirebase(USERS_PATH+"/"+id);

        if(response.equals("null")){
            return null;
        }

        JsonObject object=Json.createReader(new StringReader(response)).readObject();

        String responseID=object.getString("id");
        String name=object.getString("name");
        String DOB=object.getString("DOB");
        String email=object.getString("email");


        return new FullUser(responseID,email,name,DOB);
    }

    @Override
    public List<FullUser> getAllUsers() {

        String lines=this.getJSONStringFromFirebase(USERS_PATH);

        JsonObject object=Json.createReader(new StringReader(lines)).readObject();

        Set<Map.Entry<String, JsonValue>> entries = object.entrySet();


        List<FullUser> users=new ArrayList<>();

        for(Map.Entry<String,JsonValue> entry : entries){
            String id=entry.getKey();
            JsonObject value=(JsonObject)entry.getValue();
            String email=value.getString("email");
            String DOB=value.getString("DOB");
            String name=value.getString("name");
            users.add(new FullUser(id,email,name,DOB));
        }

        return users;
    }

    @Override
    public boolean deleteUserByID(String id) {
        if(this.getUserByID(id)==null){
            return false;
        }

        this.deleteJSONfromFirebase(USERS_PATH+"/"+id);

        return true;
    }

    @Override
    public void deleteAllUsers() {
        this.deleteJSONfromFirebase(USERS_PATH);
    }

    @Override
    public boolean updateUserByID(String id, BaseUser user) {
        if(this.getUserByID(id)==null){
            return false;
        }

        this.patchJSONfromFirebase(USERS_PATH+"/"+id,user.toString());

        return true;
    }

    @Override
    public String signup(UserPass user) {
        String parsedJSON=Json.createObjectBuilder()
                .add("email",user.getEmail())
                .add("password",user.getPassword())
                .add("returnSecureToken",JsonValue.TRUE)
                .build().toString();

        URL url=null;
        String lines="";
        HttpURLConnection connection=null;
        try {
            url=new URL(SIGN_UP_URL +API_KEY);
            connection=(HttpURLConnection)url.openConnection();
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(parsedJSON);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            lines=this.getLinesFromInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;

    }

    @Override
    public String authenticate(UserPass user) {

        String parsedJSON=Json.createObjectBuilder()
                .add("email",user.getEmail())
                .add("password",user.getPassword())
                .add("returnSecureToken",JsonValue.TRUE)
                .build()
                .toString();

        URL url=null;
        String lines="";
        HttpURLConnection connection=null;
        try {
            url=new URL(SIGN_IN_URL+API_KEY);
            connection=(HttpURLConnection)url.openConnection();
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(parsedJSON);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            lines=this.getLinesFromInputStream(connection.getInputStream());
        } catch (IOException e) {
            lines=this.getLinesFromInputStream(connection.getErrorStream());
        }

        return lines;
    }


    private String getLinesFromInputStream(InputStream in){
        String lines="";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            lines = reader.lines().reduce("", (a, b) -> a + b);
        } catch (IOException e) {
            return lines;
        }

        return lines;
    }

    private String getJSONStringFromFirebase(String path){

        URL url=null;
        String lines="";
        URLConnection connection=null;

        try {
            url=new URL(FIREBASE_URL+path+JSON_FORMAT);
            connection=url.openConnection();
            connection.setUseCaches(false);
        } catch (IOException e) {
            return lines;
        }

        /*
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            lines = reader.lines().reduce("", (a, b) -> a + b);
        } catch (IOException e) {
            return lines;
        }*/


        try {
            lines=this.getLinesFromInputStream(connection.getInputStream());
        } catch (IOException e) {
            return lines;
        }

        return lines;
    }

    private String postJSONtoFirebase(String path,String parsedJSON){
        URL url=null;
        String lines="";
        HttpURLConnection connection=null;
        try {
            url=new URL(FIREBASE_URL+path+JSON_FORMAT);
            connection=(HttpURLConnection)url.openConnection();
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(parsedJSON);
            out.close();
        } catch (IOException e) {
            return lines;
        }

        try {
            lines=this.getLinesFromInputStream(connection.getInputStream());
        } catch (IOException e) {
            return lines;
        }

        return lines;

    }

    private String putJSONtoFirebase(String path,String parsedJSON){
        URL url=null;
        String lines="";
        HttpURLConnection connection=null;
        OutputStreamWriter out=null;

        try {
            url=new URL(FIREBASE_URL+path+JSON_FORMAT);
            connection=(HttpURLConnection)url.openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            out = new OutputStreamWriter(connection.getOutputStream());
            out.write(parsedJSON);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            lines=this.getLinesFromInputStream(connection.getInputStream());
        } catch (IOException e) {
            return lines;
        }

        return lines;
    }

    public String deleteJSONfromFirebase(String path){
        URL url=null;
        String lines="";
        HttpURLConnection connection=null;


        try {
            url=new URL(FIREBASE_URL+path+JSON_FORMAT);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            lines=this.getLinesFromInputStream(connection.getInputStream());
        } catch (IOException e) {
            return lines;
        }

        return lines;
    }

    public String patchJSONfromFirebase(String path,String parsedJSON){
        URL url=null;
        String lines="";
        HttpURLConnection connection=null;
        OutputStreamWriter out=null;

        try {
            url=new URL(FIREBASE_URL+path+JSON_FORMAT);
            connection=(HttpURLConnection)url.openConnection();
            connection.setUseCaches(false);
            connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            out = new OutputStreamWriter(connection.getOutputStream());
            out.write(parsedJSON);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            lines=this.getLinesFromInputStream(connection.getInputStream());
        } catch (IOException e) {
            return lines;
        }

        return lines;
    }
}
