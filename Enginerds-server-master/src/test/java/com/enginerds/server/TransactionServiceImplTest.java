package com.enginerds.server;

//import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertArrayEquals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.enginerds.domain.Transaction;
import com.enginerds.services.PersonService;
import com.enginerds.services.SanctionService;
import com.enginerds.services.TransactionService;
import com.enginerds.services.Implementation.PersonServiceImpl;
import com.enginerds.services.Implementation.RollbackException;
import com.enginerds.services.Implementation.TransactionServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceImplTest 
{
	
	@Mock
	TransactionService tranSerMock;
	
	@Mock
	PersonService personSerMock;
	
	@Mock
	SanctionService sacntionSerMock;
	
	 @Autowired
	 TransactionService transactionService;
	 
	 @Autowired
	 PersonService personService;
	
	@InjectMocks
	TransactionServiceImpl tranImp; 
	
	@InjectMocks
	PersonServiceImpl PersonImp;
	
	@Before
	public void InitializeMockito()
	{
		MockitoAnnotations.initMocks(this);
	}
	
	
  @Override
  public boolean equals(Object otherObject) 
	 {
    if (this == otherObject) 
    {
      return true;
    }
    if (otherObject instanceof Transaction) 
    {
      Transaction that = (Transaction) otherObject;
      return true;
    }
    return false;
  }
  
  
  //1
	@Test
	public void testreadFileforNumberofInvocations() throws ParseException, RollbackException
	{
	try 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	     Calendar cal = Calendar.getInstance();
	     String desiredDate = dateFormat.format(cal.getTime());
	     Date date1 = (Date) dateFormat.parse(desiredDate);
		
		Transaction expectedtransaction = new Transaction("jIXOxhN3Zq0d","LaurineStoker","VenettaCharpentier",date1,(float) 30688.10,"110879126035","113409298468","upload_fail");
		TransactionService transactionServiceSpy=Mockito.spy(transactionService);
	        
	     Mockito.doReturn(expectedtransaction).when(transactionServiceSpy).getParameters(anyString());
	     Mockito.doReturn(true).when(transactionServiceSpy).fieldValidate(expectedtransaction);
	     
	     Transaction actualTransaction = transactionServiceSpy.readFile("test1.txt");
	     
	     Assert.assertNotNull(actualTransaction);
	     
	     Mockito.verify(transactionServiceSpy, Mockito.times(1)).getParameters(anyString());
	     Mockito.verify(transactionServiceSpy, Mockito.times(1)).fieldValidate(actualTransaction);
	    
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}

}
	
	//2
	@Test
	public void testreadFileforInvalidInput()
	{
	try 
	{
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	     Calendar cal = Calendar.getInstance();
	     String desiredDate = dateFormat.format(cal.getTime());
	     Date date1 = (Date) dateFormat.parse(desiredDate);
		
		Transaction expectedtransaction = new Transaction("fmkhj5464dif","Juie","Shekhar",date1,(float) 30688.10,"110879126035","113409298468","Field Validation Fail");
		
		stub(tranSerMock.getParameters(anyString())).toReturn(expectedtransaction);
		when(tranSerMock.fieldValidate(any(Transaction.class))).thenReturn(false);
		
		Transaction actualtransaction = tranImp.readFile("test2.txt");
		
		assertNotNull("its null",actualtransaction);
		assertEquals("not okay",expectedtransaction.getStatus(),actualtransaction.getStatus());
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}

}
	
	//3
	@Test
	public void testreadFileforValidInput()
	{
	try 
	{
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	     Calendar cal = Calendar.getInstance();
	     String desiredDate = dateFormat.format(cal.getTime());
	     Date date1 = (Date) dateFormat.parse(desiredDate);
		Transaction expectedtransaction = new Transaction("jIXOxhN3Zq0d","Juie","Shekhar",date1,(float) 30688.10,"110879126035","113409298468","Field Validation Pass");
		
		when(tranSerMock.getParameters(anyString())).thenReturn(expectedtransaction);
		when(tranSerMock.fieldValidate(any(Transaction.class))).thenReturn(true);
		when(personSerMock.checkBalance(any(Transaction.class))).thenReturn(false);
		when(sacntionSerMock.CheckSanctionList(any(Transaction.class))).thenReturn(false);
		
		Transaction actualtransaction = tranImp.readFile("test3.txt");
		
		System.out.println(actualtransaction.getStatus());
		
		assertNotNull("its null",actualtransaction);
		assertEquals("not okay",expectedtransaction.getStatus(),actualtransaction.getStatus());
		
		Mockito.verify(sacntionSerMock, Mockito.times(0)).CheckSanctionList(expectedtransaction);
		
		
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}

}
	
	//4
	@Test
	public void testreadFileReturnType()
	{
		try 
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		     Calendar cal = Calendar.getInstance();
		     String desiredDate = dateFormat.format(cal.getTime());
		     Date date1 = (Date) dateFormat.parse(desiredDate);
			Transaction expectedtransaction = new Transaction("jIXOxhN3Zq0d","Name1","Name2",date1,(float) 30688.10,"110879126035","113409298468","Field Validation Pass");
			Transaction actualtransaction = tranImp.readFile("test4.txt");
			
			
			assertEquals(expectedtransaction.getClass(),actualtransaction.getClass());
			
		}
	catch (Exception e) 
		{
			e.printStackTrace();
		}
	
	}
	
	
	@Test
	public void testsetParametersReturnType()
	{
	try 
	{
		Date yourDate = new Date(System.currentTimeMillis());
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(yourDate);
		//cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date desiredDate = cal.getTime();
		Transaction expectedtransaction = new Transaction("jIXOxhN3Zq0d","Name1","Name2",desiredDate,(float) 30688.10,"110879126035","113409298468","upload_fail");
		Transaction actualtransaction = tranImp.getParameters("I0cvTKrBfDba17032018DudleyHarshaw                      876748998192VenettaCharpentier                 062598029966     44672.32");
		
		assertEquals(expectedtransaction.getClass(),actualtransaction.getClass());
			
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}

	}
	
	@Test
	public void testFieldValidationReturnType()
	{
	try 
	{
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        String desiredDate = dateFormat.format(cal.getTime());
        Date date1 = (Date) dateFormat.parse(desiredDate);
		Transaction expectedtransaction = new Transaction("jIXOxhN3Zq0d","Sai","Juie",date1,(float) 30688.10,"110879126035","113409298468","Field Validation Pass");
		System.out.println("date**********************"+date1);
		boolean val = tranImp.fieldValidate(expectedtransaction);
		
		assertEquals(true,val);
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}

}
	
	@Test
	public void testAlphanumericReturnType()
	{
        String str1="kef8r73948kefnk";
        boolean value=tranImp.isAlphaNumeric(str1);
        
        String str2="&*@@kbhm!!!!";
        boolean value2=tranImp.isAlphaNumeric(str2);
        
        assertEquals(true,value);
        assertEquals(false,value2);
	}
	

}
	
