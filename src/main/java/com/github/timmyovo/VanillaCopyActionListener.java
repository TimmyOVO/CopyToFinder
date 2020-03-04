package com.github.timmyovo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class VanillaCopyActionListener extends AnAction {

    /**
     * @deprecated
     * old code
     * @param e
     */
    @Deprecated
    @Override
    public void actionPerformed(AnActionEvent e) {
        //NSPasteboardAPI.writeClipboardFileURL(e.getDataContext().getData(PlatformDataKeys.VIRTUAL_FILE).getPath());
    }
}
