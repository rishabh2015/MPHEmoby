package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.ContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Content} and its DTO {@link ContentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContentMapper extends EntityMapper<ContentDTO, Content> {



    default Content fromId(Long id) {
        if (id == null) {
            return null;
        }
        Content content = new Content();
        content.setId(id);
        return content;
    }
}
