package capaciti.org.za.database;

import capaciti.org.za.Login;
import capaciti.org.za.Register;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

public class DataBase implements SARS_INTERFACE{
    private String name;
    private String surname;
    private String email;
    private String password;
    private String type;
    private String IDorPASSPORT;

    public DataBase() {
    }
    public DataBase(String name, String surname, String email, String password, String type, String IDorPASSPORT) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.type = type;
        this.IDorPASSPORT = IDorPASSPORT;
        addToDataBase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIDorPASSPORT() {
        return IDorPASSPORT;
    }

    public void setIDorPASSPORT(String IDorPASSPORT) {
        this.IDorPASSPORT = IDorPASSPORT;
    }
    public void addToDataBase()
    {
        try {
            Connection con = DriverManager.getConnection(URL,userName,dbpassword);
            PreparedStatement ps = con.prepareCall(sql);
            
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, type);
            ps.setString(6, IDorPASSPORT);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(7, timestamp);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new Login();
            
        }
        catch(DerbySQLIntegrityConstraintViolationException c)
        {
            JOptionPane.showMessageDialog(null, "USER ALREADY EXIST");
            new Register();
        }
        catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "<<<FAILED TO ADD TO THE DATABASE<<<");
            new Register();
        }
    }
}
