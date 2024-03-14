package me.xra1ny.vital;

import lombok.extern.java.Log;
import me.xra1ny.except.annotation.ExceptionHandler;

@Log
public final class VitalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public static void handleException(Exception e) {
        log.severe("");
        log.severe("Vital has detected an uncaught exception!");
        log.severe("Please consider implementing exception handling!");
        log.severe("");
    }
}
