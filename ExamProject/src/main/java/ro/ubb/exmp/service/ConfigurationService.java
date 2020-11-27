package ro.ubb.exmp.service;

import ro.ubb.exmp.config.Configuration;
import ro.ubb.exmp.repository.ConfigXmlRepository;

public class ConfigurationService {

    private ConfigXmlRepository configXmlRepository;


    public ConfigurationService(ConfigXmlRepository configXmlRepository) {

        this.configXmlRepository = configXmlRepository;
    }

    public Configuration getConfig() {

        return this.configXmlRepository.loadConfigFromXML();

    }
}
