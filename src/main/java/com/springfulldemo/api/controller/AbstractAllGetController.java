package com.springfulldemo.api.controller;

import com.springfulldemo.api.controller.interfaces.IAbstractAllGetController;
import com.springfulldemo.api.infrastructure.converter.Converter;
import com.springfulldemo.api.model.dtos.AbstractDTO;
import com.springfulldemo.api.service.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AbstractAllGetController<Service extends AbstractService, DTO extends AbstractDTO>
        implements IAbstractAllGetController<DTO>, Serializable {
    private final Service service;

    private final DTO dto;

    AbstractAllGetController(Service service, DTO dto) {
        this.service = service;
        this.dto = dto;
    }

    public List<DTO> findAll() {
        return Converter.convertEntityToDTO(service.findAll(), dto.getClass());
    }

    public List<DTO> findAllFiltered(Pageable pageable, Map<String, Object> filters) {
        return Converter.convertEntityToDTO(service.findAllFiltered(pageable, filters), dto.getClass());
    }

    public Page<DTO> findAllFilteredAndPageable(Pageable pageable, Map<String, Object> filters) {
        return Converter.convertEntityToDTO(service.findAllFilteredAndPageable(pageable, filters), dto.getClass());
    }

    public DTO findById(Integer id) {
        return (DTO) Converter.convertEntityToDTO(service.findAndValidate(id), dto.getClass());
    }

}