package com.stackerrors.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTagRequest {

    @NotNull
    private int id;

    @NotBlank
   private String tagName;

    @NotBlank
   private String about;

}
