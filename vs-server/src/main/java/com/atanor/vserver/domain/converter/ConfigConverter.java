package com.atanor.vserver.domain.converter;

import org.apache.commons.lang3.Validate;

import com.atanor.vserver.common.rpc.dto.VsConfigDto;
import com.atanor.vserver.domain.entity.VsConfig;

public class ConfigConverter extends AbstractConverter<VsConfigDto, VsConfig> {

	@Override
	public VsConfigDto toDto(final VsConfig entity) {
		Validate.notNull(entity, "entity param can not be null");

		final VsConfigDto dto = new VsConfigDto(entity.getId());
		dto.setName(entity.getName());
		dto.setDefault(entity.isDefault());
		dto.setAddLogo(entity.getAddLogo());
		dto.setPalantirPort(entity.getPalantirPort());
		dto.setPalantirUrl(entity.getPalantirUrl());
		dto.setPlayerInstallPath(entity.getPlayerInstallPath());
		dto.setRecordingsOutput(entity.getRecordingsOutput());
		dto.setPresentationsOutput(entity.getPresentationsOutput());
		dto.setRecordingMediaOptions(entity.getRecordingMediaOptions());
		dto.setPresentationMediaOptions(entity.getPresentationMediaOptions());
		dto.setRecordingMediaResource(entity.getRecordingMediaResource());
		dto.setPresentationMediaResource(entity.getPresentationMediaResource());
		dto.setRecordingSnapshotOutput(entity.getRecordingSnapshotOutput());
		dto.setPresentationSnapshotOutput(entity.getPresentationSnapshotOutput());
		
		return dto;
	}

	@Override
	public VsConfig toEntity(final VsConfigDto dto) {
		Validate.notNull(dto, "dto param can not be null");

		final VsConfig entity = new VsConfig(dto.getId());
		entity.setName(dto.getName());
		entity.setDefault(dto.isDefault());
		entity.setAddLogo(dto.getAddLogo());
		entity.setPalantirPort(dto.getPalantirPort());
		entity.setPalantirUrl(dto.getPalantirUrl());
		entity.setPlayerInstallPath(dto.getPlayerInstallPath());
		entity.setRecordingsOutput(dto.getRecordingsOutput());
		entity.setPresentationsOutput(dto.getPresentationsOutput());
		entity.setRecordingMediaOptions(dto.getRecordingMediaOptions());
		entity.setPresentationMediaOptions(dto.getPresentationMediaOptions());
		entity.setRecordingMediaResource(dto.getRecordingMediaResource());
		entity.setPresentationMediaResource(dto.getPresentationMediaResource());
		entity.setRecordingSnapshotOutput(dto.getRecordingSnapshotOutput());
		entity.setPresentationSnapshotOutput(dto.getPresentationSnapshotOutput());
		
		return entity;
	}

}
