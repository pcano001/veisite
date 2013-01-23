package com.veisite.vegecom.ui.components.table;

public interface DataLoadListener {
	
	public void dataLoadInit();
	
	public void dataLoadEnd();

	public void dataLoadError(Throwable exception);

}
