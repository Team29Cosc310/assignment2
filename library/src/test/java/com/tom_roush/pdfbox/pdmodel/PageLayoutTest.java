/*
 * Copyright 2016 The Apache Software Foundation.
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
package com.tom_roush.pdfbox.pdmodel;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class PageLayoutTest
{
    /**
     * @author Tilman Hausherr
     *
     * Test for completeness (PDFBOX-3362).
     */
    @Test
    public void testValues()
    {
        Set<PageLayout> pageLayoutSet = EnumSet.noneOf(PageLayout.class);
        Set<String> stringSet = new HashSet<String>();
        for (PageLayout pl : PageLayout.values())
        {
            String s = pl.stringValue();
            stringSet.add(s);
            pageLayoutSet.add(PageLayout.fromString(s));
        }
        assertEquals(PageLayout.values().length, pageLayoutSet.size());
        assertEquals(PageLayout.values().length, stringSet.size());
    }

    /**
     * @author John Bergqvist
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fromStringInputNotNullOutputIllegalArgumentException()
    {
        // Arrange
        final String value = "SinglePag";

        // Act
        thrown.expect(IllegalArgumentException.class);
        PageLayout.fromString(value);

        // Method is not expected to return due to exception thrown
    }
}
