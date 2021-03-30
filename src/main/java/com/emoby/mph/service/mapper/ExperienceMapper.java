package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.ExperienceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Experience} and its DTO {@link ExperienceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExperienceMapper extends EntityMapper<ExperienceDTO, Experience> {



    default Experience fromId(Long id) {
        if (id == null) {
            return null;
        }
        Experience experience = new Experience();
        experience.setId(id);
        return experience;
    }
}
