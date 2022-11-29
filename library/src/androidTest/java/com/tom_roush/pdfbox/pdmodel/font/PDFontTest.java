/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tom_roush.pdfbox.pdmodel.font;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.tom_roush.fontbox.ttf.TTFParser;
import com.tom_roush.fontbox.ttf.TrueTypeCollection;
import com.tom_roush.fontbox.ttf.TrueTypeFont;
import com.tom_roush.fontbox.util.autodetect.FontFileFinder;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.android.TestResourceGenerator;
import com.tom_roush.pdfbox.cos.COSName;
import com.tom_roush.pdfbox.io.IOUtils;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;
import com.tom_roush.pdfbox.rendering.PDFRenderer;
import com.tom_roush.pdfbox.text.PDFTextStripper;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assume.assumeTrue;

/**
 *
 * @author adam
 * @author Tilman Hausherr
 */
public class PDFontTest
{
    private File IN_DIR;
    private File OUT_DIR;
    private Context testContext;

    @Before
    public void setUp() throws Exception
    {
        testContext = InstrumentationRegistry.getInstrumentation().getContext();
        PDFBoxResourceLoader.init(testContext);
        IN_DIR = new File(testContext.getCacheDir(), "fonts");
        IN_DIR.mkdirs();
        OUT_DIR = new File(testContext.getCacheDir(), "pdfbox-test-output");
        OUT_DIR.mkdirs();
    }

    /**
     * Test of the error reported in PDFBOX-988
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void testPDFBox988() throws IOException, URISyntaxException
    {
        PDDocument doc = null;
        try
        {
            File pdf = new File(testContext.getCacheDir(), "F001u_3_7j.pdf");
            OutputStream os = new FileOutputStream(pdf);
            IOUtils.copy(testContext.getAssets().open("pdfbox/com/tom_roush/pdfbox/pdmodel/font/F001u_3_7j.pdf"), os);
            os.close();

            doc = PDDocument.load(pdf);
            PDFRenderer renderer = new PDFRenderer(doc);
            renderer.renderImage(0);
            // the allegation is that renderImage() will crash the JVM or hang
        }
        finally
        {
            if (doc != null)
            {
                doc.close();
            }
        }
    }

    /**
     * PDFBOX-3826: Test ability to reuse a TrueTypeFont created from a file or a stream for several
     * PDFs to avoid parsing it over and over again. Also check that full or partial embedding is
     * done, and do render and text extraction.
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void testPDFBox3826() throws IOException, URISyntaxException
    {
        File fontFile = new File(testContext.getCacheDir(), "F001u_3_7j.pdf");
        OutputStream os = new FileOutputStream(fontFile);
        IOUtils.copy(testContext.getAssets().open("com/tom_roush/pdfbox/resources/ttf/LiberationSans-Regular.ttf"), os);
        os.close();

        TrueTypeFont ttf1 = new TTFParser().parse(fontFile);
        testPDFBox3826checkFonts(testPDFBox3826createDoc(ttf1), fontFile);
        ttf1.close();

        TrueTypeFont ttf2 = new TTFParser().parse(new FileInputStream(fontFile));
        testPDFBox3826checkFonts(testPDFBox3826createDoc(ttf2), fontFile);
        ttf2.close();
    }

    /**
     * PDFBOX-4115: Test ability to create PDF with german umlaut glyphs with a type 1 font.
     * Test for everything that went wrong before this was fixed.
     *
     * @throws IOException
     */
    @Test
    public void testPDFBOX4115() throws IOException
    {
        File fontFile = TestResourceGenerator.downloadTestResource(IN_DIR, "n019003l.pfb", "https://issues.apache.org/jira/secure/attachment/12911053/n019003l.pfb");
        assumeTrue(fontFile.exists());

        File outputFile = new File(OUT_DIR, "FontType1.pdf");
        String text = "äöüÄÖÜ";

        PDDocument doc = new PDDocument();

        PDPage page = new PDPage();
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        PDType1Font font = new PDType1Font(doc, new FileInputStream(fontFile), WinAnsiEncoding.INSTANCE);

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.newLineAtOffset(10, 700);
        contentStream.showText(text);
        contentStream.endText();
        contentStream.close();

        doc.addPage(page);

        doc.save(outputFile);
        doc.close();

        doc = PDDocument.load(outputFile);

        font = (PDType1Font) doc.getPage(0).getResources().getFont(COSName.getPDFName("F1"));
        Assert.assertEquals(WinAnsiEncoding.INSTANCE, font.getEncoding());

        for (char c : text.toCharArray())
        {
            String name = font.getEncoding().getName(c);
            Assert.assertEquals("dieresis", name.substring(1));
            Assert.assertFalse(font.getPath(name).isEmpty());
        }

        PDFTextStripper stripper = new PDFTextStripper();
        Assert.assertEquals(text, stripper.getText(doc).trim());

        doc.close();
    }

