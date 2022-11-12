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
package com.tom_roush.pdfbox.encryption;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;

import javax.crypto.Cipher;

import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.io.MemoryUsageSetting;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.encryption.AccessPermission;
import com.tom_roush.pdfbox.pdmodel.encryption.PublicKeyProtectionPolicy;
import com.tom_roush.pdfbox.pdmodel.encryption.PublicKeyRecipient;
import com.tom_roush.pdfbox.text.PDFTextStripper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.fail;

/**
 * Tests for public key encryption. These tests are not perfect - to be sure, encrypt a file by
 * using a certificate exported from your digital id in Adobe Reader, and then open that file with
 * Adobe Reader. Do this with every key length.
 *
 * @author Ben Litchfield
 */
@RunWith(Parameterized.class)
public class TestPublicKeyEncryption
{
    private File testResultsDir;

    private AccessPermission permission1;
    private AccessPermission permission2;

    private PublicKeyRecipient recipient1;
    private PublicKeyRecipient recipient2;

    private String keyStore1;
    private String keyStore2;

    private String password1;
    private String password2;

    private Context testContext;
    private final String path = "pdfbox/com/tom_roush/pdfbox/pdmodel/encryption/";

    /**
     * Simple test document that gets encrypted by the test cases.
     */
    private PDDocument document;

    private String text;
    private String producer;

    @Parameterized.Parameter
    public int keyLength;

    /**
     * Values for keyLength test parameter.
     *
     * @return
     */
    @Parameterized.Parameters
    public static Collection keyLengths()
    {
        return Arrays.asList(40, 128, 256);
    }

    public TestPublicKeyEncryption()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Before
    public void setUp() throws Exception
    {
        if (Cipher.getMaxAllowedKeyLength("AES") != Integer.MAX_VALUE)
        {
            // we need strong encryption for these tests
            fail("JCE unlimited strength jurisdiction policy files are not installed");
        }

        testContext = InstrumentationRegistry.getInstrumentation().getContext();
        PDFBoxResourceLoader.init(testContext);
        testResultsDir = new File(testContext.getCacheDir(), "pdfbox-test-output/crypto");
        testResultsDir.mkdirs();

        permission1 = new AccessPermission();
        permission1.setCanAssembleDocument(false);
        permission1.setCanExtractContent(false);
        permission1.setCanExtractForAccessibility(true);
        permission1.setCanFillInForm(false);
        permission1.setCanModify(false);
        permission1.setCanModifyAnnotations(false);
        permission1.setCanPrint(false);
        permission1.setCanPrintDegraded(false);

        permission2 = new AccessPermission();
        permission2.setCanAssembleDocument(false);
        permission2.setCanExtractContent(false);
        permission2.setCanExtractForAccessibility(true);
        permission2.setCanFillInForm(false);
        permission2.setCanModify(false);
        permission2.setCanModifyAnnotations(false);
        permission2.setCanPrint(true); // it is true now !
        permission2.setCanPrintDegraded(false);

        recipient1 = getRecipient("test1.der", permission1);
        recipient2 = getRecipient("test2.der", permission2);

        password1 = "test1";
        password2 = "test2";

        keyStore1 = "test1.pfx";
        keyStore2 = "test2.pfx";

        document = PDDocument.load(testContext.getAssets().open(path + "test.pdf"));
        text = new PDFTextStripper().getText(document);
        producer = document.getDocumentInformation().getProducer();
        document.setVersion(1.7f);
    }

    /**
     * {@inheritDoc}
     */
    @After
    public void tearDown() throws Exception
    {
        document.close();
    }

    /**
     * Protect a document with certificate 1 and try to open it with
     * certificate 2 and catch the exception.
     *
     * @throws Exception If there is an unexpected error during the test.
     */
    @Test
    public void testProtectionError() throws Exception
    {
        PublicKeyProtectionPolicy policy = new PublicKeyProtectionPolicy();
        policy.addRecipient(recipient1);
        policy.setEncryptionKeyLength(keyLength);
        document.protect(policy);

        PDDocument encryptedDoc = null;
        try
        {
            File file = save("testProtectionError");
            encryptedDoc = reload(file, password2, getKeyStore(keyStore2));
            Assert.assertTrue(encryptedDoc.isEncrypted());
            fail("No exception when using an incorrect decryption key");
        }
        catch (IOException ex)
        {
            String msg = ex.getMessage();
            Assert.assertTrue("not the expected exception: " + msg,
                msg.contains("serial-#: rid 2 vs. cert 3"));
        }
        finally
        {
            if (encryptedDoc != null)
            {
                encryptedDoc.close();
            }
        }
    }


