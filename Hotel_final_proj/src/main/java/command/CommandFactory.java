package command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ppolozhe on 5/27/19.
 */
public class CommandFactory {
    Map<String, Command> commands;

    CommandFactory() {
        commands = new HashMap<>();

        commands.put("/", MainPage.getInstance());
        commands.put("/login/signin", SignIn.getInstance());
        commands.put("/login", LoginPage.getInstance());
       // commands.put("profile/rooms", AllRooms.getInstance());
       // commands.put("signUp", SignUp.getInstance());

    }

    private static class Holder {
        static final CommandFactory INSTANCE = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return Holder.INSTANCE;
    }


    public Command getCommand(HttpServletRequest request) {
        String path = request.getServletPath();

        Command command = commands.get(path);

        if(command == null){
            command = MainPage.getInstance();
        }
        return command;
    }

}
