package com.charlie.resource;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Locale;

import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

import org.apache.commons.lang.StringUtils;

import com.charlie.resource.util.DateUtil;

public class StripesTimestampTypeConverter implements TypeConverter<Timestamp>{

	public Timestamp convert(String input, Class<? extends Timestamp> targetType,
			Collection<ValidationError> errors) {
		
		if(StringUtils.isEmpty(input)) return null;
		
		try {
			
			return DateUtil.parse(input, AppConstants.ALTERNATIVE_DATETIME_PATTERN);
			
			
		} catch (Exception ex) {
			return null;
		}
	}

	public void setLocale(Locale locale) {
		//this.setLocale(locale);
	}
}
