package ppolozhe.validator;

import org.apache.log4j.Logger;
import ppolozhe.command.SignIn;
import ppolozhe.modelEntity.Booking;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ppolozhe on 5/27/19.
 */
public class Validator {
    private static final Logger LOGGER = Logger.getLogger(Validator.class);
    private static final String LOGIN_REGEX = "^(?=.{1,50}$)[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //private static final String NAME_REGEX = "^[\\p{L} .'-]+$";
    private static final String PHONE_REGEX = "^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{2}\\s?\\d{2}$";


    public static Validator getInstance() {
        return new Validator();
    }

    public boolean validateLogin(String login){
        return login.matches(LOGIN_REGEX);
    }
//    public boolean validatePassword(String password){
//        return password.matches(PASSWORD_REGEX);
//    }


//    public boolean validateName(String firstName) {
//        return firstName.matches(NAME_REGEX);
//    }
    public boolean validatePhone(String phone) {
        return phone.matches(PHONE_REGEX);
    }

    public boolean validateDate(LocalDate dateIn, LocalDate dateOut) {
        LOGGER.info("dateIn :"+ dateIn + " dateOut : " + dateOut);
        return dateIn.isAfter(LocalDate.now()) && dateIn.isBefore(dateOut);
    }

    public boolean validateCapacity(int persons) {
        return persons >= 1 && persons <= 4;
    }

    public boolean validatePrice(int price) {
        return price > 100;
    }

    public boolean validateNumber(int number) {
        return number > 2;
    }

    public boolean validateNumberAndDateUpdatedBookings(List<Booking> bookings) {
        boolean res = true;
        for (int i = 0; i < bookings.size() - 1; i++) {
            if(bookings.get(i).getRoom() != null) {
                for (int j = i + 1; j < bookings.size(); j++) {
                    if (bookings.get(j).getRoom() != null &&
                            bookings.get(i).getRoom().getId() == bookings.get(j).getRoom().getId() &&
                            !(bookings.get(i).getDateIn().isAfter(bookings.get(j).getDateOut()) ||
                                    bookings.get(i).getDateIn().isEqual(bookings.get(j).getDateOut()) ||
                                    bookings.get(i).getDateOut().isBefore(bookings.get(j).getDateIn())||
                                    bookings.get(i).getDateOut().isEqual(bookings.get(j).getDateIn()))) {
                        res = false;
                    }
                }
            }
        }
        return res;
    }

}
