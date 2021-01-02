package com.myapp.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.myapp.aws.Credentials.*;

public class S3Client {

    String line;
    BasicAWSCredentials creds;
    AmazonS3 s3Client;
    ObjectListing objectList;

    public S3Client()
    {
        creds = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.CA_CENTRAL_1)
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();

        objectList = s3Client.listObjects(new ListObjectsRequest().withBucketName(BUCKET_NAME));
        if (objectList.getObjectSummaries().size() > 0) {
            System.out.println("filename is : " + objectList.getObjectSummaries());
        }
    }

    public void readFile()
    {
        try {
            S3Object object = s3Client.getObject(BUCKET_NAME, FILE_NAME);
            InputStream objectData = object.getObjectContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(objectData));

            while ((line = reader.readLine()) != null) {
                //implement codes here
            }
        } catch (AmazonServiceException ex) {
            System.err.println(ex.getErrorMessage());
            System.exit(1);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}
