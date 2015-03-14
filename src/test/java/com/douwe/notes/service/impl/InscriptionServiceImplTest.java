/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.service.impl;

import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.dao.IInscriptionDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Genre;
import com.douwe.notes.entities.Inscription;
import com.douwe.notes.service.IInscriptionService;
import java.util.Date;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class InscriptionServiceImplTest {
    
    public InscriptionServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInscriptionDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testGetInscriptionDao() throws Exception {
        System.out.println("getInscriptionDao");
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        IInscriptionDao expResult = null;
        IInscriptionDao result = instance.getInscriptionDao();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInscriptionDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testSetInscriptionDao() throws Exception {
        System.out.println("setInscriptionDao");
        IInscriptionDao inscriptionDao = null;
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        instance.setInscriptionDao(inscriptionDao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEtudiantDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testGetEtudiantDao() throws Exception {
        System.out.println("getEtudiantDao");
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        IEtudiantDao expResult = null;
        IEtudiantDao result = instance.getEtudiantDao();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEtudiantDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testSetEtudiantDao() throws Exception {
        System.out.println("setEtudiantDao");
        IEtudiantDao etudiantDao = null;
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        instance.setEtudiantDao(etudiantDao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAcademiqueDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testGetAcademiqueDao() throws Exception {
        System.out.println("getAcademiqueDao");
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        IAnneeAcademiqueDao expResult = null;
        IAnneeAcademiqueDao result = instance.getAcademiqueDao();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAcademiqueDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testSetAcademiqueDao() throws Exception {
        System.out.println("setAcademiqueDao");
        IAnneeAcademiqueDao academiqueDao = null;
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        instance.setAcademiqueDao(academiqueDao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParcoursDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testGetParcoursDao() throws Exception {
        System.out.println("getParcoursDao");
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        IParcoursDao expResult = null;
        IParcoursDao result = instance.getParcoursDao();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setParcoursDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testSetParcoursDao() throws Exception {
        System.out.println("setParcoursDao");
        IParcoursDao parcoursDao = null;
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        instance.setParcoursDao(parcoursDao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNiveauDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testGetNiveauDao() throws Exception {
        System.out.println("getNiveauDao");
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        INiveauDao expResult = null;
        INiveauDao result = instance.getNiveauDao();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNiveauDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testSetNiveauDao() throws Exception {
        System.out.println("setNiveauDao");
        INiveauDao niveauDao = null;
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        instance.setNiveauDao(niveauDao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOptionDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testGetOptionDao() throws Exception {
        System.out.println("getOptionDao");
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        IOptionDao expResult = null;
        IOptionDao result = instance.getOptionDao();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOptionDao method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testSetOptionDao() throws Exception {
        System.out.println("setOptionDao");
        IOptionDao optionDao = null;
        InscriptionServiceImpl instance = new InscriptionServiceImpl();
        instance.setOptionDao(optionDao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveOrUpdateInscription method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testSaveOrUpdateInscription() throws Exception {
        System.out.println("saveOrUpdateInscription");
        Inscription inscription = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IInscriptionService instance = (IInscriptionService)container.getContext().lookup("java:global/classes/InscriptionServiceImpl");
        Inscription expResult = null;
        Inscription result = instance.saveOrUpdateInscription(inscription);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteInscription method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testDeleteInscription() throws Exception {
        System.out.println("deleteInscription");
        long id = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IInscriptionService instance = (IInscriptionService)container.getContext().lookup("java:global/classes/InscriptionServiceImpl");
        instance.deleteInscription(id);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findInscriptionById method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testFindInscriptionById() throws Exception {
        System.out.println("findInscriptionById");
        long id = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IInscriptionService instance = (IInscriptionService)container.getContext().lookup("java:global/classes/InscriptionServiceImpl");
        Inscription expResult = null;
        Inscription result = instance.findInscriptionById(id);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllInscriptions method, of class InscriptionServiceImpl.
     */
    @Test
    @Ignore
    public void testGetAllInscriptions() throws Exception {
        System.out.println("getAllInscriptions");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IInscriptionService instance = (IInscriptionService)container.getContext().lookup("java:global/classes/InscriptionServiceImpl");
        List<Inscription> expResult = null;
        List<Inscription> result = instance.getAllInscriptions();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inscrireEtudiant method, of class InscriptionServiceImpl.
     */
    @Test
    public void testInscrireEtudiant() throws Exception {
        System.out.println("inscrireEtudiant");
        Etudiant etudiant = new Etudiant();
        etudiant.setActive(1);
        etudiant.setDateDeNaissance(new Date());
        etudiant.setEmail("mymail");
        etudiant.setGenre(Genre.feminin);
        etudiant.setLieuDeNaissance("kaikai");
        etudiant.setMatricule("34ZER65");
        etudiant.setNom("you");
        etudiant.setNumeroTelephone("98765432");
        String codeNiveau = "852";
        String codeOption = "Reseaux";
        Long anneeId = 951L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IInscriptionService instance = (IInscriptionService)container.getContext().lookup("java:global/classes/InscriptionServiceImpl");
        Inscription expResult = null;
        Inscription result = instance.inscrireEtudiant(etudiant, codeNiveau, codeOption, anneeId);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
