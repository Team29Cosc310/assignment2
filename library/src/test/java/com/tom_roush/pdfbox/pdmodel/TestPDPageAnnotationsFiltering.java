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
package com.tom_roush.pdfbox.pdmodel;

import java.io.IOException;
import java.util.List;

import com.tom_roush.pdfbox.cos.COSArray;
import com.tom_roush.pdfbox.cos.COSDictionary;
import com.tom_roush.pdfbox.cos.COSName;
import com.tom_roush.pdfbox.pdmodel.interactive.annotation.AnnotationFilter;
import com.tom_roush.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import com.tom_roush.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import com.tom_roush.pdfbox.pdmodel.interactive.annotation.PDAnnotationRubberStamp;
import com.tom_roush.pdfbox.pdmodel.interactive.annotation.PDAnnotationSquareCircle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This unit test validates the correct working behavior of PDPage annotations
 * filtering
 *
 * @author <a href="mailto:maxime.veron.pro@gmail.com">Maxime Veron</a>
 *
 */
public class TestPDPageAnnotationsFiltering
{
    // test mock page for annotations filtering
    private PDPage page;

    @Before
    public void initMock()
    {
        COSDictionary mockedPageWithAnnotations = new COSDictionary();
        COSArray annotsDictionary = new COSArray();
        annotsDictionary.add(new PDAnnotationRubberStamp().getCOSObject());
        annotsDictionary.add(new PDAnnotationSquareCircle(PDAnnotationSquareCircle.SUB_TYPE_SQUARE).getCOSObject());
        annotsDictionary.add(new PDAnnotationLink().getCOSObject());
        mockedPageWithAnnotations.setItem(COSName.ANNOTS, annotsDictionary);
        page = new PDPage(mockedPageWithAnnotations);
    }

    @Test
    public void validateNoFiltering() throws IOException
    {
        List<PDAnnotation> annotations = page.getAnnotations();
        Assert.assertEquals(3, annotations.size());
        Assert.assertTrue(annotations.get(0) instanceof PDAnnotationRubberStamp);
        Assert.assertTrue(annotations.get(1) instanceof PDAnnotationSquareCircle);
        Assert.assertTrue(annotations.get(2) instanceof PDAnnotationLink);
    }

    @Test
    public void validateAllFiltered() throws IOException
    {
        List<PDAnnotation> annotations = page.getAnnotations(new AnnotationFilter()
        {
            @Override
            public boolean accept(PDAnnotation annotation)
            {
                return false;
            }
        });
        Assert.assertEquals(0, annotations.size());
    }

    @Test
    public void validateSelectedFew() throws IOException
    {
        List<PDAnnotation> annotations = page.getAnnotations(new AnnotationFilter()
        {
            @Override
            public boolean accept(PDAnnotation annotation)
            {
                return (annotation instanceof PDAnnotationLink || annotation instanceof PDAnnotationSquareCircle);
            }
        });
        Assert.assertEquals(2, annotations.size());
        Assert.assertTrue(annotations.get(0) instanceof PDAnnotationSquareCircle);
        Assert.assertTrue(annotations.get(1) instanceof PDAnnotationLink);
    }
}