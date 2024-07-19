package com.springfulldemo.api.service;

import com.springfulldemo.api.infrastructure.converter.Converter;
import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumResourceInactiveException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumResourceNotFoundException;
import com.springfulldemo.api.infrastructure.specs.builders.SpecificationBuilder;
import com.springfulldemo.api.model.dtos.AbstractDTO;
import com.springfulldemo.api.model.entities.AbstractEntity;
import com.springfulldemo.api.model.entities.User;
import com.springfulldemo.api.validators.AbstractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public abstract class AbstractService
        <Repository extends JpaRepository & PagingAndSortingRepository & JpaSpecificationExecutor,
                Entity extends AbstractEntity, DTO extends AbstractDTO<Entity>, Validator extends AbstractValidator> {
    private final Repository repository;

    public final Entity entity;

    public final DTO dto;

    private final Validator validator;

    @Autowired
    private ApplicationContext applicationContext;

    AbstractService(Repository repository, Entity entity, DTO dto, Validator validator) {
        this.repository = repository;
        this.entity = entity;
        this.dto = dto;
        this.validator = validator;
    }

    public <Service extends AbstractService> Service getServiceBean(Class<? extends AbstractEntity> entityClass) {
        try {
            Class<? extends AbstractService> serviceClass = entityClass.getDeclaredConstructor().newInstance().getServiceClass();

            return (Service) applicationContext.getBean(serviceClass);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    public List<DTO> findAll() {
        return Converter.convertListEntityToDTO(repository.findAll(), dto.getClass());
    }

    public List<DTO> findAllFiltered(Pageable pageable, Map<String, Object> filters) {
        return Converter.convertListEntityToDTO(repository.findAll(SpecificationBuilder.toSpec(filters), pageable.getSort()), dto.getClass());
    }

    public Page<DTO> findAllFilteredAndPageable(Pageable pageable, Map<String, Object> filters) {
        return Converter.convertPageEntityToDTO(repository.findAll(SpecificationBuilder.toSpec(filters), pageable), dto.getClass());
    }

    public Entity findAndValidate(Integer id) {
        Optional object = repository.findById(id);

        if (object.isEmpty()) {
            throw new ApplicationGenericsException(EnumResourceNotFoundException.RESOURCE_NOT_FOUND, entity.getPortugueseClassName(), id);
        }

        return (Entity) object.get();
    }

    public Entity findAndValidateActive(Integer id, Boolean returnObjectName) {
        Entity entityObject = this.findAndValidate(id);

        try {
            Field field = entityObject.getClass().getDeclaredField("active");
            field.setAccessible(true);
            Boolean active = (Boolean) field.get(entityObject);

            if (active.equals(false)) {
                if (returnObjectName) {
                    throw new ApplicationGenericsException(
                            EnumResourceInactiveException.RESOURCE_INACTIVE,
                            entity.getPortugueseClassName(),
                            entityObject.getObjectName()
                    );
                } else {
                    throw new ApplicationGenericsException(EnumResourceInactiveException.RESOURCE_INACTIVE, entity.getPortugueseClassName(), id);
                }
            }
        } catch (NoSuchFieldException e) {
            throw new ApplicationGenericsException("Classe " + entity.getClass().getName() + " não tem campo active");
        } catch (IllegalAccessException e) {
            throw new ApplicationGenericsException("Não foi possível acessar o campo active da classe " + entity.getClass().getName());
        }

        return entityObject;
    }

    public DTO findDTOAndValidate(Integer id) {
        Entity entityObject = this.findAndValidate(id);

        return (DTO) Converter.convertEntityToDTO(entityObject, dto.getClass());
    }

    public <GenericEntity extends AbstractEntity> GenericEntity findAndValidateGeneric(Class<? extends AbstractEntity> entityClass, Integer id) {
        return (GenericEntity) getServiceBean(entityClass).findAndValidate(id);
    }

    public <GenericEntity extends AbstractEntity> GenericEntity findAndValidateActiveGeneric(Class<? extends AbstractEntity> entityClass, Integer id, Boolean returnObjectName) {
        return (GenericEntity) getServiceBean(entityClass).findAndValidateActive(id, returnObjectName);
    }

    public ResponseEntity<DTO> insert(Entity entityObject) {
        validator.validate(entityObject);

        repository.save(entityObject);
        return ResponseEntity.status(HttpStatus.CREATED).body((DTO) Converter.convertEntityToDTO(entityObject, dto.getClass()));
    }

    public ResponseEntity<DTO> activateInactivate(Integer id, Boolean active) {
        Entity entityObject = this.findAndValidate(id);

        try {
            Field field = entityObject.getClass().getDeclaredField("active");
            field.setAccessible(true);
            field.set(entityObject, active);
        } catch (NoSuchFieldException e) {
            throw new ApplicationGenericsException("Classe " + entity.getClass().getName() + " não tem campo active");
        } catch (IllegalAccessException e) {
            throw new ApplicationGenericsException("Não foi possível acessar o campo active da classe " + entity.getClass().getName());
        }

        repository.save(entityObject);
        return ResponseEntity.ok().body((DTO) Converter.convertEntityToDTO(entityObject, dto.getClass()));
    }

    public ResponseEntity<DTO> update(Integer id, Entity entityObject) {
        this.findAndValidate(id);

        try {
            Class<?> objectClass = entityObject.getClass();
            Field field = objectClass.getDeclaredField("id");
            field.setAccessible(true);
            field.set(entityObject, id);
        } catch (NoSuchFieldException e) {
            throw new ApplicationGenericsException("Classe " + entity.getClass().getName() + " não tem campo ID");
        } catch (IllegalAccessException e) {
            throw new ApplicationGenericsException("Não foi possível acessar o campo id da classe " + entity.getClass().getName());
        }

        validator.validate(entityObject);

        repository.save(entityObject);
        return ResponseEntity.ok().body((DTO) Converter.convertEntityToDTO(entityObject, dto.getClass()));
    }

    public User getUserByContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
