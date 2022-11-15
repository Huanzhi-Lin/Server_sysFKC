package com.frm.snet;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.frm.proto.PDTO;
import com.frm.thread.AbsThread;
import com.frm.utils.PrintUtils;
import com.frm.utils.TimeUtils;

import sysfkc.a_proto.apdto.PDTO_TICKER;

/**
 * @author LinHuanZhi
 * @time 2021年12月13日
 * @email lhz034069@163.com
 * @description 
 */
public class GuardReceive extends AbsThread implements IDestroyable{
	private Socket s;
	private SSocketMgr ssMgr;
//	private ObjectOutputStream oos = null; //stream 不保存
	private long receiveTimeDelay = 10000;
	private boolean run = true;
	private long lastReceiveTime = System.currentTimeMillis();

	public GuardReceive(Socket socket, SSocketMgr ssMgr) {
		this.s = socket;
		this.ssMgr = ssMgr;
		this.start();
	}
	
	private void mDispatch(PDTO msgReceived) {
		PDTO out = null;
		switch(msgReceived.getPId()) {
		case PID_TICKER:
			String str = ((PDTO_TICKER)msgReceived).info;
			((PDTO_TICKER)msgReceived).info = "server's ack...";
			out = msgReceived;
			break;
		default:
//				PrintUtils.pr("Unknow message received: " + msgReceived.getPId());
			break;
		}
		if (out != null) {
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject(out);
				oos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		while (this.ssMgr!=null && ssMgr.isFlag_EnableService() && run) {
			if (TimeUtils.MillisDiff(lastReceiveTime) > receiveTimeDelay) {
				overThis(); //long time no msg from client.
			} else {
				try {
					InputStream is;
					is = s.getInputStream();
					if (is.available() > 0) {
						ObjectInputStream ois = new ObjectInputStream(is);
						PDTO obj = (PDTO)ois.readObject(); //在这里会停住，客户端关闭后才会变成EOF
						lastReceiveTime = System.currentTimeMillis();
						PDTO msgReceived = obj;
						this.mDispatch(msgReceived);
						msgReceived.setsHsCode(s.hashCode());
						SPDTODispatchMgr.sp.dispatch(msgReceived);
					} else {
						Thread.sleep(10);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println("can't found class...");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					this.overThis();
					System.err.println("IOException....");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println("InterruptedException with sleep....");					
				}
				
			}
		}
	}
	
	public void send(PDTO dto) {
		try {
			//-------------------用旧的oos会导致客户端出现invalid stream header--------------
//			if(oos == null) {
//				oos = new ObjectOutputStream(s.getOutputStream());
//			}
			//-------------------用旧的oos会导致客户端出现invalid stream header--------------
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			//↑解决：-------------------用旧的oos会导致客户端出现invalid stream header--------------
			oos.writeObject(dto);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

//	private void handleClient() {
//		OutputStream os = null;
////		ObjectOutputStream oos = null;
//		InputStream is = null;
//		ObjectInputStream ois = null;
//		try {
//			os = socket.getOutputStream();
//			oos = new ObjectOutputStream(os);
//			is = socket.getInputStream();
//			ois = new ObjectInputStream(is);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//			PrintUtils.pr(e.getMessage());
//			return;
//		}
//		//处理来自客户端的对象
//		while(true) {
//			//从对象输入数据流中读取数据对象
//			PDTO msgReceived = null;
//			try {
//				msgReceived = (PDTO) ois.readObject();
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
////				e.printStackTrace();
//				PrintUtils.pr(e.getMessage());
//				continue;
//			} catch (IOException e) { //socket出错，停止对此socket的处理
//				// TODO Auto-generated catch block
////				e.printStackTrace();
//				PrintUtils.pr(e.getMessage());
//				return;
//			}
//			this.mDispatch(msgReceived);
//		}
//		
//	}

	/**
	 * long time or IOExcept.
	 * passive
	 */
	private void overThis() {
		run = false;
		int hsCode = this.s.hashCode();
		this.ssMgr.destroyImpl(hsCode);
		System.err.println("overThis()... 断开连接");
//		if (this.s != null) {
//			try {
//				this.s.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println("关闭：" + s.getRemoteSocketAddress());
	}
	
	/**
	 * socket 相关 close
	 */
	private void closeClient() {
		if(s != null) {
			try {
				s.shutdownInput();
				s.shutdownOutput();
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		run = false;
		this.closeClient();
		this.s = null;
		this.ssMgr = null;
	}

}
