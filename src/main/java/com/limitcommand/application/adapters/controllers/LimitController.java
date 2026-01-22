package com.limitcommand.application.adapters.controllers;

import com.limitcommand.application.dtos.LimitRequestDTO;
import com.limitcommand.application.dtos.LimitResponseDTO;
import com.limitcommand.application.mappers.LimitMapper;
import com.limitcommand.domain.Limit;
import com.limitcommand.domain.port.LimitServicePort;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/limits")
public class LimitController {

    private final LimitServicePort limitServicePort;
    private final LimitMapper limitMapper;

    public LimitController(LimitServicePort limitServicePort, LimitMapper limitMapper) {
        this.limitServicePort = limitServicePort;
        this.limitMapper = limitMapper;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<LimitResponseDTO> consult(@PathVariable("accountId") UUID accountId) {
        Limit limit = limitServicePort.consult(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(limitMapper.toDTO(limit));
    }

    @GetMapping("/{accountId}/availability")
    public ResponseEntity<Boolean> consultAvailability(
            @PathVariable("accountId") UUID accountId, @Valid @RequestParam BigDecimal amount) {
        boolean isAvailable = limitServicePort.consultAvailability(accountId, amount);
        return ResponseEntity.ok(isAvailable);
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<LimitResponseDTO> create(
            @PathVariable("accountId") UUID accountId, @Valid @RequestBody LimitRequestDTO requestDTO) {
        Limit limit = limitServicePort.create(accountId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(limitMapper.toDTO(limit));
    }

    @PostMapping("/{accountId}/reserve")
    public ResponseEntity<Void> reserve(
            @PathVariable("accountId") UUID accountId, @Valid @RequestBody LimitRequestDTO requestDTO) {
        limitServicePort.reserve(accountId, requestDTO.getAmount());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accountId}/release")
    public ResponseEntity<Void> release(
            @PathVariable("accountId") UUID accountId, @Valid @RequestBody LimitRequestDTO requestDTO) {
        limitServicePort.release(accountId, requestDTO.getAmount());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accountId}/adjustment")
    public ResponseEntity<Void> adjustment(
            @PathVariable("accountId") UUID accountId, @Valid @RequestBody LimitRequestDTO requestDTO) {
        limitServicePort.applyAdjustment(accountId, requestDTO.getAmount());
        return ResponseEntity.noContent().build();
    }
}
