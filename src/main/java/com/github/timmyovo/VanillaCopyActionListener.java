package com.github.timmyovo;

import com.github.timmyovo.nspasteboard.NSPasteboardAPI;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;

public class VanillaCopyActionListener extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        NSPasteboardAPI.writeClipboardFileURL(e.getDataContext().getData(PlatformDataKeys.VIRTUAL_FILE).getPath());
    }
}
