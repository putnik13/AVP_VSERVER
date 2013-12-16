package com.atanor.vserver.domain.converter;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.atanor.vserver.common.rpc.dto.PresentationDto;
import com.atanor.vserver.domain.entity.Presentation;

public class PresentationConverter extends AbstractConverter<PresentationDto, Presentation> {

	@Override
	public PresentationDto toDto(final Presentation entity) {
		Validate.notNull(entity, "entity param can not be null");

		final PresentationDto dto = new PresentationDto(entity.getId());
		dto.setName(entity.getName());
		dto.setCreateTime(entity.getCreateTime());
		dto.setOutdated(entity.isOutdated());
		
		return dto;
	}

	public List<PresentationDto> toListDto(final List<Presentation> entities) {
		return convertEntityList(this, entities);
	}

	@Override
	public Presentation toEntity(final PresentationDto dto) {
		Validate.notNull(dto, "dto param can not be null");

		final Presentation entity = new Presentation(dto.getId());
		entity.setName(dto.getName());
		entity.setCreateTime(dto.getCreateTime());
		entity.setOutdated(dto.isOutdated());
		
		return entity;
	}

	public List<Presentation> toListEntities(final List<PresentationDto> dtos) {
		return convertDtoList(this, dtos);
	}
}
