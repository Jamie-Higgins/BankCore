package com.bankcore.apicommon.configuration.mapping;

import org.modelmapper.PropertyMap;

public interface TargetToSourceMapping<T, U> {

  PropertyMap<T, U> mapFromTargetToSource();
}
