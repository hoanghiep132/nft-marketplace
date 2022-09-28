package com.hiepnh.nftmarket.apisvc.repository;


import com.hiepnh.nftmarket.apisvc.entites.FileUploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FileUploadRepo extends JpaRepository<FileUploadEntity, String> {

    @Query("from FileUploadEntity f where f.checkSumValue = ?1")
    Optional<FileUploadEntity> findByCheckSumValue(String checksum);

    @Query(value = "delete from FileUploadEntity f where f.fileUrl = ?1")
    void deleteByUrl(String url);
}
