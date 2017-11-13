package br.les.opus.auth.core.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorFactoryImpl;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringManagedConstraintValidatorFactory implements ConstraintValidatorFactory {
	
	@Autowired
	private ApplicationContext springContext;

	private ConstraintValidatorFactoryImpl defaultValidatorFactory;
	
	public SpringManagedConstraintValidatorFactory() {
		this.defaultValidatorFactory = new ConstraintValidatorFactoryImpl();
	}
	
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {  
		T constraintValidator = null;
		try {
			constraintValidator = (T)springContext.getBean(key);
		} catch (NoSuchBeanDefinitionException e) {
			constraintValidator = this.defaultValidatorFactory.getInstance(key);
		}
        return constraintValidator;  
    }

	@Override
	public void releaseInstance(ConstraintValidator<?, ?> instance) {
		
	}
}
