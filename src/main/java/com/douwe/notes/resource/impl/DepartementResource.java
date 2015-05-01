package com.douwe.notes.resource.impl;

import com.douwe.notes.resource.IDepartementResource;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IDepartementService;
import com.douwe.notes.service.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Path("/departements")
public class DepartementResource implements IDepartementResource {

    @EJB
    private IDepartementService departementService;

    @Override
    public Departement createDepartement(Departement dep) {
        try {
            return departementService.saveOrUpdateDepartement(dep);
            //return Response.created(URI.create("/notes/api/departements/" + ret.getId())).build();
        } catch (ServiceException ex) {
            Logger.getLogger(DepartementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Departement getDepartement( long id) {
        try {
            Departement depart = departementService.findDepartementById(id);
            if (depart == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return depart;
        } catch (ServiceException ex) {
            Logger.getLogger(DepartementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Departement updateDepartement( long id, Departement current) {
        try {
            Departement depart = departementService.findDepartementById(id);
            if (depart == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            //Departement current = readDepartement(in);
            depart.setCode(current.getCode());
            depart.setFrenchDescription(current.getFrenchDescription());
            depart.setEnglishDescription(current.getEnglishDescription());
            return departementService.saveOrUpdateDepartement(depart);
        } catch (ServiceException ex) {
            Logger.getLogger(DepartementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

//    private Departement readDepartement(InputStream in) {
//        try {
//            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            Document doc = builder.parse(in);
//            Element root = doc.getDocumentElement();
//            Departement depart = new Departement();
//            if (root.getAttribute("id") != null && !root.getAttribute("id").trim().equals("")) {
//                depart.setId(Long.valueOf(root.getAttribute("id")));
//            }
//            NodeList nodes = root.getChildNodes();
//            for (int i = 0; i < nodes.getLength(); i++) {
//                Element element = (Element) nodes.item(i);
//                if (element.getTagName().equals("code")) {
//                    depart.setCode(element.getTextContent());
//                }
//                if (element.getTagName().equals("intitule")) {
//                    depart.setDescription(element.getTextContent());
//                }
//            }
//            return depart;
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(DepartementResource.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SAXException ex) {
//            Logger.getLogger(DepartementResource.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(DepartementResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        throw new WebApplicationException(Response.Status.BAD_REQUEST);
//    }
//
//    private void outputDepartement(OutputStream output, Departement depart) {
//        PrintWriter writer = new PrintWriter(output);
//        writer.println(serializeDepartement(depart));
//        writer.close();
//
//    }
//
//    private String serializeDepartement(Departement depart) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("<departement id=\"");
//        builder.append(depart.getId());
//        builder.append("\">");
//        builder.append("<code>");
//        builder.append(depart.getCode());
//        builder.append("</code>");
//        builder.append("<intitule>");
//        builder.append(depart.getDescription());
//        builder.append("</intitule>");
//        builder.append("</departement>");
//        return builder.toString();
//    }

    @Override
    public List<Departement> getAllDepartement() {
        try {
            List<Departement> deps = departementService.getAllDepartements();
            return deps;
        } catch (ServiceException ex) {
            Logger.getLogger(DepartementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteDepartement(long id) {
        try {
            departementService.deleteDepartement(id);
        } catch (ServiceException ex) {
            Logger.getLogger(DepartementResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public List<Option> getAllOptions(long id) {
        try {
            Departement dep = departementService.findDepartementById(id);
            return departementService.getAllOptions(dep);
        } catch (ServiceException ex) {
            Logger.getLogger(DepartementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public IDepartementService getDepartementService() {
        return departementService;
    }

    public void setDepartementService(IDepartementService departementService) {
        this.departementService = departementService;
    }

    @Override
    public Departement findByCode(String code) {
        try {
            Departement departement = departementService.findByCode(code);
            if(departement == null){
                throw  new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return departement;
        } catch (ServiceException ex) {
            Logger.getLogger(DepartementResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
