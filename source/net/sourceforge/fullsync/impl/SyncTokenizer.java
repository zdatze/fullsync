package net.sourceforge.fullsync.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

import net.sourceforge.fullsync.DataParseException;

/**
 * @author <a href="mailto:codewright@gmx.net">Jan Kopcsek</a>
 */
public class SyncTokenizer extends StreamTokenizer
{
	public final static int NONE	= 0;
	public final static int	LOCAL	= 1;
	public final static int	REMOTE	= 2;
	public final static int	BOTH	= 3;
	public final static String[] locations = new String[] { "none", "source", "destination", "both" }; 
	
	public final static String[] boolwords = new String[] { "no", "yes", "false", "true" };
	
	
	private String sourceName;
	
	public SyncTokenizer( Reader in )
	{
		super( in );
		
		this.eolIsSignificant( true );
		this.lowerCaseMode( true );
		this.slashSlashComments( true );
		this.slashStarComments( true );
	}
	
	public SyncTokenizer( Reader in, String sourceName )
	{
		this( in );
		setSourceName( sourceName );
	}

	public String nextString() throws DataParseException
	{
		int i;
		try {
			i = nextToken();
		} catch( IOException ioe ) {
			throw new DataParseException( ioe.getMessage(), ioe.getCause(), lineno(), getSourceName() );
		}
		if( ttype == TT_EOF )
			return null;
		if( ttype != '\"' && ttype != TT_WORD )
			throw new DataParseException( "string expected", lineno(), getSourceName() );
		
		return sval;
	}
	public String nextWord() throws DataParseException
	{
		int i;
		try {
			i = nextToken();
		} catch( IOException ioe ) {
			throw new DataParseException( ioe.getMessage(), ioe.getCause(), lineno(), getSourceName() );
		}
		if( ttype == TT_EOF )
			return null;
		if( ttype != TT_WORD )
			throw new DataParseException( "identifier expected", lineno(), getSourceName() );

		return sval;
	}

	public int nextWordValue( String[] values ) throws DataParseException
	{
		String word = nextWord();
		
		for( int c = 0; c < values.length; c++ )
			if( sval.equals( values[c] ) )
				return c;
				
		throw new DataParseException( "could not identify identifier \""+sval+"\"", lineno(), getSourceName() );
	}
	
	public int nextLocation() throws DataParseException
	{
		return nextWordValue( locations );
	}
	
	public boolean nextBoolean() throws DataParseException
	{
		return (nextWordValue( boolwords ) % 2)==0?false:true;
	}
	
	public void finishStatement() throws DataParseException
	{
		int i = 0;
		eolIsSignificant(true);
		try {
			while( (i != TT_EOF) && (i != TT_EOL) && (i != ';') )
			{
				i = nextToken();
			}
		} catch (IOException e)
		{
			throw new DataParseException( "';' expected", lineno(), getSourceName() );
		}
		eolIsSignificant(false);
	}
	
	/**
	 * Returns the sourceName.
	 * @return String
	 */
	public String getSourceName()
	{
		return sourceName;
	}

	/**
	 * Sets the sourceName.
	 * @param sourceName The sourceName to set
	 */
	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}


}