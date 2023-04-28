package edu.AnastasiiaTkachuk.dto;

import edu.AnastasiiaTkachuk.entity.PersonalInfo;
import edu.AnastasiiaTkachuk.entity.Role;

public record UserCreateDto(PersonalInfo personalInfo,
                            String username,
                            Role role,
                            Integer companyId) {
}
