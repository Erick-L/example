package cn.erick.aiomp.controller.email;

import cn.erick.aiomp.entity.email.EmailDB;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 电子邮件相关操作类
 */
@Controller
@RequestMapping("/email")
public class EmailController {
    //日志操作
    private static Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

    /**
     * 1、跳转到邮件编辑页面
     *
     * @return
     */
    @RequestMapping("/edit.do")
    public String editEmail(Model model) throws Exception {


        String banner = "C:\\Users\\Erick\\Desktop\\fail\\banner.png";
        String banner2 = "C:\\Users\\Erick\\Desktop\\fail\\banner@2x.png";
        String banner3 = "C:\\Users\\Erick\\Desktop\\fail\\banner1.png";
        String banner4 = "C:\\Users\\Erick\\Desktop\\fail\\banner2.png";
        String banner5 = "C:\\Users\\Erick\\Desktop\\fail\\banner3.png";


        String md5Hex = digestMD5(new File(banner));
        String md5Hex2 = digestMD5(new File(banner2));
        String md5Hex3 = digestMD5(new File(banner3));
        String md5Hex4 = digestMD5(new File(banner4));
        String md5Hex5 = digestMD5(new File(banner5));

        model.addAttribute("md5value", md5Hex);
        model.addAttribute("md5value2", md5Hex2);
        model.addAttribute("md5value3", md5Hex3);
        model.addAttribute("md5value4", md5Hex4);
        model.addAttribute("md5value5", md5Hex5);


        LOGGER.info("测试MD5值。。。" + md5Hex + "\n\t");
        LOGGER.info("测试MD5值2。。。" + md5Hex2 + "\n\t");
        LOGGER.info("测试MD5值3。。。" + md5Hex3 + "\n\t");
        LOGGER.info("测试MD5值4。。。" + md5Hex4 + "\n\t");
        LOGGER.info("测试MD5值5。。。" + md5Hex5 + "\n\t");

        return "email/edit";
    }

    /**
     * 2、发送邮件
     *
     * @param emailDB
     * @return
     */
    @RequestMapping("/send.do")
    public String sendEmail(EmailDB emailDB) {


        return "email/edit";

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
