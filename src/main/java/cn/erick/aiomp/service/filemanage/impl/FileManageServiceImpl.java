package cn.erick.aiomp.service.filemanage.impl;

import cn.erick.aiomp.common.exception.UnifiedExceptionHandler;
import cn.erick.aiomp.entity.file.UploadFileDB;
import cn.erick.aiomp.mapper.filemanage.FileManageMapper;
import cn.erick.aiomp.service.filemanage.FileManageService;
import cn.erick.aiomp.utils.file.FileUtils;
import cn.erick.aiomp.utils.file.ReadConfigUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Erick
 * @Date 2020/6/3 17:23
 * @Version 1.0
 */
@Service
public class FileManageServiceImpl implements FileManageService {

    private Logger logger = LoggerFactory.getLogger(FileManageServiceImpl.class);

    @Autowired
    private FileManageMapper fileManageMapper;

    /**
     * 1.上传附件操作
     *
     * @param file
     */
    @Transactional
    @Override
    public void uploadFile(MultipartFile file) throws SQLException {

        //读取配置文件中的下载地址
        String uploadUrl = ReadConfigUtil.readConfigFileByJava("UPLOAD_FILE");
        //上传附件到指定地址，并返回执行数据
        ResponseEntity<Map> uploadFile = FileUtils.uploadFile(file, uploadUrl);
        //获取文件真实保存路径
        String realFilePath = (String) uploadFile.getBody().get("path");
        //切分
        String fileName = FileUtils.getFileNameWithSuffix(realFilePath);
        String suffix = FileUtils.getFilrSuffix(realFilePath);
        //拼接网络地址
        String net_upload = ReadConfigUtil.readConfigFileByJava("NET_FILE");
        String filePath = net_upload + fileName;
        //拼接传递参数
        UploadFileDB fileDB = new UploadFileDB();
        fileDB.setId(UUID.randomUUID().toString());
        fileDB.setFileName(fileName);
        fileDB.setFileType(suffix);
        fileDB.setFilePath(filePath);
        fileDB.setFileUser("");
        fileDB.setFileModule("");
        //调用插入方法
        fileManageMapper.uploadFile(fileDB);
    }
}
