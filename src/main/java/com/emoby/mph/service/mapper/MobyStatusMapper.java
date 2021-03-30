package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.MobyStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MobyStatus} and its DTO {@link MobyStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MobyStatusMapper extends EntityMapper<MobyStatusDTO, MobyStatus> {



    default MobyStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        MobyStatus mobyStatus = new MobyStatus();
        mobyStatus.setId(id);
        return mobyStatus;
    }
}
