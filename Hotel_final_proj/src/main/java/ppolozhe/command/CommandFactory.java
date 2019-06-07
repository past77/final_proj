package ppolozhe.command;

import org.apache.log4j.Logger;
import ppolozhe.service.UserService;

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
        commands.put("/registration/signup", SignUp.getInstance());
        commands.put("/", MainPage.getInstance());
       commands.put("/login/signin", SignIn.getInstance());
        commands.put("/login", LoginPage.getInstance());
        commands.put("profile/rooms", AllRooms.getInstance());
        commands.put("/profile/bookings/delete", DeleteBookings.getInstance());
        commands.put("/rooms", RoomsPage.getInstance());
        commands.put("/rooms/find", FreeRooms.getInstance());
        commands.put("/profile/bookings", UsersBookings.getInstance());
        commands.put("/profile", ProfilePage.getInstance());
        commands.put("/logout", Logout.getInstance());
        commands.put("/registration", RegisterPage.getInstance());
        commands.put("/allRooms", AllRooms.getInstance());
        commands.put("/profile/addBook/find", FreeRooms.getInstance());
        commands.put("/profile/addBook", AddBookPage.getInstance());
        commands.put("/profile/addBook/add", AddBook.getInstance());
        commands.put("/profile/addRoom/add", AddRooms.getInstance());
        commands.put("/profile/addRoom", AddRoomPage.getInstance());
        commands.put("/profile/rooms/delete", DeleteRoom.getInstance());
        commands.put("/profile/processedBookings", ProcessedBooking.getInstance());
        commands.put("/profile/processedBookings/update", UpdateBookingCommand.getInstance());
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
        LOGGER.info("ppolozhe.command: " + command);

        if(command == null){
            command = MainPage.getInstance();
        }
        return command;
    }

}
