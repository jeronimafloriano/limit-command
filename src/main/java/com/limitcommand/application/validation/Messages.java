package com.limitcommand.application.validation;

import br.com.leverinfo.validation.ValidationMessage;

public enum Messages implements ValidationMessage {

  REQUIRED_ID("0000", "ID is required and cannot be null"),
  REQUIRED_AMOUNT("0001", "Amount is required and cannot be null");

  Messages(String code, String message) {
        this.code = code;
        this.message = message;
  }

  private final String code;
  private final String message;

  public String getCode() {
    return code;
  }

  public final String getMessage() {
    return message;
  }
}
