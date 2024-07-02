package universityconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import universityconnect.domain.Report;
import universityconnect.domain.Role;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminDTO extends UserDTO {
    private String department;
    private List<Long> surveyIds;

    public AdminDTO() {}

}

