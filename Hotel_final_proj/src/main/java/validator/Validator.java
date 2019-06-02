package validator;

import java.time.LocalDate;

/**
 * Created by ppolozhe on 5/27/19.
 */
public class Validator {

    private static final String LOGIN_REGEX = "^(?=.{1,50}$)[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //private static final String PASSWORD_REGEX = "^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).*$";
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

    public boolean validateDate(LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom.isAfter(LocalDate.now()) && dateFrom.isBefore(dateTo);
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

}
