package edu.AnastasiiaTkachuk.mapper;

import edu.AnastasiiaTkachuk.dto.CompanyReadDto;
import edu.AnastasiiaTkachuk.entity.Company;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {
    @Override
    public CompanyReadDto mapFrom(Company object) {
        return new CompanyReadDto(
                object.getId(),
                object.getName()
        );
    }
}
