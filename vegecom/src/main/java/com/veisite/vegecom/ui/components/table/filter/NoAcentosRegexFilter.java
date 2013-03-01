package com.veisite.vegecom.ui.components.table.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdesktop.swingx.sort.RowFilters.GeneralFilter;

import com.veisite.vegecom.util.StringUtil;

public class NoAcentosRegexFilter  extends GeneralFilter {

	private Matcher matcher;
	private boolean uppercase;

	public NoAcentosRegexFilter(boolean uppercase, Pattern regex, int... columns) {
		super(columns);
		this.uppercase = uppercase;
		if (regex == null) {
			throw new IllegalArgumentException("Pattern must be non-null");
		}
		matcher = regex.matcher("");
	}

	@Override
	protected boolean include(Entry<? extends Object, ? extends Object> value,
			int index) {
		String c = value.getStringValue(index);
		if (uppercase) c = c.toUpperCase();
		matcher.reset(StringUtil.quitaAcentos(c));
		return matcher.find();
	}

}
