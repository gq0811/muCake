package com.cainiao.arrow.arrowservice.groovy;

import java.util.Date;

public class ScriptFileInfo {

    /**
     * 路径
     */
    private String filePath;

    /**
     * 文件内容
     */
    private String content;

    /**
     * 编码方式
     */
    private String encoding = "text";

    /**
     * 上次更新时间
     */
    private Date gmtModified;

    /**
     * 脚本类型
     */
    private Integer scriptType;

    /**
     * 脚本状态，0 禁用 1 启用
     */
    private Integer status = 1;

    /**
     * 上次更新后的版本号
     */
    private String version;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getScriptType() {
        return scriptType;
    }

    public void setScriptType(Integer scriptType) {
        this.scriptType = scriptType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
