//----------------------------------------------------------
//  File Name
//  	desc
//  
// Author: Alex Hinds
// Date: 
// Issues: 
//  
// Description: 
//
// Assumptions:
//  
//  
//
//-----------------------------------------------------------  
package org.ahinds.moviegame.themoviegame;

import java.util.Date;

/* PlayerProfile.java (NOT USED IN CURRENT IMPLEMENTATION)
 * 
 * 
 * Player Account Info, store customizations and other global player info
 */
public class PlayerProfile {
	String id;
	String iconPath;
	Date dateCreated;
	Date lastLogin;
	Boolean defaultMenuSettings;
	MovieGenre favoriteGenre;
	String favoriteGenreMovie; // favorite movie from selected genre - e.g. their favorite genre is fantasy, and their favorite movie is harry potter
	String[] mostRecentGames; // game ids?
}