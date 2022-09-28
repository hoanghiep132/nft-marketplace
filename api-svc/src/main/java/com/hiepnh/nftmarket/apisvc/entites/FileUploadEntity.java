package com.hiepnh.nftmarket.apisvc.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "file_upload")
public class FileUploadEntity extends BaseEntity {

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "check_sum")
    private String checkSumValue;


}
