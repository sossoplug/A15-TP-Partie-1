package service.config;

import java.util.Set;

@javax.ws.rs.ApplicationPath("galerieArt")
public class ApplicationConfig extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.config.ArtisteOperations.class);
        resources.add(service.config.CategorieOperations.class);
        resources.add(service.config.ClientsOperations.class);
        resources.add(service.config.CommandeOperations.class);
    }
    
}
