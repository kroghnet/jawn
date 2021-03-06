/*
Copyright 2009-2014 Igor Polevoy

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License. 
*/
package net.javapla.jawn.core.exceptions;

/**
 * @author Igor Polevoy
 */
public class InitException  extends RuntimeException{
    private static final long serialVersionUID = -8110990105178742073L;

    public InitException() {
        super();
    }

    public InitException(String message) {
        super(message);
    }

    public InitException(String message, Throwable cause) {
        super(message + ": " + cause.getClass() + ":" + cause.getMessage(), cause);
        setStackTrace(cause.getStackTrace());
    }

    public InitException(Throwable cause) {
        super(cause);
        setStackTrace(cause.getStackTrace());
    }
}
