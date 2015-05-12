package com.douwe.notes.service;

import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Cours;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Session;
import com.douwe.notes.entities.UniteEnseignement;
import com.itextpdf.text.Document;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IDocumentService {
    
    public String produirePv(Long niveauId, Long optionId, Long coursId, Long academiqueId, int session,OutputStream stream)throws ServiceException;
    
    public String produireSynthese(Long niveauId, Long optionId,Long academiqueId,Long semestreId, OutputStream stream) throws ServiceException;
    
    public String produireSyntheseAnnuelle(OutputStream stream)throws ServiceException;
    
}
