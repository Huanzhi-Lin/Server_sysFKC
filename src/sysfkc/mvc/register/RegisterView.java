package sysfkc.mvc.register;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.frm.mvc.BaseView;

public class RegisterView extends BaseView{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterView window = new RegisterView();
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
	public RegisterView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	protected void setVisible(boolean arg0) {
		// TODO Auto-generated method stub
		frame.setVisible(arg0);
	}
	@Override
	protected void mClear() {
		// TODO Auto-generated method stub
		
	}
	protected void mClose() {
		// TODO Auto-generated method stub
		frame.dispose();		
	}

}
