package com.atanor.vserver.domain.converter;

import com.atanor.vserver.common.rpc.dto.AbstractDto;
import com.atanor.vserver.domain.entity.AbstractEntity;

@SuppressWarnings("rawtypes")
public interface Converter<D extends AbstractDto, E extends AbstractEntity> {

	D toDto(E entity);

	E toEntity(D dto);
}
