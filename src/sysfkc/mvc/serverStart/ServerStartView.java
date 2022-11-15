package sysfkc.mvc.serverStart;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.frm.mvc.BaseView;
import com.frm.mvc.CCenter;
import com.frm.snet.SSocketMgr;
import javax.swing.SwingConstants;

/**
 * @author LinHuanZhi
 * @time 2021年12月13日
 * @email lhz034069@163.com
 * @description 
 */
public class ServerStartView extends BaseView{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerStartView window = new ServerStartView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerStartView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setTitle("server");
		frame.setBounds(100, 100, 190, 209);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("启动服务器");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SSocketMgr.ss.trigger();
			}
		});
		btnNewButton.setBounds(42, 99, 98, 49);
		frame.getContentPane().add(btnNewButton);
	}

	@Override
	protected void mClear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void mClose() {
		// TODO Auto-generated method stub
		frame.dispose();
	}

	@Override
	protected void setVisible(boolean arg0) {
		// TODO Auto-generated method stub
		frame.setVisible(arg0);
	}
}
