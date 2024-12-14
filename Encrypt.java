import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.ZeroSaltGenerator;

void main(String... args) {
	println("Text Encryptor using Jasypt Encryption Library (v1.9.3)");
    println("-------------------------------------------------------");
    var argsLength = args.length;
    if (argsLength != 2) {
        // println("Usage: java --enable-preview Encryptor.java <password_to_encrypt> <encryption_key>");
        println("Usage: source encrypt password_to_encrypt encryption_key");
        System.exit(1);
    }

    var textToEncrypt = args[0];    // Input text to encrypt
    var masterPassword = args[1];  // Master password for encryption

    // Create and configure the encryptor
    var encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword(masterPassword);
    encryptor.setAlgorithm("PBEWithMD5AndDES");
    encryptor.setSaltGenerator(new ZeroSaltGenerator()); // Fixed salt for consistent output

    println("Algorithm Used : PBEWithMD5AndDES");
    // Encrypt the text
    var encryptedText = encryptor.encrypt(textToEncrypt);
    println("Text to Encrypt: "+ textToEncrypt);
    println("Encrypted Text : "+ encryptedText);
    // Decrypt the text
    var decryptedText = encryptor.decrypt(encryptedText);
    println("Decrypted Text : "+ decryptedText);
    println("-------------------------------------------------------");
}
