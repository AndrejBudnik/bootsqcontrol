package sample;

import org.hibernate.Query;
import org.hibernate.Session;
import sample.Model.ManagerEntity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by kotsy on 20.12.2016.
 */
public class Login {
    private List<ManagerEntity> parseManagers(){
        Session session = Config.getSession();
        List<ManagerEntity> managerEntities = null;
        try {
            Query query = session.createQuery("from ManagerEntity ");
            managerEntities = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return managerEntities;
    }

    /**
     * Checks the entered password by hashing it
     * and comparing login-passhash combination with the ones in the manager list.
     * @param login is the entered login.
     * @param password is the entered password.
     * @return boolean value as a check result.
     */
    public boolean checkPassword(String login, String password){
        List<ManagerEntity> managerEntities;
        managerEntities = parseManagers();
        String hashPass = hashPassword(password);

        for (ManagerEntity m: managerEntities) {
            if(m.getPassHash().equals(hashPass) &&
                    (m.getLogin().equals(login))){
                return true;
            }
        }
        return false;
    }


    /**
     * Hashes the password using MD5 encryption method.
     * @param string is the initial password string.
     * @return the same password in MD5 hash.
     */
    private String hashPassword(String string){
        byte [] stb = new byte[0];
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(Constants.MD5HASH);
            messageDigest.reset();
            messageDigest.update(string.getBytes());
            stb = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, stb);
        String md5st = bigInt.toString(16);
        while( md5st.length() < 32 ){
            md5st = Constants.ZERO + md5st;
        }
        return md5st;
    }
}
