package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.SectorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sector} and its DTO {@link SectorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SectorMapper extends EntityMapper<SectorDTO, Sector> {



    default Sector fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sector sector = new Sector();
        sector.setId(id);
        return sector;
    }
}
