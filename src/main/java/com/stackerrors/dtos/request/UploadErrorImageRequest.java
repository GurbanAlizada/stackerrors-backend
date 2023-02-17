package com.stackerrors.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadErrorImageRequest {

    @NotNull
    private int errorId;

    @NotNull
    private List<MultipartFile> files;


}
