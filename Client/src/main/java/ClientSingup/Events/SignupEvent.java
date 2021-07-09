package ClientSingup.Events;

public class SignupEvent {

    String firstName;
    String lastName;
    String userName;
    String password1;
    String password2;
    String email;
    String birthDay;
    String birthMonth;
    String birthYear;
    String phone;
    String bio;

    public SignupEvent(String firstName,
                       String lastName,
                       String userName,
                       String password1,
                       String password2,
                       String email,
                       String birthDay,
                       String birthMonth,
                       String birthYear,
                       String phone,
                       String bio ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password1 = password1;
        this.password2 = password2;
        this.email = email;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.phone = phone;
        this.bio = bio;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBio() {
        return bio;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public String getBirthYear() {
        return birthYear;
    }
}