    /**
     * Protect a document with a public certificate and try to open it
     * with the corresponding private certificate.
     *
     * @throws Exception If there is an unexpected error during the test.
     */
    @Test
    public void testProtection() throws Exception
    {
        PublicKeyProtectionPolicy policy = new PublicKeyProtectionPolicy();
        policy.addRecipient(recipient1);
        policy.setEncryptionKeyLength(keyLength);
        document.protect(policy);

        File file = save("testProtection");
        PDDocument encryptedDoc = reload(file, password1, getKeyStore(keyStore1));
        try
        {
            Assert.assertTrue(encryptedDoc.isEncrypted());

            AccessPermission permission = encryptedDoc.getCurrentAccessPermission();
            Assert.assertFalse(permission.canAssembleDocument());
            Assert.assertFalse(permission.canExtractContent());
            Assert.assertTrue(permission.canExtractForAccessibility());
            Assert.assertFalse(permission.canFillInForm());
            Assert.assertFalse(permission.canModify());
            Assert.assertFalse(permission.canModifyAnnotations());
            Assert.assertFalse(permission.canPrint());
            Assert.assertFalse(permission.canPrintDegraded());
        }
        finally
        {
            encryptedDoc.close();
        }
    }


    /**
     * Protect the document for 2 recipients and try to open it.
     *
     * @throws Exception If there is an error during the test.
     */
    @Test
    public void testMultipleRecipients() throws Exception
    {
        PublicKeyProtectionPolicy policy = new PublicKeyProtectionPolicy();
        policy.addRecipient(recipient1);
        policy.addRecipient(recipient2);
        policy.setEncryptionKeyLength(keyLength);
        document.protect(policy);

        // open first time
        File file = save("testMultipleRecipients");
        PDDocument encryptedDoc1 = reload(file, password1, getKeyStore(keyStore1));
        try
        {
            AccessPermission permission = encryptedDoc1.getCurrentAccessPermission();
            Assert.assertFalse(permission.canAssembleDocument());
            Assert.assertFalse(permission.canExtractContent());
            Assert.assertTrue(permission.canExtractForAccessibility());
            Assert.assertFalse(permission.canFillInForm());
            Assert.assertFalse(permission.canModify());
            Assert.assertFalse(permission.canModifyAnnotations());
            Assert.assertFalse(permission.canPrint());
            Assert.assertFalse(permission.canPrintDegraded());
        }
        finally
        {
            encryptedDoc1.close();
        }

        // open second time
        PDDocument encryptedDoc2 = reload(file, password2, getKeyStore(keyStore2));
        try
        {
            AccessPermission permission = encryptedDoc2.getCurrentAccessPermission();
            Assert.assertFalse(permission.canAssembleDocument());
            Assert.assertFalse(permission.canExtractContent());
            Assert.assertTrue(permission.canExtractForAccessibility());
            Assert.assertFalse(permission.canFillInForm());
            Assert.assertFalse(permission.canModify());
            Assert.assertFalse(permission.canModifyAnnotations());
            Assert.assertTrue(permission.canPrint());
            Assert.assertFalse(permission.canPrintDegraded());
        }
        finally
        {
            encryptedDoc2.close();
        }
    }

    /**
     * Reloads the given document from a file and check some contents.
     *
     * @param file input file
     * @param decryptionPassword password to be used to decrypt the doc
     * @param keyStore password to be used to decrypt the doc
     * @return reloaded document
     * @throws Exception if 
     */
    private PDDocument reload(File file, String decryptionPassword, InputStream keyStore)
        throws IOException, NoSuchAlgorithmException
    {
        PDDocument doc2 = PDDocument.load(file, decryptionPassword,
            keyStore, null, MemoryUsageSetting.setupMainMemoryOnly());
        Assert.assertEquals("Extracted text is different",
            text,
            new PDFTextStripper().getText(doc2));
        Assert.assertEquals("Producer is different",
            producer,
            doc2.getDocumentInformation().getProducer());
        return doc2;
    }

