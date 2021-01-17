package net.kreddo.notes.service.mapper;

import java.util.List;
import net.kreddo.notes.controller.dto.NoteDto;
import net.kreddo.notes.repository.model.NoteEntity;
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
    uses = UserMapper.class)
public interface NoteMapper {

  @Mapping(target = "id", source = "id")
  NoteDto toNoteDto(NoteEntity noteEntity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  List<NoteDto> toNoteDtoList(List<NoteEntity> noteEntities, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  @InheritInverseConfiguration
  NoteEntity toNote(NoteDto noteDto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
