package net.sourceforge.fullsync.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sourceforge.fullsync.ExceptionHandler;
import net.sourceforge.fullsync.buffer.EntryDescriptor;
import net.sourceforge.fullsync.fs.File;


/**
 * @author <a href="mailto:codewright@gmx.net">Jan Kopcsek</a>
 */
public class DirCreationEntryDescriptor implements EntryDescriptor
{
    private Object reference;
    private File dst;
    
    public DirCreationEntryDescriptor( Object reference, File dst )
    {
        this.reference = reference;
        this.dst = dst;
        if( dst == null )
            throw new RuntimeException( "can't give me null !!" );
    }
    public Object getReferenceObject()
    {
        return reference;
    }
    public long getLength()
    {
        return 0;
    }
    public InputStream getInputStream()
    	throws IOException
    {
        return null;
    }
    public OutputStream getOutputStream()
    	throws IOException
    {
        return null;
    }
    public void finishWrite()
    {
        try {
            dst.makeDirectory();
            dst.refresh();
            
        } catch( IOException e ) {
            ExceptionHandler.reportException( e );
        }
    }
    public void finishStore()
    {
        
    }
    public String getOperationDescription()
    {
        return "Making Directory "+dst.getPath();
    }
}
