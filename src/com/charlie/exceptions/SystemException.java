package com.charlie.exceptions;

public class SystemException extends BaseException
{
	public SystemException(Exception ex)
	{
        super("exception.unknown", ex);
    }

    public SystemException(String messageCode)
    {
        super(messageCode);
    }	

	public SystemException(String messageCode, Exception ex)
    {
        super(messageCode, ex);
    }
    
}
