package com.pratiket.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Pratiksha Deshmukh
 * created on 07-08-2021
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
public class ConnectionException extends RuntimeException
{
    private String message;
    private String detailMessage;
}
