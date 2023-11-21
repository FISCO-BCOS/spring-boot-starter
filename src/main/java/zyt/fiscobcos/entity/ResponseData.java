package zyt.fiscobcos.entity;

/**
 * @Author: zyt
 * @Description:结果集
 * @Date: Created in 13:35 2021/1/8
 */
@SuppressWarnings("unchecked")
public class ResponseData<T> {

    private int code;
    private String msg;
    private T data;

    public static ResponseData success(String msg) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(200);
        responseData.setMsg(msg);
        return responseData;
    }

    public static <T> ResponseData<T> success(T t) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(200);
        responseData.setData(t);
        return responseData;
    }

    public static ResponseData error(String msg) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(500);
        responseData.setMsg(msg);
        return responseData;
    }

    public static <T> ResponseData<T> error(T t) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(500);
        responseData.setData(t);
        return responseData;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
