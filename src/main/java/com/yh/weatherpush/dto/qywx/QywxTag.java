package com.yh.weatherpush.dto.qywx;

import java.io.Serializable;
import lombok.Data;

@Data
public class QywxTag implements Serializable {

    private static final long serialVersionUID = 1141658194181269440L;

    private Integer tagid;

    private String tagname;
}
