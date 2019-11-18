package com.bankcore.apicommon.configuration.mapping;

import java.util.Set;
import org.modelmapper.Conditions;
import org.modelmapper.convention.MatchingStrategies;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MapperConfig {

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public Mapper mapper() throws IllegalAccessException, InstantiationException, ClassNotFoundException {

    final Mapper mapper = new Mapper();
    mapper.getConfiguration().setAmbiguityIgnored(true);
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

    Set<Class<? extends BaseMapping>> baseMappings = retrieveImplementations(BaseMapping.class, "com.bankcore.apicommon.configuration.mapping.domain");
    for (Class<? extends BaseMapping> mappingClass : baseMappings) {
      mapper.addMapping((BaseMapping) Class.forName(mappingClass.getName()).newInstance());
    }
    return mapper;
  }

  private <T> Set<Class<? extends T>> retrieveImplementations(final Class<T> targetClass, final String mappingPackage) {

    final Reflections reflections = new Reflections(new ConfigurationBuilder()
        .setUrls(ClasspathHelper.forPackage(mappingPackage))
        .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(mappingPackage))));

    return reflections.getSubTypesOf(targetClass);
  }
}