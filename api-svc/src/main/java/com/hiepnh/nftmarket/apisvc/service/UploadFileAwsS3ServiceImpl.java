package com.hiepnh.nftmarket.apisvc.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.common.base.Strings;
import com.hiepnh.nftmarket.apisvc.common.HeaderInfo;
import com.hiepnh.nftmarket.apisvc.domain.request.DeleteFileUploadRequest;
import com.hiepnh.nftmarket.apisvc.domain.response.BaseResponse;
import com.hiepnh.nftmarket.apisvc.domain.response.GetSingleItemResponse;
import com.hiepnh.nftmarket.apisvc.entites.FileUploadEntity;
import com.hiepnh.nftmarket.apisvc.repository.FileUploadRepo;
import com.hiepnh.nftmarket.apisvc.utils.HashUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class UploadFileAwsS3ServiceImpl extends BaseService implements UploadFileService {

    private final AmazonS3 amazonS3;

    private final FileUploadRepo fileUploadRepo;

    @Value("${aws.bucket}")
    private String bucketName;

    @Value("${aws.base_url}")
    private String baseUrl;

    public UploadFileAwsS3ServiceImpl(AmazonS3 amazonS3, FileUploadRepo fileUploadRepo) {
        this.amazonS3 = amazonS3;
        this.fileUploadRepo = fileUploadRepo;
    }

    @Override
//    @Async
    public GetSingleItemResponse<String> uploadFile(final MultipartFile multipartFile, HeaderInfo headerInfo) {
        GetSingleItemResponse<String> response = new GetSingleItemResponse<>();

        // check file này đã được upload chưa
        String checkSumValue;
        try {
            checkSumValue = HashUtils.checksumMd5(multipartFile);
            Optional<FileUploadEntity> fileUploadOptional = fileUploadRepo.findByCheckSumValue(checkSumValue);
            if (fileUploadOptional.isPresent()) {
                response.setItem(fileUploadOptional.get().getFileUrl());
                response.setSuccess();
                return response;
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            checkSumValue = "";
            logger.error("checksum file {} error: {}, ", multipartFile.getOriginalFilename(), e);
        }

        logger.info("File upload in progress.");
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            if (file == null) {
                response.setResult(-1, "Upload file xảy ra lỗi");
                return response;
            }
            String url = baseUrl + uploadFileToS3Bucket(bucketName, file);
            logger.info("File upload is completed.");
            if (file.delete()) {
                logger.info("Deleted temporary file");
            } else {
                logger.info("Can't delete temporary files");
            }

            if (!Strings.isNullOrEmpty(checkSumValue)) {
                FileUploadEntity fileUploadEntity = new FileUploadEntity();
                fileUploadEntity.setFileUrl(url);
                fileUploadEntity.setCheckSumValue(checkSumValue);
                fileUploadEntity.setCreateAt(System.currentTimeMillis());
                fileUploadEntity.setCreateBy(headerInfo.getUsername());

                fileUploadRepo.save(fileUploadEntity);
            }

            response.setItem(url);
            response.setSuccess();
            return response;
        } catch (final AmazonServiceException ex) {
            logger.info("File upload is failed.");
            logger.error("Error= {} while uploading file.", ex.getMessage());
            response.setResult(-1, "Error");
            return response;
        }
    }

    @Override
    public BaseResponse deleteFile(DeleteFileUploadRequest request) {
        BaseResponse response = new BaseResponse();
        if (request.getFileUrlList() == null) {
            response.setSuccess();
            return response;
        }
        for (String url : request.getFileUrlList()) {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, url);
            amazonS3.deleteObject(deleteObjectRequest);

            fileUploadRepo.deleteByUrl(url);
        }
        response.setSuccess();
        return response;
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        if (multipartFile.getOriginalFilename() == null) {
            return null;
        }
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            logger.error("Error converting the multi-part file to file: {}", ex.getMessage());
        }
        return file;
    }

    private String uploadFileToS3Bucket(final String bucketName, final File file) {
        String uniqueFileName = UUID.randomUUID() + "_" + System.currentTimeMillis();
        logger.info("Uploading file with name= " + uniqueFileName);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
        amazonS3.putObject(putObjectRequest);
        return uniqueFileName;
    }

}
