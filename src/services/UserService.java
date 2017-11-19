package services;

import db.FirebaseHabitsDatabase;
import db.HabitsDatabase;
import users.BaseUser;
import users.FullUser;
import users.UserPass;

import javax.json.Json;
import javax.json.JsonObject;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;


@Path("/users")
public class UserService {

    private HabitsDatabase db=new FirebaseHabitsDatabase();

    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String signin(UserPass user,@Context final HttpServletResponse res){
        String response=this.db.authenticate(user);

        JsonObject object=Json.createReader(new StringReader(response)).readObject();

        if(object.containsKey("error")){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            try {
                res.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers(){
        return db.getAllUsers().toString();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addNewUser(UserPass newUser, @Context final HttpServletResponse response){
        /*
        int newId=2;

        if(newUser.getEmail().equals("nanes@example.com")){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }

        response.setStatus(HttpServletResponse.SC_CREATED);

        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(newUser.toString());

        return Json.createObjectBuilder().add("id",newId).build().toString();
        */


        FullUser user=newUser.getFullUser();


        String json=this.db.addUser(user);

        if(json==null) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        else{
            response.setStatus(HttpServletResponse.SC_CREATED);
        }

        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(json==null){
            return Json.createObjectBuilder().add("status","already exists").build().toString();
        }
        //return json;

        String auth="";

        if(json!=null) {
            auth=this.db.signup(newUser);
        }


        String id=Json.createReader(new StringReader(json)).readObject().getString("id");


        JsonObject authObj=Json.createReader(new StringReader(auth)).readObject();

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
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAllUsers(){
        //borrar

        this.db.deleteAllUsers();
        return Json.createObjectBuilder().add("status","Deleted all users").build().toString();
    }



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

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUserByID(@PathParam("id") String id, @Context HttpServletResponse response){
        /*String result="";
        if(id==1){
            result+=Json.createObjectBuilder().add("status","deleted").build().toString();
        }
        else if(id==3){

        }
        return result;
        */

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
    }

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
