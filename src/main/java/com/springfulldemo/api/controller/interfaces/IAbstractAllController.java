package com.springfulldemo.api.controller.interfaces;

import com.springfulldemo.api.model.dtos.AbstractDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IAbstractAllController<DTO extends AbstractDTO> extends IAbstractAllGetController {
    @PostMapping
    ResponseEntity<DTO> insert(@RequestBody DTO dto);

    @PutMapping("/{id}/activate")
    ResponseEntity<DTO> activateInactivate(@PathVariable("id") Integer id, @RequestParam(value = "active", defaultValue = "true") Boolean active);

    @PutMapping("/{id}")
    ResponseEntity<DTO> update(@PathVariable("id") Integer id, @RequestBody DTO dto);
}
