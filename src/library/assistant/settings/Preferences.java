package library.assistant.settings;

public class Preferences {

    int nDayWithoutFine;
    float finePerDay;
    String username;
    String password;
    
    public Preferences(){
        nDayWithoutFine = 14;
        finePerDay = 2;
        username = "admin";
        password = "admin";
    }

    public int getnDayWithoutFine() {
        return nDayWithoutFine;
    }

    public void setnDayWithoutFine(int nDayWithoutFine) {
        this.nDayWithoutFine = nDayWithoutFine;
    }

    public float getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(float finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
