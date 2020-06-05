package cn.erick.aiomp.entity.email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Email实体类操作
 */
public class EmailDB implements Serializable {

    private String id;//邮件id
    private String sender;//发件人
    private String recipient;//收件人
    private String subject;//邮件主题
    private String context;//邮件内容
    private Date sendDate;//发送日期
    private String cc;
    private String status;//状态
    private String errormsg;//报错信息
    @JsonIgnore
    private MultipartFile[] multipartFiles;//邮件附件

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public MultipartFile[] getMultipartFiles() {
        return multipartFiles;
    }

    public void setMultipartFiles(MultipartFile[] multipartFiles) {
        this.multipartFiles = multipartFiles;
    }

    @Override
    public String toString() {
        return "EmailDB{" +
                "id='" + id + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", context='" + context + '\'' +
                ", sendDate=" + sendDate +
                ", cc='" + cc + '\'' +
                ", status='" + status + '\'' +
                ", errormsg='" + errormsg + '\'' +
                ", multipartFiles=" + Arrays.toString(multipartFiles) +
                '}';
    }
}
