package ee.edio.garmin.runconfig.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.builders.BuildTargetType;
import org.jetbrains.jps.incremental.BuilderService;
import org.jetbrains.jps.incremental.TargetBuilder;

import java.util.Arrays;
import java.util.List;

public class MonkeyCBuilderService extends BuilderService {

  @NotNull
  @Override
  public List<? extends TargetBuilder<?, ?>> createBuilders() {
    return super.createBuilders();
  }

  @NotNull
  @Override
  public List<? extends BuildTargetType<?>> getTargetTypes() {
    return Arrays.asList(MonkeyCTargetType.PRODUCTION, MonkeyCTargetType.TESTS);

  }
}
