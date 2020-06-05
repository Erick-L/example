package cn.erick.aiomp.mapper.filemanage;

import cn.erick.aiomp.entity.file.UploadFileDB;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 文件管理
 * @Author Erick
 * @Date 2020/6/3 17:14
 * @Version 1.0
 */
@Repository
@Mapper
public interface FileManageMapper {

    //文件上传操作
    void uploadFile(UploadFileDB fileData);
}
