
package ppolozhe.modelEntity;

import java.util.List;

public class User {
    private int id;
    private String firstName;
    private String surname;
    private String phoneNumber;
    private List<Booking> bookingList;
    private Accounts accounts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setBookingList(List<Booking>  bookingList) {
        this.bookingList =  bookingList;
    }

    public static class Builder {
        private String phoneNumber;
        private String firstName;
        private String surname;
        private Accounts accounts;

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setAccounts(Accounts accounts) {
            this.accounts = accounts;
            return this;
        }

        public User build() {
            User user = new User();
            user.setAccounts(accounts);
            user.setFirstName(firstName);
            user.setSurname(surname);
            user.setPhoneNumber(phoneNumber);
            return user;
        }
    }
}
