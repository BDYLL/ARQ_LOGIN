package services;

import db.FirebaseHabitsDatabase;
import db.HabitsDatabase;
import users.UserOnlyPass;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringReader;

@Path("/signin")
public class SignInService {

    private HabitsDatabase db=new FirebaseHabitsDatabase();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String signin(UserOnlyPass user, @Context final HttpServletResponse res){
        String response=this.db.authenticate(user);

        JsonObject object= Json.createReader(new StringReader(response)).readObject();

        if(object.containsKey("error")){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            try {
                res.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return Json.createObjectBuilder().add("status","Sign in failed").build().toString();
        }


        return Json.createObjectBuilder()
                .add("email",object.getString("email"))
                .add("idToken",object.getString("idToken"))
                .add("refreshToken",object.getString("refreshToken"))
                .add("expiresIn",object.getString("expiresIn"))
                .add("id",object.getString("localId"))
                .build()
                .toString();
    }
}
