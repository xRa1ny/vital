package me.xra1ny.vital.core;

import lombok.NonNull;
import lombok.extern.java.Log;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

@Log
public class VitalUncaughtExceptionHandler extends Handler {
    private final VitalExceptionsHandlerManager vitalExceptionsHandlerManager;

    public VitalUncaughtExceptionHandler(@NonNull VitalExceptionsHandlerManager vitalExceptionsHandlerManager) {
        this.vitalExceptionsHandlerManager = vitalExceptionsHandlerManager;
    }

    @Override
    public void publish(LogRecord record) {
        if(record.getThrown() == null) {
            return;
        }

        for(VitalExceptionsHandler vitalExceptionsHandler : vitalExceptionsHandlerManager.getVitalComponentList()) {
            vitalExceptionsHandler.executeAnnotatedMethodsByException(record.getThrown());
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
