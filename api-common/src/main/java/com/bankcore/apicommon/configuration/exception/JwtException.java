package com.bankcore.apicommon.configuration.exception;

public class JwtException extends RuntimeException {

  public JwtException() {
    super();
  }

  public JwtException(final String message) {
    super(message);
  }
}
