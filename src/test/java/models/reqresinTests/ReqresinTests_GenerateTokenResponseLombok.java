package models.reqresinTests;

import lombok.Data;

@Data
public class ReqresinTests_GenerateTokenResponseLombok {

    private String status;
    private String token;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}