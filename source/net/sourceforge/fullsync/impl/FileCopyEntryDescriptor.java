package net.sourceforge.fullsync.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sourceforge.fullsync.buffer.EntryDescriptor;
import net.sourceforge.fullsync.fs.File;


/**
 * @author <a href="mailto:codewright@gmx.net">Jan Kopcsek</a>
 */
public class FileCopyEntryDescriptor implements EntryDescriptor
{
    private File src;
    private File dst;
    
    public FileCopyEntryDescriptor( File src, File dst )
    {
        this.src = src;
        this.dst = dst;
    }
    
    public long getLength()
    {
        return src.getFileAttributes().getLength();
    }
    public InputStream getInputStream()
    	throws IOException
    {
        return src.getInputStream();
    }
    public OutputStream getOutputStream()
    	throws IOException
    {
        return dst.getOutputStream();
    }
    public void finishWrite()
    {
        dst.setFileAttributes( src.getFileAttributes() );
        dst.refresh();
    }
    public void finishStore()
    {
        
    }
    public String getOperationDescription()
    {
        return "Copied "+src.getPath()+" to "+dst.getPath(); 
    }
}