package org.manzatech.brewer.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.net.URI;

@Plugin(name = "LogConfiguration", category = ConfigurationFactory.CATEGORY)
@Order(1)
public class LoggingConfig extends ConfigurationFactory {

    static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        builder.setConfigurationName(name);
        builder.setStatusLevel(Level.WARN);

        AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE");
        appenderBuilder.addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);

        LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout");
        layoutBuilder.addAttribute("pattern", "%d{HH:mm:ss.SSS} %-5level %logger{36}: %msg%n");

        appenderBuilder.add(layoutBuilder);
        builder.add(appenderBuilder);

        RootLoggerComponentBuilder  rootBuilder = builder.newRootLogger(Level.ERROR);
        AppenderRefComponentBuilder appenderRef =  builder.newAppenderRef("Stdout");
        rootBuilder.add(appenderRef);

        LoggerComponentBuilder loggerBuilder = builder.newLogger("org.hibernate.SQL", Level.DEBUG);
        builder.add(loggerBuilder);

        builder.add(rootBuilder);

        return builder.build();
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[] {"*"};
    }

}
