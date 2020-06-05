package cn.erick.aiomp.entity.file;

import java.io.Serializable;

/**
 * @Author Erick
 * @Date 2020/6/3 16:07
 * @Version 1.0
 */
public class UploadFileDB implements Serializable {

    private static final long serialVersionUID = 100000000L;
    private String id;//id
    private String fileName;//文件名
    private String fileType;//文件类型
    private String filePath;//文件路径
    private String fileUser;//文件所属用户
    private String fileModule;//文件所属模块

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUser() {
        return fileUser;
    }

    public void setFileUser(String fileUser) {
        this.fileUser = fileUser;
    }

    public String getFileModule() {
        return fileModule;
    }

    public void setFileModule(String fileModule) {
        this.fileModule = fileModule;
    }

    @Override
    public String toString() {
        return "UploadFileDB{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileUser='" + fileUser + '\'' +
                ", fileModule='" + fileModule + '\'' +
                '}';
    }
}
