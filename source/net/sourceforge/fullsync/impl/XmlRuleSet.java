package net.sourceforge.fullsync.impl;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;



/**
 * @author <a href="mailto:codewright@gmx.net">Jan Kopcsek</a>
 */
public class XmlRuleSet extends AbstractRuleSet
{
    public XmlRuleSet()
    {
        
    }

    public void processRules( InputStream in, String filename ) 
    {
        XmlRulesFile file = null;
        try {
            file = XmlRulesFile.getXmlRulesFile( in );
        } catch( IntrospectionException e ) {
            e.printStackTrace();
        } catch( IOException e ) {
            e.printStackTrace();
        } catch( SAXException e ) {
            e.printStackTrace();
        }
        XmlRuleSet ruleSet = file.getRuleSet( this.getName() );
    }
}