package net.sourceforge.fullsync;

import net.sourceforge.fullsync.fs.File;

/**
 * @author <a href="mailto:codewright@gmx.net">Jan Kopcsek</a>
 */
public interface IgnoreDecider
{
    public boolean isNodeIgnored( File node );
}