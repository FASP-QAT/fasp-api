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
public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {
        super("Unkown error occurred");
    }

    public InvalidDataException(String message) {
        super(message);
    }

}
