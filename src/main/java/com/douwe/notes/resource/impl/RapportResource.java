package com.douwe.notes.resource.impl;

import com.douwe.notes.resource.IRapportResource;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Path("/rapport")
public class RapportResource implements IRapportResource {

    @Override
    public Response test() throws Exception {
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                try {
                    buildDocument(output);
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                }
            }
        };

        //return Response.ok()
        return Response.ok(stream).header("Content-Disposition",
                "attachment; filename=toto.pdf").build();
    }
    
    // Cette méthode pourrait bien etre dans une autre classe carrement
    private void buildDocument(OutputStream out) throws Exception{
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, out);
        doc.open();
        Paragraph p = new Paragraph("Hello world my friend");
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
        doc.add(new Paragraph("Vraiment de mieux en mieux"));
        doc.close();
    }

}
