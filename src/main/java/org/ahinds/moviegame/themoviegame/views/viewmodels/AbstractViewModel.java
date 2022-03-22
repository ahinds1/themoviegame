package org.ahinds.moviegame.themoviegame.views.viewmodels;

/*	AbstractViewModel.java
 * 
 * 	Blueprint for all ViewModels. 
 *  
 *  Only GameViewModel uses prevModel, and implements .rollback() 
 *  to support Player rejections.
 * 	
 * 	GameOptionsBuilderViewModel is the only view model that currently implements .save()
 * 
 * 	FUTURE WORK: 
 * 		- Examine if moving prevModel and other reverting methods should be 
 * 			moved out into a different interface / abstract class
 *   
 */
public abstract class AbstractViewModel<T> implements ViewModel<T> {
	protected T prevModel;
	protected T model;
	
}