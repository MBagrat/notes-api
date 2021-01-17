package net.kreddo.notes.mapper;

import java.util.List;
import net.kreddo.notes.dto.UserDto;
import net.kreddo.notes.model.User;
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
  @Mapping(target = "id", source = "id")
  UserDto toUserDto(User user, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  List<UserDto> toUserDtoList(List<User> users, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  @InheritInverseConfiguration
  User toUser(UserDto userDto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

}
