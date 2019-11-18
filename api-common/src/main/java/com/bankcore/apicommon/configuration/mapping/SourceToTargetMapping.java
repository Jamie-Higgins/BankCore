package com.bankcore.apicommon.configuration.mapping;

import org.modelmapper.PropertyMap;

public interface SourceToTargetMapping<T, U> {

  PropertyMap<T, U> mapFromSourceToTarget();
}
