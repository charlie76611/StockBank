package com.charlie.resource;

import java.sql.Timestamp;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.validation.DefaultTypeConverterFactory;

public class StripesConverterFactory extends DefaultTypeConverterFactory {
	public void init(Configuration configuration) {
		
		
		super.init(configuration);
		
		// add by jini
		add(Timestamp.class, StripesTimestampTypeConverter.class);
	}
}
