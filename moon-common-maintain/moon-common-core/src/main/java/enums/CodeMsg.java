package enums;

import lombok.Getter;

@Getter
public enum CodeMsg {

    SUCCESS(200, "200", "请求处理成功"),
    ERROR(500, "500", "系统异常");

    private final int code;
    private final String msg;

    CodeMsg(int code, String codeStr, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
