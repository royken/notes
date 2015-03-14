/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.service.impl;

import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.entities.Departement;
import com.douwe.notes.entities.Option;
import com.douwe.notes.service.IDepartementService;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class DepartementServiceImplTest {
    
    public DepartementServiceImplTest() {
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
     * Test of saveOrUpdateDepartement method, of class DepartementServiceImpl.
     */
    @Test
    public void testSaveOrUpdateDepartement() throws Exception {
        System.out.println("saveOrUpdateDepartement");
        Departement departement = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IDepartementService instance = (IDepartementService)container.getContext().lookup("java:global/classes/DepartementServiceImpl");
        Departement expResult = null;
        Departement result = instance.saveOrUpdateDepartement(departement);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDepartement method, of class DepartementServiceImpl.
     */
    @Test
    public void testDeleteDepartement() throws Exception {
        System.out.println("deleteDepartement");
        Long id = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IDepartementService instance = (IDepartementService)container.getContext().lookup("java:global/classes/DepartementServiceImpl");
        instance.deleteDepartement(id);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllDepartements method, of class DepartementServiceImpl.
     */
    @Test
    public void testGetAllDepartements() throws Exception {
        System.out.println("getAllDepartements");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IDepartementService instance = (IDepartementService)container.getContext().lookup("java:global/classes/DepartementServiceImpl");
        List<Departement> expResult = null;
        List<Departement> result = instance.getAllDepartements();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDepartementDao method, of class DepartementServiceImpl.
     */
    @Test
    public void testGetDepartementDao() throws Exception {
        System.out.println("getDepartementDao");
        DepartementServiceImpl instance = new DepartementServiceImpl();
        IDepartementDao expResult = null;
        IDepartementDao result = instance.getDepartementDao();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDepartementDao method, of class DepartementServiceImpl.
     */
    @Test
    public void testSetDepartementDao() throws Exception {
        System.out.println("setDepartementDao");
        IDepartementDao departementDao = null;
        DepartementServiceImpl instance = new DepartementServiceImpl();
        instance.setDepartementDao(departementDao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findDepartementById method, of class DepartementServiceImpl.
     */
    @Test
    public void testFindDepartementById() throws Exception {
        System.out.println("findDepartementById");
        long id = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IDepartementService instance = (IDepartementService)container.getContext().lookup("java:global/classes/DepartementServiceImpl");
        Departement expResult = null;
        Departement result = instance.findDepartementById(id);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllOptions method, of class DepartementServiceImpl.
     */
    @Test
    public void testGetAllOptions() throws Exception {
        System.out.println("getAllOptions");
        Departement departement = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IDepartementService instance = (IDepartementService)container.getContext().lookup("java:global/classes/DepartementServiceImpl");
        List<Option> expResult = null;
        List<Option> result = instance.getAllOptions(departement);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findByCode method, of class DepartementServiceImpl.
     */
    @Test
    public void testFindByCode() throws Exception {
        System.out.println("findByCode");
        String code = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IDepartementService instance = (IDepartementService)container.getContext().lookup("java:global/classes/DepartementServiceImpl");
        Departement expResult = null;
        Departement result = instance.findByCode(code);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
