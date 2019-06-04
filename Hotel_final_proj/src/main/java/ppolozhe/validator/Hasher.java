package ppolozhe.validator;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * Created by ppolozhe on 5/27/19.
 */
public class Hasher {
    public static String hashCode(String password) {
        return DigestUtils.md5Hex(password);
    }
}
