package sysfkc.mvc.answer;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.frm.mvc.BaseView;

public class AnswerView extends BaseView{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnswerView window = new AnswerView();
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
	public AnswerView() {
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

	@Override
	protected void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void mClear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void mClose() {
		// TODO Auto-generated method stub
		
	}

}
