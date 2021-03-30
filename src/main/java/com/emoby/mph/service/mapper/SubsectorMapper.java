package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.SubsectorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subsector} and its DTO {@link SubsectorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubsectorMapper extends EntityMapper<SubsectorDTO, Subsector> {



    default Subsector fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subsector subsector = new Subsector();
        subsector.setId(id);
        return subsector;
    }
}
