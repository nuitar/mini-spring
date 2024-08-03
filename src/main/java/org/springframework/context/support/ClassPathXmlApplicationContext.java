package org.springframework.context.support;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    private String[] configLocations;

    public ClassPathXmlApplicationContext(String locations){
        this(new String[]{locations});
    }

    public ClassPathXmlApplicationContext(String[] locations){
        this.configLocations = locations;
        refresh();

    }
    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