    /**
     * Test whether bug from PDFBOX-4318 is fixed, which had the wrong cache key.
     * @throws java.io.IOException
     */
    @Test
    public void testPDFox4318() throws IOException
    {
        try
        {
            PDType1Font.HELVETICA_BOLD.encode("\u0080");
            Assert.fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException ex)
        {
        }
        PDType1Font.HELVETICA_BOLD.encode("€");
        try
        {
            PDType1Font.HELVETICA_BOLD.encode("\u0080");
            Assert.fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException ex)
        {
        }
    }

    @Test
    public void testFullEmbeddingTTC() throws IOException
    {
        FontFileFinder fff = new FontFileFinder();
        TrueTypeCollection ttc = null;
        for (URI uri : fff.find())
        {
            if (uri.getPath().endsWith(".ttc"))
            {
                File file = new File(uri);
                Log.i("PdfBox-Android", "TrueType collection file: " + file);
                ttc = new TrueTypeCollection(file);
                break;
            }
        }
        Assume.assumeTrue("testFullEmbeddingTTC skipped, no .ttc files available", ttc != null);

        final List<String> names = new ArrayList<String>();
        ttc.processAllFonts(new TrueTypeCollection.TrueTypeFontProcessor()
        {
            @Override
            public void process(TrueTypeFont ttf) throws IOException
            {
                Log.i("PdfBox-Android", "TrueType font in collection: " + ttf.getName());
                names.add(ttf.getName());
            }
        });

        TrueTypeFont ttf = ttc.getFontByName(names.get(0)); // take the first one
        Log.i("PdfBox-Android", "TrueType font used for test: " + ttf.getName());

        try
        {
            PDType0Font.load(new PDDocument(), ttf, false);
        }
        catch (IOException ex)
        {
            Assert.assertEquals("Full embedding of TrueType font collections not supported", ex.getMessage());
            return;
        }
        Assert.fail("should have thrown IOException");
    }

    /**
     * Test using broken Type1C font.
     *
     * @throws IOException
     */
    @Test
    public void testPDFox5048() throws IOException
    {
        InputStream is = new URL("https://issues.apache.org/jira/secure/attachment/13017227/stringwidth.pdf").openStream();
        PDDocument doc = PDDocument.load(is);
        PDPage page = doc.getPage(0);
        PDFont font = page.getResources().getFont(COSName.getPDFName("F70"));
        Assert.assertTrue(font.isDamaged());
        Assert.assertEquals(0f, font.getHeight(0), 0);
        Assert.assertEquals(0f, font.getStringWidth("Pa"), 0);
        doc.close();
        is.close();
    }

    private void testPDFBox3826checkFonts(byte[] byteArray, File fontFile) throws IOException
    {
        PDDocument doc = PDDocument.load(byteArray);

        PDPage page2 = doc.getPage(0);

        // F1 = type0 subset
        PDType0Font fontF1 = (PDType0Font) page2.getResources().getFont(COSName.getPDFName("F1"));
        Assert.assertTrue(fontF1.getName().contains("+"));
        Assert.assertTrue(fontFile.length() > fontF1.getFontDescriptor().getFontFile2().toByteArray().length);

        // F2 = type0 full embed
        PDType0Font fontF2 = (PDType0Font) page2.getResources().getFont(COSName.getPDFName("F2"));
        Assert.assertFalse(fontF2.getName().contains("+"));
        Assert.assertEquals(fontFile.length(), fontF2.getFontDescriptor().getFontFile2().toByteArray().length);

        // F3 = tt full embed
        PDTrueTypeFont fontF3 = (PDTrueTypeFont) page2.getResources().getFont(COSName.getPDFName("F3"));
        Assert.assertFalse(fontF2.getName().contains("+"));
        Assert.assertEquals(fontFile.length(), fontF3.getFontDescriptor().getFontFile2().toByteArray().length);

        new PDFRenderer(doc).renderImage(0);

        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setLineSeparator("\n");
        String text = stripper.getText(doc);
        Assert.assertEquals("testMultipleFontFileReuse1\ntestMultipleFontFileReuse2\ntestMultipleFontFileReuse3", text.trim());

        doc.close();
    }

