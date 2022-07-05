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
public class IncorrectAccessControlException extends Exception {

    public IncorrectAccessControlException() {
        super("Unkown error occurred");
    }

    public IncorrectAccessControlException(String message) {
        super(message);
    }

}
