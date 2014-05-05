package com.cash.flow.task;

public interface TaskCompleteListener {
	void onTaskComplete(Integer idCaller, boolean sukses, String errorMessage);
}
