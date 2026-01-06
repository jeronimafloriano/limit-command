package com.limitcommand.infrastructure.adapters;

import com.limitcommand.infrastructure.adapters.entity.LimitEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringLimitRepository extends JpaRepository<LimitEntity, UUID> {}
