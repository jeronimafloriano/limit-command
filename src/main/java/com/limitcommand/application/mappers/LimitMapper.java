package com.limitcommand.application.mappers;

import com.limitcommand.application.dtos.LimitResponseDTO;
import com.limitcommand.domain.Limit;
import org.springframework.stereotype.Component;

@Component
public class LimitMapper {

    public LimitResponseDTO toDTO(Limit limit) {
        if (limit == null) {
            return null;
        }
        return new LimitResponseDTO(
                limit.getAccountId(), limit.getTotalLimit(), limit.getReservedLimit(), limit.getAvailableLimit());
    }
}
