package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.TechnicalDisciplineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechnicalDiscipline} and its DTO {@link TechnicalDisciplineDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TechnicalDisciplineMapper extends EntityMapper<TechnicalDisciplineDTO, TechnicalDiscipline> {



    default TechnicalDiscipline fromId(Long id) {
        if (id == null) {
            return null;
        }
        TechnicalDiscipline technicalDiscipline = new TechnicalDiscipline();
        technicalDiscipline.setId(id);
        return technicalDiscipline;
    }
}
