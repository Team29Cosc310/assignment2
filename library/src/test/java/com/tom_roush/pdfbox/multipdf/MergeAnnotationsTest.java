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
package com.tom_roush.pdfbox.multipdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tom_roush.pdfbox.cos.COSName;
import com.tom_roush.pdfbox.io.IOUtils;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDDocumentNameDestinationDictionary;
import com.tom_roush.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * Test merging different PDFs with Annotations.
 */
public class MergeAnnotationsTest
{
    private static final File OUT_DIR = new File("target/test-output/merge/");
    private static final File TARGET_PDF_DIR = new File("target/pdfs");

    @Before
    public void setUp()
    {
        OUT_DIR.mkdirs();
    }

    /*
     * PDFBOX-1065 Ensure that after merging the PDFs there are all link
     * annotations and they point to the correct page.
     */
    @Test
    public void testLinkAnnotations() throws IOException {

        // Merge the PDFs from PDFBOX-1065
        PDFMergerUtility merger = new PDFMergerUtility();
        InputStream is1 = null;
        InputStream is2 = null;

        try {
            File file1 = new File(TARGET_PDF_DIR, "PDFBOX-1065-1.pdf");
            assumeTrue(file1.exists());
            is1 = new FileInputStream(file1);

            File file2 = new File(TARGET_PDF_DIR, "PDFBOX-1065-2.pdf");
            assumeTrue(file2.exists());
            is2 = new FileInputStream(file2);
            File pdfOutput = new File(OUT_DIR, "PDFBOX-1065.pdf");
            merger.setDestinationFileName(pdfOutput.getAbsolutePath());
            merger.addSource(is1);
            merger.addSource(is2);
            merger.mergeDocuments(null);

            // Test merge result
            PDDocument mergedPDF = PDDocument.load(pdfOutput);
            assertEquals("There shall be 6 pages", 6, mergedPDF.getNumberOfPages());

            PDDocumentNameDestinationDictionary destinations = mergedPDF.getDocumentCatalog().getDests();

            // Each document has 3 annotations with 2 entries in the /Dests dictionary per annotation. One for the
            // source and one for the target.
            assertEquals("There shall be 12 entries", 12, destinations.getCOSObject().entrySet().size());

            List<PDAnnotation> sourceAnnotations01 = mergedPDF.getPage(0).getAnnotations();
            List<PDAnnotation> sourceAnnotations02 = mergedPDF.getPage(3).getAnnotations();

            List<PDAnnotation> targetAnnotations01 = mergedPDF.getPage(2).getAnnotations();
            List<PDAnnotation> targetAnnotations02 = mergedPDF.getPage(5).getAnnotations();

            // Test for the first set of annotations to be merged an linked correctly
            assertEquals("There shall be 3 source annotations at the first page", 3, sourceAnnotations01.size());
            assertEquals("There shall be 3 source annotations at the third page", 3, targetAnnotations01.size());
            assertTrue("The annotations shall match to each other", testAnnotationsMatch(sourceAnnotations01, targetAnnotations01));

            // Test for the second set of annotations to be merged an linked correctly
            assertEquals("There shall be 3 source annotations at the first page", 3, sourceAnnotations02.size());
            assertEquals("There shall be 3 source annotations at the third page", 3, targetAnnotations02.size());
            assertTrue("The annotations shall match to each other", testAnnotationsMatch(sourceAnnotations02, targetAnnotations02));
            mergedPDF.close();
        } finally {
            IOUtils.closeQuietly(is1);
            IOUtils.closeQuietly(is2);
        }
    }

    /*
     * Source and target annotations are línked by name with the target annotation's name
     * being the source annotation's name prepended with 'annoRef_'
     */
    private boolean testAnnotationsMatch(List<PDAnnotation> sourceAnnots, List<PDAnnotation> targetAnnots)
    {
        Map<String, PDAnnotation> targetAnnotsByName = new HashMap<String, PDAnnotation>();
        COSName destinationName;

        // fill the map with the annotations destination name
        for (PDAnnotation targetAnnot : targetAnnots)
        {
            destinationName = (COSName) targetAnnot.getCOSObject().getDictionaryObject(COSName.DEST);
            targetAnnotsByName.put(destinationName.getName(), targetAnnot);
        }

        // try to lookup the target annotation for the source annotation by destination name
        for (PDAnnotation sourceAnnot : sourceAnnots)
        {
            destinationName = (COSName) sourceAnnot.getCOSObject().getDictionaryObject(COSName.DEST);
            if (targetAnnotsByName.get("annoRef_" + destinationName.getName()) == null)
            {
                return false;
            }
        }
        return true;
    }
}
