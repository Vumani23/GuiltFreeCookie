package za.ac.cput.guiltfreecookie.controller;

public class ChangePasswordRequest {

    private String currentPassword;
    private String newPassword;

    protected ChangePasswordRequest() {
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
