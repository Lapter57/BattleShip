package com.lapter57.db.service;

import com.lapter57.db.model.AbstractEntity;
import com.lapter57.db.repository.CommonRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>>
        implements CommonService<E> {

    protected final R repository;
}
