package com.pjh.share.domain.account;

import org.springframework.data.repository.CrudRepository;

public interface SessionUserRedisRepository extends CrudRepository<SessionUser,Long> {
}
