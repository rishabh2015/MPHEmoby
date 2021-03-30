package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.ProjectphaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Projectphase} and its DTO {@link ProjectphaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectphaseMapper extends EntityMapper<ProjectphaseDTO, Projectphase> {



    default Projectphase fromId(Long id) {
        if (id == null) {
            return null;
        }
        Projectphase projectphase = new Projectphase();
        projectphase.setId(id);
        return projectphase;
    }
}
