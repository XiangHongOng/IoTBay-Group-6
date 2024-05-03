package org.project.iotprojecttest.model.objects;

import java.io.Serializable;
import java.util.Date;

public class UserAccessLog implements Serializable {

    private int logId;
    private int userId;
    private Date loginDateTime;
    private Date logoutDateTime;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(Date loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public Date getLogoutDateTime() {
        return logoutDateTime;
    }

    public void setLogoutDateTime(Date logoutDateTime) {
        this.logoutDateTime = logoutDateTime;
    }
}
