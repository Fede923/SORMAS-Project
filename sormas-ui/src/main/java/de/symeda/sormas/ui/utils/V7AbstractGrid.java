package de.symeda.sormas.ui.utils;

import de.symeda.sormas.api.BaseCriteria;

public interface V7AbstractGrid<C extends BaseCriteria> {

	public abstract C getCriteria();
	public abstract void setCriteria(C criteria);
	
}
