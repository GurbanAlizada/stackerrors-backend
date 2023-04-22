package com.stackerrors.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenResponseDto {

    private Map<String ,String> tokens;

    private UserDto userDto;


}
