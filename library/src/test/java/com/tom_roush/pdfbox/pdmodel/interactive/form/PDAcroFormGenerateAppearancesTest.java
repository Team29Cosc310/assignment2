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

package com.tom_roush.pdfbox.pdmodel.interactive.form;

import java.io.IOException;
import java.net.URL;

import com.tom_roush.pdfbox.io.IOUtils;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDDocumentCatalog;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class PDAcroFormGenerateAppearancesTest {

   /**
    * PDFBOX-5041 Missing font descriptor
    *
    * @throws IOException
    */
   @Test
   public void test5041MissingFontDescriptor() throws IOException
   {

      String sourceUrl = "https://issues.apache.org/jira/secure/attachment/13016941/REDHAT-1301016-0.pdf";

      PDDocument testPdf = null;
      try
      {
         testPdf = PDDocument.load(new URL(sourceUrl).openStream());
         PDDocumentCatalog catalog = testPdf.getDocumentCatalog();
         boolean thrown = false;
         try
         {
            catalog.getAcroForm();
         }
         catch (Exception e)
         {
            thrown = true;
         }
         assertFalse("There shall be no exception when getting the AcroForm", thrown);
      }
      finally
      {
         IOUtils.closeQuietly(testPdf);
      }
   }

   /**
    * PDFBOX-4086 Character missing for encoding
    * @throws IOException
    */
   @Test
   public void test4086CharNotEncodable() throws IOException
   {

      String sourceUrl = "https://issues.apache.org/jira/secure/attachment/12908175/AML1.PDF";

      PDDocument testPdf = null;
      try
      {
         testPdf = PDDocument.load(new URL(sourceUrl).openStream());
         PDDocumentCatalog catalog = testPdf.getDocumentCatalog();
         boolean thrown = false;
         try
         {
            catalog.getAcroForm();
         }
         catch (Exception e)
         {
            thrown = true;
         }
         assertFalse("There shall be no exception when getting the AcroForm", thrown);
      }
      finally
      {
         IOUtils.closeQuietly(testPdf);
      }
   }

   /**
    * PDFBOX-5043 PaperMetaData
    * @throws IOException
    */
   @Test
   public void test5043PaperMetaData() throws IOException
   {
      String sourceUrl = "https://issues.apache.org/jira/secure/attachment/13016992/PDFBOX-3891-5.pdf";

      PDDocument testPdf = null;
      try
      {
         testPdf = PDDocument.load(new URL(sourceUrl).openStream());
         PDDocumentCatalog catalog = testPdf.getDocumentCatalog();
         boolean thrown = false;
         try
         {
            catalog.getAcroForm();
         }
         catch (Exception e)
         {
            thrown = true;
         }
         assertFalse("There shall be no exception when getting the AcroForm", thrown);
      }
      finally
      {
         IOUtils.closeQuietly(testPdf);
      }
   }

}
