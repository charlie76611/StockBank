package com.charlie.exceptions;


import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class BaseException extends RuntimeException
{

	public BaseException(String messageCode)
	{
	    super(messageCode);
		this.messageCode = messageCode;
	}
	
	public BaseException(Exception ex)
	{
	    super("exception.unknown");
	    messageCode = "exception.unknown";
	    this.ex = ex;
	}
	
	public BaseException(String messageCode, Exception ex)
	{
	    super(messageCode);
		this.messageCode = messageCode;
		this.ex = ex;
	}
	
	public Exception getException()
	{
	    return ex;
	}
	
	public String getMessage()
	{
	    return super.getMessage();
	}
	
	public String getMessageCode()
	{
	    return messageCode;
	}
	
	public static String getStackTraceAsString(Throwable t)
	{
	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    PrintWriter writer = new PrintWriter(bytes, true);
	    t.printStackTrace(writer);
	    return bytes.toString();
	}
	
	public String printStackTrace(Throwable t) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    PrintWriter writer = new PrintWriter(bytes, true);
	    ex.printStackTrace(writer);
	    return bytes.toString();
	}
	
	private Exception ex;
	private String messageCode;
}