    /**
     * Returns a recipient specification with the given access permissions
     * and an X.509 certificate read from the given classpath resource.
     *
     * @param certificate X.509 certificate resource, relative to this class
     * @param permission access permissions
     * @return recipient specification
     * @throws Exception if the certificate could not be read
     */
    private PublicKeyRecipient getRecipient(String certificate, AccessPermission permission) throws Exception
    {
        InputStream input = testContext.getAssets().open(path + certificate);
        try
        {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            PublicKeyRecipient recipient = new PublicKeyRecipient();
            recipient.setPermission(permission);
            recipient.setX509((X509Certificate) factory.generateCertificate(input));
            return recipient;
        }
        finally
        {
            input.close();
        }
    }

    private InputStream getKeyStore(String name) throws IOException
    {
        return testContext.getAssets().open(path + name);
    }

    private File save(String name) throws IOException
    {
        File file = new File(testResultsDir, name + "-" + keyLength + "bit.pdf");
        document.save(file);
        return file;
    }

    @Test
    public void testReadPubkeyEncryptedAES128() throws IOException
    {
        InputStream is = testContext.getAssets().open(path + "AESkeylength128.pdf");
        PDDocument doc = PDDocument.load(is,
            "w!z%C*F-JaNdRgUk",
            testContext.getAssets().open(path + "PDFBOX-4421-keystore.pfx"),
            "testnutzer");
        PDFTextStripper stripper = new PDFTextStripper();
        Assert.assertEquals("Key length: 128", stripper.getText(doc).trim());
        is.close();
        doc.close();
    }

    @Test
    public void testReadPubkeyEncryptedAES256() throws IOException
    {
        InputStream is = testContext.getAssets().open(path + "AESkeylength256.pdf");
        PDDocument doc = PDDocument.load(is,
            "w!z%C*F-JaNdRgUk",
            testContext.getAssets().open(path + "PDFBOX-4421-keystore.pfx"),
            "testnutzer");
        PDFTextStripper stripper = new PDFTextStripper();
        Assert.assertEquals("Key length: 256", stripper.getText(doc).trim());
        is.close();
        doc.close();
    }

    /**
     * PDFBOX-5249: Read a file encrypted with AES128 but not with PDFBox, and with exposed
     * Metadata.
     *
     * @throws IOException
     */
    @Test
    public void testReadPubkeyEncryptedAES128withMetadataExposed() throws IOException
    {
        InputStream is = testContext.getAssets().open(path + "AES128ExposedMeta.pdf");
        PDDocument doc = PDDocument.load(is, "",
            testContext.getAssets().open(path + "PDFBOX-5249.p12"), "test",
            MemoryUsageSetting.setupMainMemoryOnly());
        Assert.assertEquals("PublicKeySecurityHandler",
            doc.getEncryption().getSecurityHandler().getClass().getSimpleName());
        Assert.assertEquals(128, doc.getEncryption().getSecurityHandler().getKeyLength());
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setLineSeparator("\n");
        Assert.assertEquals("AES key length: 128\nwith exposed Metadata", stripper.getText(doc).trim());
        doc.close();
        is.close();
    }

    /**
     * PDFBOX-5249: Read a file encrypted with AES128 but not with PDFBox, and with exposed
     * Metadata.
     *
     * @throws IOException
     */
    @Test
    public void testReadPubkeyEncryptedAES256withMetadataExposed() throws IOException
    {
        InputStream is = testContext.getAssets().open(path + "AES256ExposedMeta.pdf");
        PDDocument doc = PDDocument.load(is, "",
            testContext.getAssets().open(path + "PDFBOX-5249.p12"), "test",
            MemoryUsageSetting.setupMainMemoryOnly());
        Assert.assertEquals("PublicKeySecurityHandler",
            doc.getEncryption().getSecurityHandler().getClass().getSimpleName());
        Assert.assertEquals(256, doc.getEncryption().getSecurityHandler().getKeyLength());
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setLineSeparator("\n");
        Assert.assertEquals("AES key length: 256 \nwith exposed Metadata", stripper.getText(doc).trim());
        doc.close();
        is.close();
    }
}
