package code.backend.helpers.payload.response;

import java.util.Date;
import java.util.List;

public class AccountResponse {
    public int id;
    public String title;
    public String firstName;
    public String lastName;
    public String email;
    public List<String> role;
    public Date created;
    public Date updated;
    public boolean isVerified;
}