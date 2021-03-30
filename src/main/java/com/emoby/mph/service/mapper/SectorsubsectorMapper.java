package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.SectorsubsectorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SectorSubsector} and its DTO {@link SectorsubsectorDTO}.
 */
@Mapper(componentModel = "spring", uses = {SectorMapper.class, SubsectorMapper.class})
public interface SectorsubsectorMapper extends EntityMapper<SectorsubsectorDTO, SectorSubsector> {

    @Mapping(source = "sector.id", target = "sectorId")
    @Mapping(source = "sector.libelle", target = "sectorLibelle")
    @Mapping(source = "subsector.id", target = "subsectorId")
    @Mapping(source = "subsector.libelle", target = "subsectorLibelle")
    SectorsubsectorDTO toDto(SectorSubsector sectorsubsector);

    @Mapping(source = "sectorId", target = "sector")
    @Mapping(source = "subsectorId", target = "subsector")
    SectorSubsector toEntity(SectorsubsectorDTO sectorsubsectorDTO);

    default SectorSubsector fromId(Long id) {
        if (id == null) {
            return null;
        }
        SectorSubsector sectorsubsector = new SectorSubsector();
        sectorsubsector.setId(id);
        return sectorsubsector;
    }
}
