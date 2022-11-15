package com.frm.snet;

import java.io.IOException;
import java.net.Socket;

import com.frm.thread.AbsThread;

/**
 * @author LinHuanZhi
 * @time 2021年12月13日
 * @email lhz034069@163.com
 * @description 
 */
public class GuardConnection extends AbsThread{
	private SSocketMgr ssMgr;
	public GuardConnection(SSocketMgr ssMgr) {
		// TODO Auto-generated constructor stub
		this.ssMgr = ssMgr;
		this.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(500); //偶现ssMgr == null;
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(ssMgr.isFlag_EnableService()) {
			try {
				if(ssMgr.getServerSocket() != null) { //解决： Cannot invoke "java.net.ServerSocket.accept()" because "this.serverSocket" is null
					Socket socket = ssMgr.getServerSocket().accept();
					ssMgr.clientSocketCreate(socket);
//					new ServiceThread(socket).start();
//					socket = null;
				}
//				}
			}
			catch (IOException e) {
//				e.printStackTrace();
				System.err.println("GuardConnection.run.Socket closed");
			}
			finally {
			} //停止服务
		}
	}

}
