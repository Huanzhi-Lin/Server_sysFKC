package com.frm.snet;

import java.net.Socket;

import com.frm.proto.PDTO;

/**
 * @author LinHuanZhi
 * @time 2021年12月13日
 * @email lhz034069@163.com
 * @description 
 */
public class ClientSocketImpl implements IDestroyable{
//	private Socket cs;
//	private SSocketMgr ssMgr;
	private GuardReceive guardReceive;

	public ClientSocketImpl(Socket socket, SSocketMgr ssMgr) {
		// TODO Auto-generated constructor stub
//		this.cs = socket;
//		this.ssMgr = ssMgr;
		guardReceive = new GuardReceive(socket, ssMgr);
	}
	
	public void send(PDTO dto) {
		guardReceive.send(dto);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		this.guardReceive.destroy();
		this.guardReceive = null;
	}

}
