package cn.erick.aiomp.service.filemanage;

import cn.erick.aiomp.entity.file.UploadFileDB;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

/**
 * @Author Erick
 * @Date 2020/6/3 17:22
 * @Version 1.0
 */
public interface FileManageService {

    //上传文件
    void uploadFile(MultipartFile file) throws SQLException;
}
