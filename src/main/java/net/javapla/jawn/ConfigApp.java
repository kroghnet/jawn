package net.javapla.jawn;

import net.javapla.jawn.util.Constants;

import com.google.inject.AbstractModule;

public class ConfigApp {
    private AbstractModule[] modules;
    private String[] languages;
    private String encoding = Constants.DEFAULT_ENCODING;

    public void registerModules(AbstractModule... modules) {
        this.modules = modules;
    }
    
    AbstractModule[] getRegisteredModules() {
        return modules;
    }
    
    public void setSupportedLanguages(String... languages) {
        this.languages = languages;
    }
    
    String[] getSupportedLanguages() {
        return this.languages;
    }
    
    public void setCharacterEncoding(String encoding) {
        this.encoding = encoding;
    }
    String getCharacterEncoding() {
        return encoding;
    }
}
