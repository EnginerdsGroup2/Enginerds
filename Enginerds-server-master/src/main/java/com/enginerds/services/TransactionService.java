package com.enginerds.services;

import java.io.IOException;
import java.text.ParseException;

import com.enginerds.domain.*;

public interface TransactionService{

    public Transaction saveTransaction(Transaction transaction) throws Exception;
    public void Polling() throws IOException,InterruptedException;
    public Transaction readFile(String filename);
    public Object findAllTransaction();
    public void createTransactionFile() throws IOException;
    public int[] count();
    public void invokeMethods();
    public Transaction getParameters(String line);
    public boolean fieldValidate(Transaction transaction) throws ParseException;
}
