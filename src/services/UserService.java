package services;

import db.FirebaseHabitsDatabase;
import db.HabitsDatabase;
import users.BaseUser;
import users.FullUser;
import users.UserPass;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;


@Path("/users")
public class UserService {

    private HabitsDatabase db=new FirebaseHabitsDatabase();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers(){
        List<FullUser> allUsers = db.getAllUsers();

        if(allUsers.isEmpty()){
            return Json.createArrayBuilder().build().toString();
        }

        return allUsers.toString();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addNewUser(UserPass newUser, @Context final HttpServletResponse response){

        /*
        FullUser user=newUser.getFullUser();
        String json=this.db.addUser(user);

        if(json==null) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        else{
            response.setStatus(HttpServletResponse.SC_CREATED);
        }

        JsonObject object=Json.createReader(new StringReader(json)).readObject();

        boolean invalidEmail=false;

        if(object.containsKey("error")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            invalidEmail=true;
        }

        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(json==null){
            return Json.createObjectBuilder().add("status","already exists").build().toString();
        }

        if(invalidEmail){
            return Json.createObjectBuilder().add("status","Invalid email").build().toString();
        }

        //return json;

        String auth="";

        if(json!=null) {
            auth=this.db.signup(newUser);
        }

        JsonObject authObj=Json.createReader(new StringReader(auth)).readObject();

        if(authObj.containsKey("error")){
            return auth;
        }


        String id=Json.createReader(new StringReader(json)).readObject().getString("id");

        String jsonResponse=Json.createObjectBuilder()
                .add("id",id)
                .add("kind",authObj.getString("kind"))
                .add("idToken",authObj.getString("idToken"))
                .add("email",authObj.getString("email"))
                .add("refreshToken",authObj.getString("refreshToken"))
                .add("expiresIn",authObj.getString("expiresIn"))
                .add("localId",authObj.getString("localId"))
                .build().toString();

        return jsonResponse;
        */

        /*

        if(this.db.getUserByID(newUser.getId())!=null){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            try {
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return Json.createObjectBuilder().add("status","Already exists").build().toString();
        }
        */

        String sign=this.db.signup(newUser);

        JsonObject signObj=Json.createReader(new StringReader(sign)).readObject();

        if(signObj.containsKey("error")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return Json.createObjectBuilder().add("status","Invalid email").build().toString();
        }


        String id=signObj.getString("localId");

        String email=newUser.getEmail();

        String DOB=newUser.getDOB();

        String name=newUser.getName();

        FullUser user=new FullUser(id,email,name,DOB);

        String add=this.db.addUser(user);


        String jsonResponse=Json.createObjectBuilder()
                .add("id",id)
                .add("idToken",signObj.getString("idToken"))
                .add("email",signObj.getString("email"))
                .add("refreshToken",signObj.getString("refreshToken"))
                .add("expiresIn",signObj.getString("expiresIn"))
                .build().toString();


        return jsonResponse;
    }

    /*

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAllUsers(){
        //borrar

        this.db.deleteAllUsers();
        return Json.createObjectBuilder().add("status","Deleted all users").build().toString();
    }
    */


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserByID(@PathParam("id") String id,@Context final HttpServletResponse response){

        FullUser user=this.db.getUserByID(id);

        String result="";

        if(user==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            result= Json.createObjectBuilder().add("status","Not found").build().toString();
        }
        else{
            result=user.toString();
        }

     return result;
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUserByID(@PathParam("id") String id, BaseUser newUserInfo, @Context HttpServletResponse response){
      /*  String result="";

        if(id==1){
            result+=Json.createObjectBuilder().add("status","updated").build().toString();
        }
        else if(id==3){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            result+= Json.createObjectBuilder().add("status","Not found").build().toString();
        }

        return result;*/

      String result="";
      boolean updated=this.db.updateUserByID(id,newUserInfo);

      if(updated){
          result=Json.createObjectBuilder().add("status","updated").build().toString();
      }
      else{
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          try {
              response.flushBuffer();
          } catch (IOException e) {
              e.printStackTrace();
          }
          result+= Json.createObjectBuilder().add("status","Not found").build().toString();
      }

      return result;
    }

    /*
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUserByID(@PathParam("id") String id, @Context HttpServletResponse response){


        boolean deleted=this.db.deleteUserByID(id);

        String result="";

        if(deleted){
            result=Json.createObjectBuilder().add("status","deleted").build().toString();
        }
        else{

            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            result+= Json.createObjectBuilder().add("status","Not found").build().toString();
        }

        return result;
    }*/

    /*

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserByEmail(@PathParam("email")String email,@Context final HttpServletResponse response){
        return "{}";
    }


    @PUT
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUserByEmail(@PathParam("email")String email, BaseUser newUserInfo, @Context final HttpServletResponse response){
        return "{}";
    }

    @DELETE
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUserByEmail(@PathParam("email")String email,@Context final HttpServletResponse response){
        return "{}";
    }
    */
}