    private byte[] testPDFBox3826createDoc(TrueTypeFont ttf) throws IOException
    {
        PDDocument doc = new PDDocument();

        PDPage page = new PDPage();
        doc.addPage(page);

        // type 0 subset embedding
        PDFont font = PDType0Font.load(doc, ttf, true);
        PDPageContentStream cs = new PDPageContentStream(doc, page);

        cs.beginText();
        cs.newLineAtOffset(10, 700);
        cs.setFont(font, 10);
        cs.showText("testMultipleFontFileReuse1");
        cs.endText();

        // type 0 full embedding
        font = PDType0Font.load(doc, ttf, false);

        cs.beginText();
        cs.newLineAtOffset(10, 650);
        cs.setFont(font, 10);
        cs.showText("testMultipleFontFileReuse2");
        cs.endText();

        // tt full embedding but only WinAnsiEncoding
        font = PDTrueTypeFont.load(doc, ttf, WinAnsiEncoding.INSTANCE);

        cs.beginText();
        cs.newLineAtOffset(10, 600);
        cs.setFont(font, 10);
        cs.showText("testMultipleFontFileReuse3");
        cs.endText();

        cs.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        doc.save(baos);
        doc.close();
        return baos.toByteArray();
    }

    /**
     * Check that font can be deleted after usage.
     *
     * @throws IOException
     */
    @Test
    public void testDeleteFont() throws IOException
    {
        File tempFontFile = new File(OUT_DIR, "LiberationSans-Regular.ttf");
        File tempPdfFile = new File(OUT_DIR, "testDeleteFont.pdf");
        String text = "Test PDFBOX-4823";

        InputStream is = testContext.getAssets().open("com/tom_roush/pdfbox/resources/ttf/LiberationSans-Regular.ttf");

        OutputStream os = new FileOutputStream(tempFontFile);
        IOUtils.copy(is, os);
        is.close();
        os.close();

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        PDFont font1 = PDType0Font.load(doc, tempFontFile);
        cs.beginText();
        cs.setFont(font1, 50);
        cs.newLineAtOffset(50, 700);
        cs.showText(text);
        cs.endText();
        cs.close();
        doc.save(tempPdfFile);
        doc.close();

        Assert.assertTrue(tempFontFile.delete());

        doc = PDDocument.load(tempPdfFile);
        PDFTextStripper stripper = new PDFTextStripper();
        String extractedText = stripper.getText(doc);
        Assert.assertEquals(text, extractedText.trim());
        doc.close();

        Assert.assertTrue(tempPdfFile.delete());
    }

    /**
     * PDFBOX-5115: U+00AD (soft hyphen) should work with WinAnsiEncoding.
     */
    @Test
    public void testSoftHyphen() throws IOException
    {
        String text = "- \u00AD";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        PDFont font1 = PDType1Font.HELVETICA;
        PDFont font2 = PDType0Font.load(doc, testContext.getAssets().open(
            "com/tom_roush/pdfbox/resources/ttf/LiberationSans-Regular.ttf"));

        Assert.assertEquals(font1.getStringWidth("-"), font1.getStringWidth("\u00AD"), 0);
        Assert.assertEquals(font2.getStringWidth("-"), font2.getStringWidth("\u00AD"), 0);

        PDPageContentStream cs = new PDPageContentStream(doc, page);
        cs.beginText();
        cs.newLineAtOffset(100, 500);
        cs.setFont(font1, 10);
        cs.showText(text);
        cs.newLineAtOffset(0, 100);
        cs.setFont(font2, 10);
        cs.showText(text);
        cs.endText();
        cs.close();
        doc.save(baos);
        doc.close();

        doc = PDDocument.load(baos.toByteArray());
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setLineSeparator("\n");
        String extractedText = stripper.getText(doc);
        Assert.assertEquals(text + "\n" + text, extractedText.trim());
        doc.close();
    }
}
