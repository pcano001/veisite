package com.veisite.vegecom.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.veisite.vegecom.model.Articulo;

public class TestValidation {

	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void testArticuloValidation() {
		Articulo articulo = new Articulo();
		
		Set<ConstraintViolation<Articulo>> constraintViolations =
				validator.validate(articulo);
		for (ConstraintViolation<Articulo> c : constraintViolations) {
			System.out.println(c.getMessage()+", "+
						c.getMessageTemplate()+", "+c.getPropertyPath());
		}
	}

}
