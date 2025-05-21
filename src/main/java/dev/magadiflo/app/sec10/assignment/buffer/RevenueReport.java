package dev.magadiflo.app.sec10.assignment.buffer;

import java.time.LocalTime;
import java.util.Map;

// Reporte de ingresos
public record RevenueReport(LocalTime time,
                            Map<String, Integer> revenue) {
}
