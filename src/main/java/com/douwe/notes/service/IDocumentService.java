package com.douwe.notes.service;

import java.io.OutputStream;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IDocumentService {
    
    public String produirePv(Long niveauId, Long optionId, Long coursId, Long academiqueId, int session,OutputStream stream)throws ServiceException;
    
    public String produireSynthese(Long niveauId, Long optionId,Long academiqueId,Long semestreId, OutputStream stream) throws ServiceException;
    
    
    public void produireRelevet(Long niveauId, Long optionId, Long anneeId, OutputStream stream);
    
    
    
}
