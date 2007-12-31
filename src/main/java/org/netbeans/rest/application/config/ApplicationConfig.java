package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@javax.ws.rs.ApplicationPath("/api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
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
        resources.add(com.douwe.notes.resource.impl.CoursResource.class);
        resources.add(com.douwe.notes.resource.impl.CycleResource.class);
        resources.add(com.douwe.notes.resource.impl.DepartementResource.class);
        resources.add(com.douwe.notes.resource.impl.EnseignantResource.class);
        resources.add(com.douwe.notes.resource.impl.EtudiantResource.class);
        resources.add(com.douwe.notes.resource.impl.NiveauResource.class);
        resources.add(com.douwe.notes.resource.impl.OptionResource.class);
        resources.add(com.douwe.notes.resource.impl.TypeCoursResource.class);
    }
    
}
