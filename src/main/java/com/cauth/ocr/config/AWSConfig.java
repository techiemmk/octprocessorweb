package com.cauth.ocr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSConfig {

    @Value("${aws.regionName:ap-south-1}")
    private String regionName;

    @Value("${aws.accesskey:AKIAZ33IHANFC354RAEP}")
    private String awsAccesskey;
//AKIAZ33IHANFC354RAEP ::: M+6lv/ZmsR/IBrMEhe0t3m9VydfCP12sCuCJ51YU
//AKIAZ33IHANFPBDYDB6F ::: xtS76ZkzBJ36AgCWFk9fJ7L9zI/gu2y3wXxGpW9F
    @Value("${aws.secretkey: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}")
    private String awsSecretkey;

    @Bean
    public AWSCredentials s3ReadCredentials() {
        return new BasicAWSCredentials(awsAccesskey, awsSecretkey);
    }

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(s3ReadCredentials()))
                .withRegion(Regions.fromName(regionName))
                .build();

    }
}
