package com.bankcore.apicommon.configuration.mapping;

public interface BaseMapping<T, U> extends TargetToSourceMapping<U, T>, SourceToTargetMapping<T, U> {

}
