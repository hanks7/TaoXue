package com.taoxue.utils.permission;

public interface PermissionResult {
    void onGranted();

    void onDenied();
    void onNext();
}
