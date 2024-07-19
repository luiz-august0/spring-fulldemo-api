package com.springfulldemo.api.external.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.model.beans.MultipartBean;
import com.springfulldemo.api.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class S3StorageService {

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public String upload(MultipartBean multipartBean, Boolean isPublic) {
        File fileObj = FileUtil.convertMultipartBeanToFile(multipartBean);

        try {
            String fileName = System.currentTimeMillis() + "_" + multipartBean.getFilename();

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, fileObj);

            if (isPublic) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }

            s3Client.putObject(putObjectRequest);


            return s3Client.getUrl(bucketName, fileName).toString();
        } finally {
            fileObj.delete();
        }

    }

    public byte[] download(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);

        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try {
            byte[] content = IOUtils.toByteArray(inputStream);

            return content;
        } catch (IOException e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    public void delete(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
    }

}