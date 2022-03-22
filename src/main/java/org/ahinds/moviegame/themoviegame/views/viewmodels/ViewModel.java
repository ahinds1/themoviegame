package org.ahinds.moviegame.themoviegame.views.viewmodels;

/* ViewModel.java
 * 
 * View Model contract, interface for AbstractViewModel.
 * 
 * NOTE: Not all view models have an implementation for each method (but they should / will);
 * 
 */
public interface ViewModel<T> {
	void init(T model);
	void update(T model);
	void updateModelRefs(T model);
	void rollback();
	void setModel(T model);
	void reloadFromModel();
	void save();
}
