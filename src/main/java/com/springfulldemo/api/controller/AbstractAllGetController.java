package com.springfulldemo.api.controller;

import com.springfulldemo.api.controller.interfaces.IAbstractAllGetController;
import com.springfulldemo.api.model.dtos.AbstractDTO;
import com.springfulldemo.api.service.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AbstractAllGetController<Service extends AbstractService, DTO extends AbstractDTO> implements IAbstractAllGetController<DTO>, Serializable {
    private final Service service;

    AbstractAllGetController(Service service) {
        this.service = service;
    }

    public List<DTO> findAll() {
        return service.findAll();
    }

    public List<DTO> findAllFiltered(Pageable pageable, Map<String, Object> filters) {
        return service.findAllFiltered(pageable, filters);
    }

    public Page<DTO> findAllFilteredAndPageable(Pageable pageable, Map<String, Object> filters) {
        return service.findAllFilteredAndPageable(pageable, filters);
    }

    public DTO findById(Integer id) {
        return (DTO) service.findDTOAndValidate(id);
    }
}
