package com.github.timmyovo;

import com.github.timmyovo.nspasteboard.NSPasteboardAPI;
import com.intellij.ide.PsiCopyPasteManager;
import com.intellij.openapi.ide.CopyPasteManager;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CopyPasteListener implements CopyPasteManager.ContentChangedListener {
    @Override
    public void contentChanged(@Nullable Transferable oldTransferable, Transferable newTransferable) {
        if (!(newTransferable instanceof PsiCopyPasteManager.MyTransferable)) {
            return;
        }
        try {
            NSPasteboardAPI.writeClipboardFilesURL(
                    ((ArrayList<File>) newTransferable
                            .getTransferData(DataFlavor.javaFileListFlavor))
                            .stream()
                            .map(File::getAbsolutePath)
                            .toArray(String[]::new)
            );
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }
}
