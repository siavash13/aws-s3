package com.myapp.aws;


public class App
{
    public static void main( String[] args )
    {
        S3Client s3Client = new S3Client();
        s3Client.readFile();
    }
}
