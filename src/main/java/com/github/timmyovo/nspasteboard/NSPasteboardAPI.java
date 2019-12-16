package com.github.timmyovo.nspasteboard;

public class NSPasteboardAPI {
    public static native void clearContent();
    public static native String getClipboardContent(int type);
    public static native void writeClipboardString(String string);
    public static native void writeClipboardFileURL(String string);
}
