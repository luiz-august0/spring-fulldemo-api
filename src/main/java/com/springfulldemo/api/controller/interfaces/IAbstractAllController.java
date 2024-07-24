package com.springfulldemo.api.controller.interfaces;

import com.springfulldemo.api.model.dtos.AbstractDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface IAbstractAllController<DTO extends AbstractDTO> extends IAbstractAllGetController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DTO insert(@RequestBody DTO dto);

    @PutMapping("/{id}/activate")
    DTO activateInactivate(@PathVariable("id") Integer id, @RequestParam(value = "active", defaultValue = "true") Boolean active);

    @PutMapping("/{id}")
    DTO update(@PathVariable("id") Integer id, @RequestBody DTO dto);
}
