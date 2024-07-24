package com.springfulldemo.api.infrastructure.converter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springfulldemo.api.infrastructure.annotations.ObjectFieldsOnly;
import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.model.dtos.AbstractDTO;
import com.springfulldemo.api.model.entities.AbstractEntity;
import com.springfulldemo.api.utils.ClassUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.lang.reflect.Field;
import java.util.*;

public class Converter {
    private static final ModelMapper mapper = new ModelMapper();

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO<Entity>> DTO convertEntityToDTO(Entity entity, Class<DTO> dtoClass) {
        try {
            DTO dtoObj = dtoClass.getDeclaredConstructor().newInstance();
            List<Field> dtoFields = Arrays.stream(dtoClass.getDeclaredFields()).toList().stream().filter(Converter::doFilterAnnotations).toList();

            mapperEntityFieldsToDTOFields(entity, dtoObj, dtoFields);

            return dtoObj;
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO<Entity>> Entity convertDTOToEntity(DTO dto, Class<Entity> entityClass) {
        return mapper.map(dto, entityClass);
    }

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO<Entity>> List<DTO> convertEntityToDTO(List<Entity> entityList, Class<DTO> dtoClass) {
        return entityList.stream().map(entity -> convertEntityToDTO(entity, dtoClass)).toList();
    }

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO<Entity>> List<Entity> convertDTOToEntity(List<DTO> dtoList, Class<Entity> entityClass) {
        return dtoList.stream().map(dto -> convertDTOToEntity(dto, entityClass)).toList();
    }

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO<Entity>> Page<DTO> convertEntityToDTO(Page<Entity> entityPage, Class<DTO> dtoClass) {
        return new PageImpl<>(
                entityPage.stream().map(entity -> convertEntityToDTO(entity, dtoClass)).toList(),
                entityPage.getPageable(),
                entityPage.getTotalElements()
        );
    }

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO<Entity>> Page<Entity> convertDTOToEntity(Page<DTO> dtoPage, Class<Entity> entityClass) {
        return new PageImpl<>(
                dtoPage.stream().map(dto -> convertDTOToEntity(dto, entityClass)).toList(),
                dtoPage.getPageable(),
                dtoPage.getTotalElements()
        );
    }

    private static <Entity extends AbstractEntity, DTO extends AbstractDTO<Entity>> DTO mapperEntityFieldsToDTOFields(Entity entity, DTO dtoObj, List<Field> dtoFields) {
        dtoFields.forEach(dtoField -> {
            try {
                Field entityField = entity.getClass().getDeclaredField(dtoField.getName());
                entityField.setAccessible(true);
                dtoField.setAccessible(true);
                Object entityFieldValue = entityField.get(entity);

                if (entityFieldValue instanceof AbstractEntity) {
                    dtoField.set(dtoObj, getDTOValueFromDTOField((Entity) entityFieldValue, (Class<DTO>) dtoField.getType(), dtoField));
                } else if (entityFieldValue instanceof Collection<?> entityCollection) {
                    if (!entityCollection.isEmpty()) {
                        Class<?> entityCollectionClass = ClassUtil.getClassFromCollectionField(entityField);

                        if (entityCollectionClass.getSuperclass().equals(AbstractEntity.class)) {
                            Class<DTO> dtoCollectionClass = (Class<DTO>) ClassUtil.getClassFromCollectionField(dtoField);
                            Collection<DTO> dtoCollection;

                            if (entityCollection instanceof HashSet<?>) {
                                dtoCollection = new HashSet<>();
                            } else {
                                dtoCollection = new ArrayList<>();
                            }

                            entityCollection.forEach(entityCollectionFieldValue -> {
                                dtoCollection.add(getDTOValueFromDTOField((Entity) entityCollectionFieldValue, dtoCollectionClass, dtoField));
                            });

                            dtoField.set(dtoObj, dtoCollection);
                        } else {
                            dtoField.set(dtoObj, entityFieldValue);
                        }
                    }
                } else {
                    dtoField.set(dtoObj, entityFieldValue);
                }

            } catch (Exception e) {
                throw new ApplicationGenericsException(e.getMessage());
            }
        });

        return dtoObj;
    }

    private static <Entity extends AbstractEntity, DTO extends AbstractDTO<Entity>> DTO getDTOValueFromDTOField(Entity entity, Class<DTO> dtoClass, Field dtoField) {
        try {
            String[] DTOFields;
            String[] DTOFieldsToIgnore;

            if (dtoField.isAnnotationPresent(ObjectFieldsOnly.class)) {
                DTOFields = dtoField.getAnnotation(ObjectFieldsOnly.class).value();
                DTOFieldsToIgnore = dtoField.getAnnotation(ObjectFieldsOnly.class).ignored();
            } else {
                DTOFields = new String[0];
                DTOFieldsToIgnore = new String[0];
            }

            DTO dtoFieldObj = dtoClass.getDeclaredConstructor().newInstance();
            List<Field> dtoFields = Arrays.stream(dtoClass.getDeclaredFields()).toList().stream().filter(field ->
                    doFilterAnnotations(field) && (doDTOFieldsFilter(DTOFields, field)) && (doDTOFieldsToIgnoreFilter(DTOFieldsToIgnore, field))
            ).toList();

            return mapperEntityFieldsToDTOFields(entity, dtoFieldObj, dtoFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    private static boolean doFilterAnnotations(Field dtoField) {
        return ((!dtoField.isAnnotationPresent(JsonProperty.class)) ||
                (dtoField.isAnnotationPresent(JsonProperty.class) && !dtoField.getAnnotation(JsonProperty.class).access().equals(JsonProperty.Access.WRITE_ONLY))) &&
                (!dtoField.isAnnotationPresent(JsonIgnore.class));
    }

    private static boolean doDTOFieldsFilter(String[] DTOFields, Field dtoField) {
        return DTOFields.length == 0 || Arrays.stream(DTOFields).anyMatch(DTOField -> DTOField.equals(dtoField.getName()));
    }

    private static boolean doDTOFieldsToIgnoreFilter(String[] DTOFieldsToIgnore, Field dtoField) {
        return DTOFieldsToIgnore.length == 0 || Arrays.stream(DTOFieldsToIgnore).noneMatch(DTOField -> DTOField.equals(dtoField.getName()));
    }
}