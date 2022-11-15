package com.frm.snet;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.frm.proto.PDTO;

import sysfkc.mvc.answer.AnswerModel;

/**
 * @author LinHuanZhi
 * @time 2021年12月13日
 * @email lhz034069@163.com
 * @description 
 */
public class SSocketMgr {
	public static SSocketMgr ss = new SSocketMgr();
	public void trigger() { System.err.println("trigger-服务器已启动..."); };
	private ServerSocket serverSocket = null;
	//--------------cfg-----------------
	private int serverPort = INetCfg.serverPort;
	//--------------cfg-----------------
	/**
	 * ui控制
	 */
	private boolean flag_EnableService = false;
	
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public boolean isFlag_EnableService() {
		return flag_EnableService;
	}

	public void setFlag_EnableService(boolean flag_EnableService) {
		this.flag_EnableService = flag_EnableService;
	}


//	private HashSet<ClientSocketImpl> socketSet = new HashSet<ClientSocketImpl>();
	/**
	 * 如果出问题，考虑换成 ConcurrentHashMap(部分锁，线程安全)
	 * 全锁（HashTable）
	 * 不锁（HashMap）
	 */
//	private Map<Integer, ClientSocketImpl> waitLoginMap = new HashMap<Integer, ClientSocketImpl>();
	private Map<Integer, ClientSocketImpl> waitLoginMap = new ConcurrentHashMap<Integer, ClientSocketImpl>();
	/**
	 * afterLogin
	 */
	private Map<Integer, ClientSocketImpl> uIdMap = new HashMap<Integer, ClientSocketImpl>();
	
//	/**
//	 * 
//	 * @description 
//	 */
//	public void receive() {
//		
//	}
	
	/**
	 * 
	 * @description one2N
	 */
	public void broadcast(PDTO dto) { //准备用登录后的socket
//		dto.setsHsCode(0);
//		uIdMap.forEach((uId, cSocketImpl)->{ //后续需要区分登录前&&后用户
		waitLoginMap.forEach((cSocketImplHashCode, cSocketImpl)->{
			cSocketImpl.send(dto);
		});
	}
	/**
	 * 
	 * @description one2one，initiative
	 */
	public void send(PDTO dto) {
		ClientSocketImpl csImpl = findImplByCode(dto.getsHsCode());
		if(csImpl == null) {
			System.err.println("can't found ClientSocketImpl...");
		}else {			
			csImpl.send(dto);
		}
	}
	/**
	 * 
	 * @description one2one，passive
	 */
	public void response(PDTO dto) {
		this.send(dto);
	}
	
	private ClientSocketImpl findImplByCode(int sHsCode) {
		ClientSocketImpl csImpl = waitLoginMap.get(sHsCode); //后续需要区分登录前&&后用户
		return csImpl;
	}
	
	
	private SSocketMgr() {
		// TODO Auto-generated constructor stub
		flag_EnableService=true;
		enableService();
//		serverSocket_DisableService("constructor_上面方法跑完才会执行这里");
		shutDownRegister();
	}
	
	private void shutDownRegister() {
		Thread t = new Thread(() -> {
			serverSocket_DisableService("when windowClosing_窗口关闭时触发");
        });
        Runtime.getRuntime().addShutdownHook(t);
	}



//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		if(e.getSource() == this.btnEnable) {
//			this.uiEnableServer();
//		}
//		else if(e.getSource() == this.btnDisable) {
//			this.userDisableServer();
//		}
//	}
//	
//	private void uiEnableServer() {
//		String account = fieldAccount.getText();
//		String pwd = new String(fieldPwd.getPassword());
//		if(handleLogin(account, pwd)) {
//			btnEnable.setEnabled(false);
//			btnDisable.setEnabled(true);
//			flag_EnableService = true;
//		}
//		else {
//			JOptionPane.showMessageDialog(this, "Invalid User");
//			fieldPwd.setText("");
//		}
//	}
//	private boolean handleLogin(String ac, String pwd) {
//		return true;
//	}
//	private void userDisableServer() {
//		flag_EnableService = false;
//		serverSocket_DisableService("btn trigger_主动点击触发");
//		System.exit(0);
//	}
	
	
	/**
	 * 
	 * @description 
	 */
	public void enableService() {
		//等待启动服务
		while(!flag_EnableService) {
			System.out.println("等待服务器启动...");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				
			}	
		}
		//开放服务端口
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		//允许client端多次连接， 由于没有使用线程，同时只能由一个客户端连接服务器
//		while(flag_EnableService) {
//			try {
//				if(serverSocket != null) { //解决： Cannot invoke "java.net.ServerSocket.accept()" because "this.serverSocket" is null
//					Socket socket = serverSocket.accept();
//					new ServiceThread(socket).start();
//					socket = null;
//				}
////				}
//			}
//			catch (IOException e) {
//				e.printStackTrace();
//			}
//			finally {
//			} //停止服务
//		}
		new GuardConnection(this);
	}
	
	
	/**
	 * @param enterPoint
	 * @description ServerSocket关闭 && 断引
	 */
	private void serverSocket_DisableService(String enterPoint) {
		if(serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			serverSocket = null;
		}
	}
	
	
	public void clientSocketCreate(Socket socket) {
		//创建ClientSocketImpl——实例化同时创建相关subThread
		ClientSocketImpl csi = new ClientSocketImpl(socket, this);
		Integer hsCode = socket.hashCode();
		//保存
		this.waitLoginMap.put(hsCode, csi);
	}
	
	public void destroyImpl(int hsCode) {
		ClientSocketImpl csi = this.waitLoginMap.remove(hsCode);
		//temporary
		AnswerModel.removeVMembers(hsCode);
		if(csi != null) {
			csi.destroy();
		}
	}

}
