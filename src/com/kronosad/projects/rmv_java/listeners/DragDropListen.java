/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kronosad.projects.rmv_java.listeners;

import com.kronosad.projects.rmv_java.WindowMain;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

/**
 *
 * @author russjr08
 */
public class DragDropListen implements DropTargetListener {
    private WindowMain window;
    
    public DragDropListen(WindowMain window){
        this.window = window;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        window.prepareDrop();
        dtde.acceptDrag(DnDConstants.ACTION_COPY);
        
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {}

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {}

    @Override
    public void dragExit(DropTargetEvent dte) {
        window.revert();
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        dtde.acceptDrop(DnDConstants.ACTION_COPY);
        
        // Get the transfer which can provide the dropped item data
        Transferable transferable = dtde.getTransferable();

        // Get the data formats of the dropped item
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        // Loop through the flavors
        for (DataFlavor flavor : flavors) {

            try {

                // If the drop items are files
                if (flavor.isFlavorJavaFileListType()) {

                    // Get all of the dropped files
                    List files = (List) transferable.getTransferData(flavor);
                    

                    // Loop them through
//                    for (File file : files) {
//
//                        // Print out the file path
//                        System.out.println("File path is '" + file.getPath() + "'.");
//
//                    }
                    for(int i = 0; i < files.size(); i++){
                        
                        File file = (File)files.get(i);
                        System.out.println("Found File at: " + file.getPath());
                        window.receiveDrop(file.getAbsolutePath());
                        
                    }

                }

            } catch (Exception e) {

                // Print out the error stack
                e.printStackTrace();

            }
        }
        
        dtde.dropComplete(true);
        window.revert();

    }
    
}
