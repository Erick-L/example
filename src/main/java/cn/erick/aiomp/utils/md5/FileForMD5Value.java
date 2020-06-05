package cn.erick.aiomp.utils.md5;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileForMD5Value {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        String md5Hex = getFileForMD5Value("");
        digestMD5(new File("C:\\Users\\Erick\\Desktop\\fail\\banner.png"));

    }
    public static String  getFileForMD5Value(String filepath) throws IOException {

        String md5Hex = DigestUtils.md5Hex(new FileInputStream(new File(filepath)));

        return  md5Hex;

    }
    private static String digestMD5(File file)
            throws IOException, NoSuchAlgorithmException {
        String res = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[4096];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                md.update(buffer, 0, length);
            }

            byte[] result = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                String s = Integer.toHexString(0xFF & result[i]);
                if (s.length() < 2) {
                    sb.append('0');
                }
                sb.append(s);
            }
            res = sb.toString();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException localIOException1) {
            }
        }
        return res;
    }

}

