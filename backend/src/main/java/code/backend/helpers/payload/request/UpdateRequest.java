package code.backend.helpers.payload.request;

import java.util.List;

public class UpdateRequest {
    private String _password;
    private String _confirmPassword;
    private String _role;
    private String _email;

    public String Title;
    public String FirstName;
    public String LastName;

    // [EnumDataType(typeof(RoleEnum))]
    public List<String> Role;
    public String Email;
    public String Password;
    public String ConfirmPassword;

}