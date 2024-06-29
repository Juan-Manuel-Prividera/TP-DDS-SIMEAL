package ar.edu.utn.frba.dds.simeal.service;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;


public class ConfigReader {
  private Configuration configuration;
  String fileName = "src/main/application.properties";

  public ConfigReader() {
    Configurations configs = new Configurations();
    try {
      configuration = configs.properties(new File(fileName));
    } catch (ConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  public String getProperty(String propertyName) {
    return configuration.getString(propertyName);
  }
}
