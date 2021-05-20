package org.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {

    public static final String OK_CODE = "0";

    private String code;
    private String message;
    private Object data;

    public static CommonResponse ok(Object data) {
        return new CommonResponse(OK_CODE, "", data);
    }

    public static CommonResponse fail(String code, Exception ex) {
        return new CommonResponse(code, ex.getMessage(), null);
    }
}
