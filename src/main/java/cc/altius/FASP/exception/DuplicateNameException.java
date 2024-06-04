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
public class DuplicateNameException extends RuntimeException {

    public DuplicateNameException() {
        super("Duplicate value provided");
    }

    public DuplicateNameException(String message) {
        super(message);
    }

}
