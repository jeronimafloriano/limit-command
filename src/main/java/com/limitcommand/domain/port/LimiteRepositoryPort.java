package com.limitcommand.domain.port;

import com.limitcommand.domain.Limit;
import java.util.Optional;
import java.util.UUID;

public interface LimiteRepositoryPort {
  Optional<Limit> findByAccountId(UUID accountId);
  void save(Limit limit);

}
