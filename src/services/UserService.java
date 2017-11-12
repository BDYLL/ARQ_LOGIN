package services;

import users.BaseUser;
import users.FullUser;
import users.UserNoID;

import javax.json.Json;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;

@Path("/users")
public class UserService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers(){
        List<FullUser> fullUsers =new ArrayList<>();

        fullUsers.add(new FullUser(1,"nanes@example.com","nanes","01/01/1995"));
        fullUsers.add(new FullUser(2,"yisus@dios.com","yisus","25/12/0000"));

        return fullUsers.toString();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addNewUser(UserNoID newUser, @Context final HttpServletResponse response){

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
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAllUsers(){
        //borrar

        return Json.createObjectBuilder().add("status","Deleted all users").build().toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String getUser(@PathParam("id") int id,@Context final HttpServletResponse response){
        String result="";
        if(id==1){
            result+=new FullUser(1,"nanes@example.com","nanes","01/01/1995").toString();
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
        return result;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String updateUser(@PathParam("id") int id, BaseUser newUserInfo, @Context HttpServletResponse response){
        String result="";

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

        return result;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String deleteUser(@PathParam("id") int id, @Context HttpServletResponse response){
        String result="";
        if(id==1){
            result+=Json.createObjectBuilder().add("status","deleted").build().toString();
        }
        else if(id==3){

        }
        return result;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{email}")
    public String getUser(@PathParam("email")String email,@Context final HttpServletResponse response){
        return "{}";
    }


    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{email}")
    public String updateUser(@PathParam("email")String email, BaseUser newUserInfo, @Context final HttpServletResponse response){
        return "{}";
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{email}")
    public String deleteUser(@PathParam("email")String email,@Context final HttpServletResponse response){
        return "{}";
    }
}
