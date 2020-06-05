package cn.erick.aiomp.controller.filemanage;

import cn.erick.aiomp.common.exception.Result;
import cn.erick.aiomp.common.exception.UnifiedExceptionHandler;
import cn.erick.aiomp.service.filemanage.FileManageService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

/**
 * @Author Erick
 * @Date 2020/6/3 17:34
 * @Version 1.0
 */
@Controller
@RequestMapping("/file")
public class FileManageController {

    @Autowired
    private FileManageService fileManageService;

    @ResponseBody
    @RequestMapping("/upload.do")
    public JSONObject uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            fileManageService.uploadFile(file);
        } catch (SQLException e) {
            e.printStackTrace();
            UnifiedExceptionHandler exceptionHandler = new UnifiedExceptionHandler();
            JSONObject jsonObject = exceptionHandler.exceptionHandler(e);
            return Result.FAIL("文件上传失败", jsonObject);
        }
        return Result.SUCCESS("文件上传成功", null);
    }
}
