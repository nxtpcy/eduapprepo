package com.slhj.www.edu.utils;

import com.slhj.www.edu.common.StatusType;

public class DAOResultUtil {

	//判断增改删是否成功
	public static StatusType getAddUpDateRemoveResult(int operateRows,
			int standardRows) {
		if (operateRows > standardRows) {
			return StatusType.SUCCESS;
		}
		return StatusType.ERROR;
	}

	public static StatusType getBatchResult(int operateRows, int standardRows) {
		if (operateRows == standardRows) {
			return StatusType.SUCCESS;
		}
		return StatusType.ERROR;
	}
}
