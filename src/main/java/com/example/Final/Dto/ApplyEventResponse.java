package com.example.Final.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyEventResponse {
    private boolean success;
    private String message;
    public ApplyEventResponse(boolean b, String s) {
    }
}