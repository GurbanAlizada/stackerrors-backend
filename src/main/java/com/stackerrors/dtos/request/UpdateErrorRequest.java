package com.stackerrors.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateErrorRequest {


    @NotNull
    private int errorId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String solution;


    @NotNull
    private List<Integer> tags;



}
