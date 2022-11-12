/*
 * Copyright 2014 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tom_roush.pdfbox.multipdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.tom_roush.harmony.awt.AWTColor;
import com.tom_roush.pdfbox.cos.COSDictionary;
import com.tom_roush.pdfbox.cos.COSName;
import com.tom_roush.pdfbox.cos.COSObject;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import com.tom_roush.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentProperties;

import junit.framework.TestCase;

/**
 * Test suite for PDFCloneUtility, see PDFBOX-2052.
 *
 * @author Cornelis Hoeflake
 * @author Tilman Hausherr
 */
public class PDFCloneUtilityTest extends TestCase
{
    /**
     * original (minimal) test from PDFBOX-2052.
     *
     * @throws IOException
     */
    public void testClonePDFWithCosArrayStream() throws IOException
    {
        PDDocument srcDoc = new PDDocument();
        PDDocument dstDoc = new PDDocument();
        PDPage pdPage = new PDPage();
        srcDoc.addPage(pdPage);
        new PDPageContentStream(srcDoc, pdPage, AppendMode.APPEND, true).close();
        new PDPageContentStream(srcDoc, pdPage, AppendMode.APPEND, true).close();
        new PDFCloneUtility(dstDoc).cloneForNewDocument(pdPage.getCOSObject());
        srcDoc.close();
        dstDoc.close();
    }

    /**
     * broader test that saves to a real PDF document.
     *
     * @throws IOException
     */
    public void testClonePDFWithCosArrayStream2() throws IOException
    {
        final String TESTDIR = "target/test-output/clone/";
        final String CLONESRC = "clone-src.pdf";
        final String CLONEDST = "clone-dst.pdf";

        new File(TESTDIR).mkdirs();

        PDDocument srcDoc = new PDDocument();
        PDPage pdPage = new PDPage();
        srcDoc.addPage(pdPage);
        PDPageContentStream pdPageContentStream1 = new PDPageContentStream(srcDoc, pdPage, AppendMode.APPEND, false);
        pdPageContentStream1.setNonStrokingColor(AWTColor.black);
        pdPageContentStream1.addRect(100, 600, 300, 100);
        pdPageContentStream1.fill();
        pdPageContentStream1.close();
        PDPageContentStream pdPageContentStream2 = new PDPageContentStream(srcDoc, pdPage, AppendMode.APPEND, false);
        pdPageContentStream2.setNonStrokingColor(AWTColor.red);
        pdPageContentStream2.addRect(100, 500, 300, 100);
        pdPageContentStream2.fill();
        pdPageContentStream2.close();
        PDPageContentStream pdPageContentStream3 = new PDPageContentStream(srcDoc, pdPage, AppendMode.APPEND, false);
        pdPageContentStream3.setNonStrokingColor(AWTColor.yellow);
        pdPageContentStream3.addRect(100, 400, 300, 100);
        pdPageContentStream3.fill();
        pdPageContentStream3.close();

        srcDoc.save(TESTDIR + CLONESRC);
        PDFMergerUtility merger = new PDFMergerUtility();
        PDDocument dstDoc = new PDDocument();

        // this calls PDFCloneUtility.cloneForNewDocument(), 
        // which would fail before the fix in PDFBOX-2052
        merger.appendDocument(dstDoc, srcDoc);

        // save and reload PDF, so that one can see that the files are legit
        dstDoc.save(TESTDIR + CLONEDST);
        PDDocument.load(new File(TESTDIR + CLONESRC)).close();
        PDDocument.load(new File(TESTDIR + CLONESRC), (String)null).close();
        PDDocument.load(new File(TESTDIR + CLONEDST)).close();
        PDDocument.load(new File(TESTDIR + CLONEDST), (String)null).close();
    }

    /**
     * PDFBOX-4814: this tests merging a direct and an indirect COSDictionary, when "target" is
     * indirect in cloneMerge().
     *
     * @throws IOException
     */
    public void testDirectIndirect() throws IOException
    {
        PDDocument doc1 = new PDDocument();

        doc1.addPage(new PDPage());
        doc1.getDocumentCatalog().setOCProperties(new PDOptionalContentProperties());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        doc1.save(baos);
        PDDocument doc2 = PDDocument.load(baos.toByteArray());
        PDFMergerUtility merger = new PDFMergerUtility();
        // The OCProperties is a direct object here, but gets saved as an indirect object.
        assertTrue(doc1.getDocumentCatalog().getCOSObject().getItem(COSName.OCPROPERTIES) instanceof COSDictionary);
        assertTrue(doc2.getDocumentCatalog().getCOSObject().getItem(COSName.OCPROPERTIES) instanceof COSObject);
        merger.appendDocument(doc2, doc1);
        assertEquals(2, doc2.getNumberOfPages());
        doc2.close();
        doc1.close();
    }
}