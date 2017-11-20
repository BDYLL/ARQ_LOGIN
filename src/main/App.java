package main;

import services.RefreshService;
import services.SignInService;
import services.UserService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.*;

@ApplicationPath("/")
public class App extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(UserService.class);
        h.add(SignInService.class);
        h.add(RefreshService.class);
        return h;
    }
}
