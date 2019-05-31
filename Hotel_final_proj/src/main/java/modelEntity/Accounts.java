package modelEntity;

import enums.StatusUser;

import java.util.Objects;

public class Accounts {

    private int id;
    private String login;
    private String password;
    private StatusUser statusUser;

    public static class Builder {
        private int id;
        private String login;
        private String password;
        private StatusUser statusUser;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setStatusUser(StatusUser statusUser) {
            this.statusUser = statusUser;
            return this;
        }

        public Accounts build() {
         Accounts accounts = new Accounts();
         accounts.setId(id);
         accounts.setLogin(login);
         accounts.setPassword(password);
         accounts.setStatusUser(statusUser);
         return accounts;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StatusUser getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(StatusUser statusUser) {
        this.statusUser = statusUser;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", statusUser=" + statusUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accounts accounts = (Accounts) o;
        return id == accounts.id &&
                Objects.equals(login, accounts.login) &&
                Objects.equals(password, accounts.password) &&
                statusUser == accounts.statusUser;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (statusUser != null ? statusUser.hashCode() : 0);
        return result;
    }
}
