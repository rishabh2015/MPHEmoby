package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.EducationlevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Educationlevel} and its DTO {@link EducationlevelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EducationlevelMapper extends EntityMapper<EducationlevelDTO, Educationlevel> {



    default Educationlevel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Educationlevel educationlevel = new Educationlevel();
        educationlevel.setId(id);
        return educationlevel;
    }
}
