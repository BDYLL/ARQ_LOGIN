package services;

import db.FirebaseHabitsDatabase;
import db.HabitsDatabase;
import users.UserRefreshToken;

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

@Path("/refresh")
public class RefreshService {

    private HabitsDatabase db=new FirebaseHabitsDatabase();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String refreshToken(UserRefreshToken token,@Context final HttpServletResponse response){

        String resp=this.db.refreshToken(token);

        JsonObject respObj= Json.createReader(new StringReader(resp)).readObject();

        if(respObj.containsKey("error")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Json.createObjectBuilder().add("status","Invalid refresh token").build().toString();
        }

        return resp;
    }

}
