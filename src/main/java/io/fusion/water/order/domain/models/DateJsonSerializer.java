package io.fusion.water.order.domain.models;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DateJsonSerializer extends StdSerializer<LocalDateTime>{

	public DateJsonSerializer() {
		this(null, false);
	}
	protected DateJsonSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7035242849275956037L;

	@Override
	public void serialize(LocalDateTime value, 
			JsonGenerator gen, 
			SerializerProvider provider) throws IOException {
		gen.writeString(value.toString());		
	}

}
