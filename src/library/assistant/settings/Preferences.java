package library.assistant.settings;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.assistant.alert.AlertMaker;
import org.apache.commons.codec.digest.DigestUtils;

public class Preferences {

    private static final String CONFIG_FILE = "config.txt";
    int nDayWithoutFine;
    float finePerDay;
    String username;
    String password;

    public Preferences() {
        nDayWithoutFine = 14;
        finePerDay = 2;
        username = "admin";
        //password = "admin";
        setPassword("admin");
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
        //String pass = DigestUtils.shaHex(password);
        //this.password = password;
        this.password = DigestUtils.shaHex(password);
    }

    public static void initConfig() {
        Writer writer = null;
        try {
            Preferences preference = new Preferences();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preference, writer);
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Preferences getPreferences() {
        Gson gson = new Gson();
        Preferences preferences = new Preferences();
        try {
            preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
        } catch (FileNotFoundException ex) {
            initConfig();
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preferences;
    }

    public static void writePreferenceToFile(Preferences preferences) {
        Writer writer = null;
        try {
            
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences, writer);
            
            AlertMaker.showSimpleAlert("Success", "Settings updated");
        } catch (IOException e) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, e);
            AlertMaker.showErrorMessage(e, "Failed", "Cant save configuration file");
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
