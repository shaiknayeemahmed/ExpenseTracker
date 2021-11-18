package com.expensetracker.service;

import java.time.LocalDate;
import java.util.List;

import com.expensetracker.exceptions.ExpenseRecordNotFoundException;
import com.expensetracker.exceptions.UserNotFoundException;
import com.expensetracker.model.Expense;


public interface IExpenseService {
	void addTransaction(Expense expense);
	void deleteTransaction(int transactionId);
	void updateTransaction(int transactionId);

	
	List<Expense> getTransactionById(int transaction) throws ExpenseRecordNotFoundException;
	List<Expense> getTransactionByDate(LocalDate date) throws ExpenseRecordNotFoundException;
	List<Expense> findTransactionByUser(int userId)throws UserNotFoundException;
	List<Expense> findAllTransaction();

}
