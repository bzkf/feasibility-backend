package de.numcodex.feasibility_gui_backend.query.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryResultRateLimit {
    private long limit;
    private long remaining;
}
