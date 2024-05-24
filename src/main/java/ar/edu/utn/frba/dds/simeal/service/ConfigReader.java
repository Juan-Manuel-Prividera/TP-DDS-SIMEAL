package ar.edu.utn.frba.dds.simeal.service;

import java.io.File;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;


public class ConfigReader {
  private Configuration configuration;

  public ConfigReader(String fileName) {
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
