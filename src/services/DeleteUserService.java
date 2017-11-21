package services;

import db.FirebaseHabitsDatabase;
import db.HabitsDatabase;
import users.UserToken;
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

@Path("/delete")
public class DeleteUserService {

    private HabitsDatabase db=new FirebaseHabitsDatabase();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(UserToken token, @Context final HttpServletResponse response){

        String deleteResp=this.db.deleteUser(token);

        JsonObject respObj= Json.createReader(new StringReader(deleteResp)).readObject();

        if(respObj.containsKey("error")){

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Json.createObjectBuilder().add("status","Invalid token").build().toString();
        }

        this.db.deleteUserByID(token.getId());

        return Json.createObjectBuilder().add("status","User deleted").build().toString();
    }
}
