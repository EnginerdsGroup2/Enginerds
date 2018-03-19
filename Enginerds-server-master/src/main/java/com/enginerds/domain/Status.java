package com.enginerds.domain;

public class Status {
	public String fieldValidFail; 
	public String fieldValidPass; 
	public String statusValidFail;
	public String statusValidPass; 
	public String balanceValidFail;
	public String uploadPass;
	public String uploadFail;
	public String rollback;
	public Status()
	{
		fieldValidFail = "Field Validation Fail";
		fieldValidPass = "Field Validation Pass";
		statusValidFail = "Sanction Screening Fail";
		statusValidPass = "Sanction Screening Pass";	
		balanceValidFail = "Insufficient Balance";
		rollback = "Transaction Rollback";
		uploadPass = "upload_pass";
		uploadFail = "upload_fail";
	}
}
