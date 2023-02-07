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
public class UpdateQuestionRequest {


    @NotNull
    int questionId;

    @NotBlank
    String title;

    @NotBlank
    String text;


    @NotNull
    List<Integer> tags;




}
