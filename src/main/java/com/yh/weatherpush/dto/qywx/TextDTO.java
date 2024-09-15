package com.yh.weatherpush.dto.qywx;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class TextDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 766135219074593197L;

    private String content;
}
