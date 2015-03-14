/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.douwe.notes.web.beans;

import com.douwe.notes.entities.UniteEnseignement;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author root
 */
public class UEDataModel  extends ListDataModel<UniteEnseignement> implements SelectableDataModel<UniteEnseignement> {
    public  UEDataModel() {  
    }  
  
    public  UEDataModel(List<UniteEnseignement> data) {  
        super(data);  
    }
    public Object getRowKey(UniteEnseignement t) {
        System.out.println("================== 1");
        return t.getCode();
    }

    public UniteEnseignement getRowData(String rowKey) {
         List<UniteEnseignement> uniteEnseignements = (List<UniteEnseignement>) getWrappedData();  
          System.out.println("================== 2");
        for(UniteEnseignement uniteEnseignement : uniteEnseignements) {  
            if(uniteEnseignement.getCode().equals(rowKey))  
                return uniteEnseignement;  
        }  
          
        return null;  
    }
    
}
