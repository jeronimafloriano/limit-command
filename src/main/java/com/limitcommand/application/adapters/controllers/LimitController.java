package com.limitcommand.application.adapters.controllers;

import com.limitcommand.application.dtos.LimitRequestDTO;
import com.limitcommand.application.dtos.LimitResponseDTO;
import com.limitcommand.application.mappers.LimitMapper;
import com.limitcommand.domain.port.LimiteServicePort;
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

  private final LimiteServicePort limiteServicePort;
  private final LimitMapper limitMapper;

  public LimitController(LimiteServicePort limiteServicePort, LimitMapper limitMapper) {
    this.limiteServicePort = limiteServicePort;
    this.limitMapper = limitMapper;
  }

  @GetMapping("/{accountId}")
  public ResponseEntity<LimitResponseDTO> consult(@PathVariable UUID accountId) {
    return limiteServicePort.consult(accountId)
        .map(limitMapper::toDTO)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/{accountId}/availability")
  public ResponseEntity<Boolean> consultAvailability(
      @PathVariable UUID accountId, @RequestParam BigDecimal amount) {
    boolean isAvailable = limiteServicePort.consultAvailability(accountId, amount);
    return ResponseEntity.ok(isAvailable);
  }

  @PostMapping("/{accountId}/reserve")
  public ResponseEntity<Void> reserve(
      @PathVariable UUID accountId,
      @RequestBody LimitRequestDTO requestDTO) {
    limiteServicePort.reserve(accountId, requestDTO.getAmount());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/{accountId}/release")
  public ResponseEntity<Void> release(
      @PathVariable UUID accountId,
      @RequestBody LimitRequestDTO requestDTO) {
    limiteServicePort.release(accountId, requestDTO.getAmount());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{accountId}/adjustment")
  public ResponseEntity<Void> adjustment(
      @PathVariable UUID accountId,
      @RequestBody LimitRequestDTO requestDTO) {
    limiteServicePort.applyAdjustment(accountId, requestDTO.getAmount());
    return ResponseEntity.ok().build();
  }
}
