package command;

import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ppolozhe on 5/27/19.
 */
public class CommandFactory {
    Map<String, Command> commands;
    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    CommandFactory() {
        commands = new HashMap<>();

        commands.put("/", MainPage.getInstance());
        commands.put("/login/signin", SignIn.getInstance());
        commands.put("/login", LoginPage.getInstance());
        commands.put("profile/rooms", AllRooms.getInstance());
        commands.put("/rooms", AllRoomPage.getInstance());
       // commands.put("signUp", SignUp.getInstance());
        commands.put("/profile", ProfilePage.getInstance());
        commands.put("/logout", Logout.getInstance());
        commands.put("/registration", RegisterPage.getInstance());

    }

    private static class Holder {
        static final CommandFactory INSTANCE = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return Holder.INSTANCE;
    }


    public Command getCommand(HttpServletRequest request) {
        String path = request.getServletPath();
        LOGGER.info("path: " + path);
        Command command = commands.get(path);

        if(command == null){
            command = MainPage.getInstance();
        }
        return command;
    }

}
