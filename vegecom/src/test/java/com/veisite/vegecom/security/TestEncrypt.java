package com.veisite.vegecom.security;

import org.junit.Test;


public class TestEncrypt {
	
	@Test
	public void testEncrypt() throws Exception {
		DesEncrypter encrypter = new DesEncrypter("d5VdfGh78pcyds3BpcyBtbvgeSBwYXreNzd29yZC4dXNlcm5hreebcerGFz43vcwa");

	    String encrypted = encrypter.encrypt("cvilladesa");

	    String decrypted = encrypter.decrypt(encrypted);
	    
	    System.out.println(encrypted);
	    System.out.println(decrypted);
	}
	
}
