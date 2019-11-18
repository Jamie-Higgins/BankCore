package com.bankcore.apicommon.configuration.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException() {
    super();
  }

  public BadRequestException(final String message) {
    super(message);
  }
}

