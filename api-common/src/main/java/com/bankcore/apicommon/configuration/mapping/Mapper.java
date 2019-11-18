package com.bankcore.apicommon.configuration.mapping;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

public class Mapper extends ModelMapper {

  public void addMapping(final BaseMapping mapping) {

    Objects.requireNonNull(mapping);
    this.addSourceToTargetMapping(mapping);
    this.addTargetToSourceMapping(mapping);
  }

  public void addSourceToTargetMapping(final SourceToTargetMapping mapping) {

    Objects.requireNonNull(mapping);
    this.addMappings(mapping.mapFromSourceToTarget());
  }

  public void addTargetToSourceMapping(final TargetToSourceMapping mapping) {

    Objects.requireNonNull(mapping);
    this.addMappings(mapping.mapFromTargetToSource());
  }

  public <D, T> List<D> map(final Collection<T> entityList, Class<D> outCLass) {

    Objects.requireNonNull(entityList);
    Objects.requireNonNull(outCLass);

    return entityList.stream()
        .map(entity -> map(entity, outCLass))
        .collect(Collectors.toList());
  }

  public <D, T> Set<D> map(final Set<T> entityList, Class<D> outCLass) {

    Objects.requireNonNull(entityList);
    Objects.requireNonNull(outCLass);

    return entityList.stream()
        .map(entity -> map(entity, outCLass))
        .collect(Collectors.toSet());
  }
}
