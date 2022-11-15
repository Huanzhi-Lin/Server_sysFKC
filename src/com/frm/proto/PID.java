package com.frm.proto;

/**
 * @author LinHuanZhi
 * @time 2021年11月1日
 * @email lhz034069@163.com
 * @description should be generated / separate by MAX_moduleName | enumName增加c2s | s2c
 */
public enum PID {
	PID_ZERO,
	PID_TICKER,
//	ERROR_STRING_TIPS,
	MAX_REMAIN_PID, //保留字，不要在上面加
	
	
	
	
	
	PID_SQL_LOGIN,
	PID_YZP_DNEGLU,
	PID_YZP_ZHUCE, //注册的协议号
	PID_YZP_DENGLUFAIL, //登录失败
	MAX_ACCOUNT, //账号的相关协议，在上面加
	
	
	
	
	CHAT_INFO,
	MAX_CHAT, //聊天账号的相关协议，在上面加
	
	
	PID_QUESTION_ENTER_ROOM, //进入c
	PID_QUESTION_ENTER_STATE, //进入房间收到的信息s
	PID_QUESTION_REQ_START, //请求开始答题c （就绪检测c）
	PID_QUESTION_PREPARE_START, //倒计时开始s
	PID_QUESTION_INFO_UPDATE, //题目信息s  tag
	PID_QUESTION_COMMIT_ANSWER, //提交答案c
	PID_QUESTION_SHOW_RESULT, //显示结果s  进入倒计时c  定时跳到tag||结束
	PID_QUESTION_END, //结束答题比赛s
	
	PID_QUESTION_ROOM_FULL, //房间满提示
	MAX_RANDOM_QUESTION,//答题的相关协议，在上面加
}
