package com.springfulldemo.api.model.beans;

import lombok.Data;

import java.util.Base64;

@Data
public class MultipartBean {

    private String file;

    private String filename;

    public String getType() {
        String fileNormalized = this.file.replace("data:", "");

        return fileNormalized.substring(0, fileNormalized.indexOf(";"));
    }

    public String getBase64() {
        String[] fileSplit = this.file.split(",");

        return fileSplit.length >= 1 ? fileSplit[1] : file;
    }

    public byte[] getBytes() {
        return Base64.getDecoder().decode(getBase64());
    }

    public String getFormat() {
        return this.filename.substring(this.filename.indexOf(".") + 1);
    }

}