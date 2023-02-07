package com.stackerrors.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AskQuestionRequest {

   @NotBlank
   String title;

   @NotBlank
   String description;


   @NotNull
   boolean draft;


   @NotNull
   List<Integer> tags;

   List<MultipartFile> files;


}
