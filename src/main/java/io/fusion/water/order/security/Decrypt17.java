/**
 * Copyright (c) 2024 Araf Karsh Hamid
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 * <p>
 * or (per the licensee's choosing)
 * <p>
 * under the terms of the Apache 2 License version 2.0
 * as published by the Apache Software Foundation.
 */
package io.fusion.water.order.security;
// Jasypt
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
// Java
import static java.lang.System.out;

/**
 * ms-test-quickstart / Decrypt17 
 *
 * @author: Araf Karsh Hamid
 * @version: 0.1
 * @date: 2024-12-15T08:29
 */
public class Decrypt17 {

    public static void main(String[] args) {
        out.println("Text Decryptor using Jasypt Encryption Library (v1.9.3)");
        out.println("-------------------------------------------------------");
        doDecryptionAES(args);
    }

    private static void doDecryptionAES(String[] args) {
        if(!validateInputs(args)) {
            return;
        }
        var testToDecrypt = args[0];    // Input text to Decrypt
        var masterPassword = args[1];  // Master password for Decryption

        // Create and configure the decryptor
        var decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(masterPassword);

        // Use an AES-based PBE algorithm.
        // Note: 'PBEWithHmacSHA512AndAES_256' is a common AES-based PBE algorithm supported by Jasypt.
        String algo = "PBEWithHmacSHA512AndAES_256";
        decryptor.setAlgorithm(algo);
        out.println("Algorithm Used : "+algo);

        // Add Randomness to the Decryption
        decryptor.setIvGenerator(new RandomIvGenerator());
        decryptor.setSaltGenerator(new RandomSaltGenerator());

        var decryptedText = decryptor.decrypt(testToDecrypt);
        out.println("Decrypted Text : "+ decryptedText);
        out.println("-------------------------------------------------------");
    }

    private static boolean validateInputs(String[] args) {
        if (args.length != 2) {
            // println("Usage: java io.fusion.water.order.security.Encrypt17 <password_to_encrypt> <encryption_key>");
            out.println("Usage: source decrypt encrypted_text encryption_key");
            return false;
        }
        return true;
    }
}
