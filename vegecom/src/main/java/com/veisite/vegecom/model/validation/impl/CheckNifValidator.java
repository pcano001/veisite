package com.veisite.vegecom.model.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.veisite.vegecom.model.validation.CheckNif;

public class CheckNifValidator implements ConstraintValidator<CheckNif, String> {
	
	/**
	 * Expresion regular de forato de Nif español válido
	 */
	private static final String pattern_es = "[0-9A-Z]{1}[0-9]{7}[0-9A-Z]{1}";
	/**
	 * Secuencia de caracteres de control para la letra del NIF de persona fisica. 
	 * a partir de DNI,NIE
	 */
	private static final String letraDni = "TRWAGMYFPDXBNJZSQVHLCKE";

	@Override
	public void initialize(CheckNif constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value==null || value.isEmpty()) return true;
		String v = value.toUpperCase();
		if (!v.matches(pattern_es)) return false;
		String c1 = v.substring(0, 1); 
		if (c1.matches("[0-9XYZ]{1}")) {
			// Es un NIF de persona física a partir de DNI o NIE. Calcular letra de control
			String c8 = v.substring(0, 8);
			if (c1.matches("XYZ")) {
				c8.replace('X', '0');
				c8.replace('Y', '1');
				c8.replace('Z', '2');
			}
			char letra = letraDni.charAt(Integer.parseInt(c8) % 23);
			if (letra!=v.charAt(8)) {
	            context.disableDefaultConstraintViolation();
	            context.buildConstraintViolationWithTemplate("{com.veisite.constraints.CheckNif.IncorrectLetter}")
	            	.addConstraintViolation();
	            return false;
			} else return true;
		}
		return false;
	}
	
	
	/**
	 * Espera una cadena de digitos numericos y devuelve la suma de todos los dígitos
	 * Si encuentra un caracter no numericos lo ignora
	 * 
	 * @param s
	 * @return
	 */
	private int sumaDigitos(String s) {
		if (s==null) return 0;
		int suma=0;
		for (int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if (c>='0' && c<='9') suma += (c-'0');
		}
		return suma;
	}

}
