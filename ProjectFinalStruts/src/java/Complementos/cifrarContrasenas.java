package Complementos;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 *
 * @author RodrigoSalazar
 */
public class cifrarContrasenas {
    public cifrarContrasenas() {
    }
    
    public String encriptar(String s) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
    }
    
    public String desencriptar(String s) throws UnsupportedEncodingException{
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        return new String(decode, "utf-8");
    }
}
