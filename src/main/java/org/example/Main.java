package org.example;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.List;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) throws Exception {

        Document doc = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("output.pdf"));
        doc.open();

        var root = new List();

        var list1 = new List();
        list1.setIndentationLeft(20);

        var list2 = new List();
        list2.setIndentationLeft(20);

        /*
        Expected output:
        - 1
            - a
                - i
        - 2
         */

        root.add("1");
        list1.add("a");
        list2.add("i");
        list1.add(list2);
        root.add(list1);
        root.add("2");

        var dc = writer.getDirectContent();

        ColumnText columnText = new ColumnText(dc);
        columnText.setSimpleColumn(
                0,
                PageSize.A4.getHeight(),
                300,
                300);
        columnText.setAlignment(Element.ALIGN_LEFT);
        columnText.addElement(root);
        columnText.go();

        doc.close();
        writer.close();
    }
}