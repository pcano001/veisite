package com.veisite.vegecom.model;

import com.veisite.vegecom.bean.Bean;

public abstract class ModelObject extends Bean {
	
	public abstract Object getId();
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ModelObject))
			return false;
		return getId().equals(((ModelObject) obj).getId());
	}
	
}
