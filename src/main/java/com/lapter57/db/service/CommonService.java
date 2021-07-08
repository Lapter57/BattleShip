package com.lapter57.db.service;

import com.lapter57.db.model.AbstractEntity;

public interface CommonService<E extends AbstractEntity> {

    E save(E entity);
}
