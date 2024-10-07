/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.exception;

/**
 *
 * @author akil
 */
public class AccessControlFailedException extends Exception {

    public AccessControlFailedException() {
        super("Unkown error occurred");
    }

    public AccessControlFailedException(String message) {
        super(message);
    }

}
