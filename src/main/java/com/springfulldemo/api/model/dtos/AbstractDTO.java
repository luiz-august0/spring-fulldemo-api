package com.springfulldemo.api.model.dtos;

import com.springfulldemo.api.model.entities.AbstractEntity;

import java.io.Serializable;

public abstract class AbstractDTO<Entity extends AbstractEntity> implements Serializable {

}