/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.business;

/**
 * An existing file was not overwritten
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
public class SkippedException extends Exception {

    SkippedException(String message) {
        super(message);
    }
    
}
