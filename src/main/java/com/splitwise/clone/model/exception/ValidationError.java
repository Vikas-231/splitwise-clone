package com.splitwise.clone.model.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class ValidationError {

    private String name;

    private String path;

    private String reason;
}
