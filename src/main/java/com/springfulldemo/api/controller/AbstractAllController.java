package com.springfulldemo.api.controller;

import com.springfulldemo.api.controller.interfaces.IAbstractAllController;
import com.springfulldemo.api.infrastructure.converter.Converter;
import com.springfulldemo.api.model.dtos.AbstractDTO;
import com.springfulldemo.api.service.AbstractService;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public abstract class AbstractAllController
        <Service extends AbstractService, DTO extends AbstractDTO>
        extends AbstractAllGetController
        implements IAbstractAllController<DTO>, Serializable
{
    private final Service service;

    AbstractAllController(Service service) {
        super(service);
        this.service = service;
    }

    public ResponseEntity<DTO> insert(DTO dto) {
        return service.insert(Converter.convertDTOToEntity(dto, service.entity.getClass()));
    }

    public ResponseEntity<DTO> activateInactivate(Integer id, Boolean active) {
        return service.activateInactivate(id, active);
    }

    public ResponseEntity<DTO> update(Integer id, DTO dto) {
        return service.update(id, Converter.convertDTOToEntity(dto, service.entity.getClass()));
    }
}
