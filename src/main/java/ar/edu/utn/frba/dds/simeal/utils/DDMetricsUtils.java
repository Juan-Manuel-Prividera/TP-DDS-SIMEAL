package ar.edu.utn.frba.dds.simeal.utils;

import com.itextpdf.text.pdf.PRAcroForm;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmHeapPressureMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.step.StepMeterRegistry;
import io.micrometer.datadog.DatadogConfig;
import io.micrometer.datadog.DatadogMeterRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.math.DD;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DDMetricsUtils {
  @Getter
  private final StepMeterRegistry registry;

  @Getter @Setter
  private AtomicInteger acceso;

  @Getter @Setter
  private AtomicInteger dineroDonado;

  @Getter @Setter
  private AtomicInteger accesoHeladera;

  @Getter @Setter
  private AtomicInteger viandasDonadas;

  @Getter @Setter
  private AtomicInteger incidentesReportados;
  @Getter @Setter
  private AtomicInteger ofertasCanjeadas;

  private static DDMetricsUtils instance = null;

  static public DDMetricsUtils getInstance() {
    if (instance == null) {
      instance = new DDMetricsUtils("siemal");
    }
    return instance;
  }

  private DDMetricsUtils(String appTag) {
    // crea un registro para nuestras métricas basadas en DD
    var config = new DatadogConfig() {
      @Override
      public Duration step() {
        return Duration.ofSeconds(10);
      }

      @Override
      public String apiKey() {
        return "b415f8d279580e15aa172703cf303e6e";
      }

      @Override
      public String uri() {
        return "https://api.us5.datadoghq.com";
      }

      @Override
      public String get(String k) {
        return null; // accept the rest of the defaults
      }
    };
    registry = new DatadogMeterRegistry(config, Clock.SYSTEM);
    registry.config().commonTags("app", appTag );
    initInfraMonitoring() ;
  }

  private void initInfraMonitoring() {
    // agregamos a nuestro reigstro de métricas todo lo relacionado a infra/tech
    // de la instancia y JVM
    try (var jvmGcMetrics = new JvmGcMetrics(); var jvmHeapPressureMetrics = new JvmHeapPressureMetrics()) {
      jvmGcMetrics.bindTo(registry);
      jvmHeapPressureMetrics.bindTo(registry);
    }
    new JvmMemoryMetrics().bindTo(registry);
    new ProcessorMetrics().bindTo(registry);
    new FileDescriptorMetrics().bindTo(registry);
  }

}
