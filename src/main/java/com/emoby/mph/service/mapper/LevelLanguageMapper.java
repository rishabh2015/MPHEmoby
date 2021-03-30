package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.LevelLanguageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LevelLanguage} and its DTO {@link LevelLanguageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LevelLanguageMapper extends EntityMapper<LevelLanguageDTO, LevelLanguage> {



    default LevelLanguage fromId(Long id) {
        if (id == null) {
            return null;
        }
        LevelLanguage levelLanguage = new LevelLanguage();
        levelLanguage.setId(id);
        return levelLanguage;
    }
}
