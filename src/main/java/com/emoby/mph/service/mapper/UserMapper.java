package com.emoby.mph.service.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.emoby.mph.domain.Authority;
import com.emoby.mph.domain.Sector;
import com.emoby.mph.domain.User;
import com.emoby.mph.service.dto.SectorDTO;
import com.emoby.mph.service.dto.UserDTO;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).collect(Collectors.toList());
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            
            //TODO DD A améliorer avec mapper::toDTO
            if(userDTO.getSectors() != null) {
            	Set<Sector> sectors = userDTO.getSectors().stream().map(sectorDTO -> {
            		Sector sector = new Sector();
            		sector.setId(sectorDTO.getId());
            		sector.setCode(sectorDTO.getCode());
            		sector.setLibelle(sectorDTO.getLibelle());
            		return sector;
            	}).collect(Collectors.toSet());
            	
            	user.setSectors(sectors);
            }
            
            return user;
        }
    }

	public Set<Sector> sectorFromDTO(Set<SectorDTO> sectorDTO) {
        Set<Sector> sectors = new HashSet<>();

        if (sectorDTO != null) {
        	sectors =
        			sectorDTO
                    .stream()
                    .map(
                    		dto -> {
                        	Sector sector = new Sector();
                        	sector.setLibelle(dto.getLibelle());
                        	sector.setId(dto.getId());
                        	sector.setCode(dto.getCode());
                            return sector;
                        }
                    )
                    .collect(Collectors.toSet());
        }

        return sectors;
    }
    
	private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities =
                authoritiesAsString
                    .stream()
                    .map(
                        string -> {
                            Authority auth = new Authority();
                            auth.setName(string);
                            return auth;
                        }
                    )
                    .collect(Collectors.toSet());
        }

        return authorities;
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
