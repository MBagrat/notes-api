package net.kreddo.notes.service.mapper;

import java.util.List;
import net.kreddo.notes.controller.dto.UserDto;
import net.kreddo.notes.repository.model.UserEntity;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = NoteMapper.class)
public interface UserMapper {
  @Mapping(target = "password", ignore = true)
  UserDto toUserDto(UserEntity userEntity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  List<UserDto> toUserDtoList(List<UserEntity> users, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  @InheritInverseConfiguration
  UserEntity toUser(UserDto userDto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

}
