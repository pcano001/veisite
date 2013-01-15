package com.veisite.vegecom.binding;

public interface Converter<ST, DT> {
	public DT convert(ST value);
}
