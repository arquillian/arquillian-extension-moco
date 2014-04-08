package org.jboss.arquillian.moco.container;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.moco.ReflectionHelper;
import org.jboss.arquillian.moco.api.MocoServerResource;
import org.jboss.arquillian.test.spi.TestEnricher;

public class MocoTestEnricher implements TestEnricher {

	@Inject 
	private Instance<MocoLocation> mocoLocationInstance;
	
	@Override
	public void enrich(Object testCase) {
		
		List<Field> mockFields = ReflectionHelper.getFieldsWithAnnotation(
				testCase.getClass(), MocoServerResource.class);

		for (Field mockField : mockFields) {
			try {
				Object mocked = mocoLocationInstance.get().getMocoUrl();
				if (!mockField.isAccessible()) {
					mockField.setAccessible(true);
				}
				mockField.set(testCase, mocked);
			} catch (Exception e) {
				throw new RuntimeException(
						"Could not inject mocked object on field " + mockField,
						e);
			}
		}

	}

	@Override
	public Object[] resolve(Method method) {
		return new Object[method.getParameterTypes().length];
	}

}
