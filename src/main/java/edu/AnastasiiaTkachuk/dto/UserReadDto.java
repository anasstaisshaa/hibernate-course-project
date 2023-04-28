package edu.AnastasiiaTkachuk.dto;

import edu.AnastasiiaTkachuk.entity.PersonalInfo;
import edu.AnastasiiaTkachuk.entity.Role;

public record UserReadDto(Long id,
                          PersonalInfo personalInfo,
                          String username,
                          Role role,
                          CompanyReadDto company) {
}
