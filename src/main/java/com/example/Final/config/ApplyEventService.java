package com.example.Final.config;

import com.example.Final.Dto.ApplyEventRequest;
import com.example.Final.Dto.ApplyEventResponse;

public interface ApplyEventService {
    ApplyEventResponse applyForEvent(ApplyEventRequest request);
}
