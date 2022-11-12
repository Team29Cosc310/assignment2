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

package com.tom_roush.pdfbox.rendering;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;

import org.junit.Before;
import org.junit.Test;

/**
 * Functional test for PDF rendering. This test simply tries to render
 * a series of PDFs using PDFBox to make sure that no exceptions are thrown.
 *
 * It does not attempt to detect if rendering is correct, see {@link com.tom_roush.pdfbox.rendering.TestPDFToImage}.
 *
 * @author John Hewson
 */
public class TestRendering
{
    private static final String INPUT_DIR = "pdfbox/input/rendering";
    private static File OUT_DIR;

    Context testContext;

    private boolean findAssetPDFs(String path, ArrayList<String> pdfs)
    {
        try
        {
            String[] list = testContext.getAssets().list(path);
            if (list.length > 0)
            {
                // This is a folder
                for (String file : list)
                {
                    if (!findAssetPDFs(path + "/" + file, pdfs))
                    {
                        return false;
                    }
                }
            }
            else
            {
                // This is a file
                if(path.endsWith(".pdf") || path.endsWith(".ai"))
                {
                    pdfs.add(path);
                }
            }
        }
        catch (IOException e)
        {
            return false;
        }

        return true;
    }

    /**
     * Test suite setup.
     */
    @Before
    public void setUp() throws IOException
    {
        testContext = InstrumentationRegistry.getInstrumentation().getContext();
        PDFBoxResourceLoader.init(testContext);
        OUT_DIR = new File(testContext.getCacheDir(), "pdfbox-test-output/rendering");
        OUT_DIR.mkdirs();
    }

    @Test
    public void testRendering()
    {
        ArrayList<String> testFiles = new ArrayList<>();
        findAssetPDFs(INPUT_DIR, testFiles);
        for (String testFile : testFiles)
        {
            try
            {
                render(testFile);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void render(String fileName) throws IOException
    {
        PDDocument document = PDDocument.load(testContext.getAssets().open(fileName));
        render(document, fileName.substring(fileName.lastIndexOf("/") + 1));
    }

    public void render(File file) throws IOException
    {
        PDDocument document = PDDocument.load(file);
        render(document, file.getName());
    }

    private void render(PDDocument document, String name) throws IOException
    {
        PDFRenderer renderer = new PDFRenderer(document);
        for (int i = 0; i < document.getNumberOfPages(); i++)
        {
            Bitmap image = renderer.renderImage(i);

            File renderFile = new File(OUT_DIR, name + "-" + (i + 1) + ".png");
            FileOutputStream fileOut = new FileOutputStream(renderFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fileOut);
            fileOut.close();
        }

        // We don't actually do anything with the image for the same reason that
        // TestPDFToImage is disabled - different JVMs produce different results
        // but at least we can make sure that PDFBox did not throw any exceptions
        // during the rendering process.

        document.close();
        Log.e("PdfBox-Android", "Rendered " + name + " without dying");
    }
}
